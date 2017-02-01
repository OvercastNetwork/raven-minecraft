package tc.oc.minecraft.logging;

import net.kencochrane.raven.DefaultRavenFactory;
import net.kencochrane.raven.Raven;
import net.kencochrane.raven.dsn.Dsn;
import net.kencochrane.raven.event.Event;
import net.kencochrane.raven.event.EventBuilder;
import net.kencochrane.raven.event.helper.EventBuilderHelper;
import net.kencochrane.raven.jul.SentryHandler;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/**
 * Adds a few handy features to the superclass:
 * - Filters
 * - Cloning
 * - Passing LogRecords to EventBuilderHelpers
 */
public class BetterRaven extends Raven {
    public static class Factory extends DefaultRavenFactory {
        @Override
        public BetterRaven createRavenInstance(Dsn dsn) {
            BetterRaven raven = new BetterRaven(this);
            raven.setConnection(createConnection(dsn));
            return raven;
        }
    }

    class Handler extends SentryHandler {
        public Handler(Level level) {
            super(BetterRaven.this);
            setLevel(level);
        }

        @Override
        public boolean isLoggable(LogRecord record) {
            if(!super.isLoggable(record)) return false;
            for(Filter filter : filters) {
                if(!filter.isLoggable(record)) return false;
            }
            return true;
        }

        @Override
        protected Event buildEvent(LogRecord record) {
            logRecord.set(record);
            try {
                return super.buildEvent(record);
            } finally {
                logRecord.remove();
            }
        }
    }

    public interface Helper {
        void helpBuildingEvent(EventBuilder eventBuilder, @Nullable LogRecord logRecord);
    }

    // Used to pass the current LogRecord from Handler#buildEvent to Builder#helpBuildingEvent.
    // Raven does not seem to provide any better way to do this.
    private static final ThreadLocal<LogRecord> logRecord = new ThreadLocal<>();

    private final Factory factory;
    private final Set<Filter> filters = new HashSet<>();
    private final Set<Helper> helpers = new LinkedHashSet<>();

    public BetterRaven(Factory factory) {
        this.factory = factory;
        addBuilderHelper(eventBuilder -> {
            for(Helper helper : helpers) {
                helper.helpBuildingEvent(eventBuilder, logRecord.get());
            }
        });
    }

    public void listen(Logger logger, Level level) {
        logger.addHandler(new Handler(level));
    }

    public void listen(Logger logger) {
        listen(logger, Level.SEVERE);
    }

    public boolean addFilter(Filter filter) {
        return filters.add(filter);
    }

    public Set<Filter> getFilters() {
        return filters;
    }

    public boolean addHelper(Helper helper) {
        return helpers.add(helper);
    }

    public Set<Helper> getHelpers() {
        return helpers;
    }

    public BetterRaven clone(Dsn dsn) {
        BetterRaven child = factory.createRavenInstance(dsn);
        for(EventBuilderHelper helper : getBuilderHelpers()) {
            child.addBuilderHelper(helper);
        }
        child.filters.addAll(filters);
        child.helpers.addAll(helpers);
        return child;
    }
}

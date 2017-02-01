package tc.oc.bukkit.logging;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

/**
 * Deny log records forwarded from JUL to L4J by CraftBukkit
 */
class ForwardedEventFilter extends AbstractFilter {
    private Result filter(StackTraceElement source) {
        if(source.getClassName().endsWith("ForwardLogHandler")) {
            return Result.DENY;
        } else {
            return Result.NEUTRAL;
        }
    }

    @Override
    public Result filter(LogEvent event) {
        return filter(event.getSource());
    }
}

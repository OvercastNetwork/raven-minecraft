package tc.oc.bukkit.logging;

import java.util.logging.Logger;
import javax.inject.Inject;

import com.google.inject.multibindings.OptionalBinder;
import net.kencochrane.raven.log4j2.SentryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.bukkit.plugin.PluginLogger;
import tc.oc.inject.ProtectedModule;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.MinecraftRavenModule;

public class BukkitRavenModule extends ProtectedModule {

    public BukkitRavenModule() {
        super(BukkitRavenModule.class);
    }

    @Override
    protected void configure() {
        install(new MinecraftRavenModule());
        OptionalBinder.newOptionalBinder(publicBinder(), BetterRaven.class)
                      .setBinding().toProvider(BukkitRavenFactory.class);
        bind(Installer.class).asEagerSingleton();
    }

    private static class Installer {
        @Inject void install(BetterRaven raven, PluginLogger logger) {
            logger.info("Installing Raven");

            raven.listen(Logger.getLogger(""));

            org.apache.logging.log4j.Logger mojangLogger = LogManager.getRootLogger();
            if(mojangLogger instanceof org.apache.logging.log4j.core.Logger) {
                // No way to do this through the public API
                SentryAppender appender = new SentryAppender(raven);
                appender.addFilter(ThresholdFilter.createFilter("ERROR", "NEUTRAL", "DENY"));
                appender.addFilter(new ForwardedEventFilter());
                appender.start();
                ((org.apache.logging.log4j.core.Logger) mojangLogger).addAppender(appender);
            } else {
                logger.warning("Raven cannot integrate with Mojang logger with unexpected class " + mojangLogger.getClass());
            }
        }
    }
}

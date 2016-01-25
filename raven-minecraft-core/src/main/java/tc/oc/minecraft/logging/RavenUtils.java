package tc.oc.minecraft.logging;

import java.util.logging.Logger;

public final class RavenUtils {
    private RavenUtils() {}

    public static Logger getRootLogger(Logger logger) {
        while(logger.getParent() != null) logger = logger.getParent();
        return logger;
    }
}

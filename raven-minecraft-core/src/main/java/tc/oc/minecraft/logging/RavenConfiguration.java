package tc.oc.minecraft.logging;

import java.util.Set;

import net.kencochrane.raven.dsn.Dsn;

public interface RavenConfiguration {
    boolean enabled();
    Dsn dsn();
    Set<String> pluginVersionTags();
}

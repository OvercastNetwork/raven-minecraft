package tc.oc.bungee.logging;

import java.util.Set;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import net.kencochrane.raven.dsn.Dsn;
import net.md_5.bungee.config.Configuration;
import tc.oc.minecraft.logging.RavenConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

public class BungeeRavenConfiguration implements RavenConfiguration {

    private final Configuration config;

    public BungeeRavenConfiguration(Configuration config) {
        this.config = checkNotNull(config.getSection("raven"));
    }

    @Override
    public boolean enabled() {
        return config.getBoolean("enabled");
    }

    @Override
    public Dsn dsn() {
        return new Dsn(config.getString("dsn"));
    }

    @Override
    public Set<String> pluginVersionTags() {
        return ImmutableSet.copyOf(config.getStringList("version-tags"));
    }
}

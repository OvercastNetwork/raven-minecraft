package tc.oc.minecraft.logging;

import java.util.Set;
import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;
import net.kencochrane.raven.dsn.Dsn;
import tc.oc.minecraft.api.configuration.Configuration;
import tc.oc.minecraft.api.configuration.ConfigurationSection;

import static com.google.common.base.Preconditions.checkNotNull;

public class RavenConfiguration {

    private final ConfigurationSection config;

    @Inject RavenConfiguration(Configuration config) {
        this.config = checkNotNull(config.getSection("raven"));
    }

    public boolean enabled() {
        return config.getBoolean("enabled");
    }

    public Dsn dsn() {
        return new Dsn(config.getString("dsn"));
    }

    public Set<String> pluginVersionTags() {
        return ImmutableSet.copyOf(config.getStringList("version-tags"));
    }
}

package tc.oc.bukkit.logging;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import net.kencochrane.raven.dsn.Dsn;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import tc.oc.minecraft.logging.RavenConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

public class BukkitRavenConfiguration implements RavenConfiguration {

    private final ConfigurationSection config;

    public BukkitRavenConfiguration(Configuration config) {
        this.config = checkNotNull(config.getConfigurationSection("raven"));
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

package tc.oc.bungee.logging;

import java.util.logging.Logger;
import javax.annotation.Nullable;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import tc.oc.bungee.configuration.YamlConfigurationLoader;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.RavenConfiguration;

public class RavenPlugin extends Plugin {

    private RavenConfiguration configuration;

    private RavenConfiguration getRavenConfiguration() {
        if(configuration == null) {
            configuration = new BungeeRavenConfiguration(new YamlConfigurationLoader(this).loadConfig());
        }
        return configuration;
    }

    private BetterRaven raven;

    public @Nullable BetterRaven getRaven() {
        if(raven == null && getRavenConfiguration().enabled()) {
            getLogger().info("Installing Raven");
            buildRaven();
            installRaven();
        }
        return raven;
    }

    private void buildRaven() {
        raven = new BungeeRavenFactory().createRavenInstance(getRavenConfiguration());
    }

    private void installRaven() {
        raven.listen(Logger.getLogger(""));
        raven.listen(ProxyServer.getInstance().getLogger()); // Main Bungee logger does not use parent handlers
    }
}

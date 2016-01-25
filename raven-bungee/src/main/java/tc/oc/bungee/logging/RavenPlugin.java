package tc.oc.bungee.logging;

import java.util.logging.Logger;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import tc.oc.bungee.BungeeVersionInspector;
import tc.oc.bungee.configuration.YamlConfigurationLoader;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.CoreRavenFactory;
import tc.oc.minecraft.logging.RavenConfiguration;

public class RavenPlugin extends Plugin {
    private BetterRaven raven;
    public BetterRaven getRaven() { return raven; }

    private RavenConfiguration configuration;
    private RavenConfiguration getRavenConfiguration() {
        if(configuration == null) {
            configuration = new BungeeRavenConfiguration(new YamlConfigurationLoader(this).loadConfig());
        }
        return configuration;
    }

    private void buildRaven() {
        raven = new CoreRavenFactory(new BungeeVersionInspector(getProxy())).createRavenInstance(getRavenConfiguration());
    }

    private void installRaven() {
        raven.listen(Logger.getLogger(""));
        raven.listen(ProxyServer.getInstance().getLogger()); // Main Bungee logger does not use parent handlers
    }

    @Override
    public void onEnable() {
        if(getRavenConfiguration().enabled()) {
            getLogger().info("Installing Raven");
            buildRaven();
            installRaven();
        }
    }
}

package tc.oc.bungee.logging;

import net.md_5.bungee.api.ProxyServer;
import tc.oc.bungee.BungeeVersionInspector;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.CoreRavenFactory;
import tc.oc.minecraft.logging.RavenConfiguration;

public class BungeeRavenFactory extends CoreRavenFactory {

    public BungeeRavenFactory() {
        super(new BungeeVersionInspector(ProxyServer.getInstance()));
    }

    @Override
    public BetterRaven createRavenInstance(RavenConfiguration configuration) {
        final BetterRaven raven = super.createRavenInstance(configuration);
        raven.addHelper(new BungeeRavenHelper());
        return raven;
    }
}

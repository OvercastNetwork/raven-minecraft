package tc.oc.bungee.logging;

import net.kencochrane.raven.dsn.Dsn;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.MinecraftRavenFactory;

public class BungeeRavenFactory extends MinecraftRavenFactory {

    @Override
    public BetterRaven createRavenInstance(Dsn dsn) {
        final BetterRaven raven = new BetterRaven.Factory().createRavenInstance(dsn);
        raven.addHelper(new BungeeRavenHelper());
        return raven;
    }
}

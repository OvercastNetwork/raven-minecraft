package tc.oc.bukkit.logging;

import net.kencochrane.raven.dsn.Dsn;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.MinecraftRavenFactory;

class BukkitRavenFactory extends MinecraftRavenFactory {

    @Override
    public BetterRaven createRavenInstance(Dsn dsn) {
        final BetterRaven raven = new BetterRaven.Factory().createRavenInstance(dsn);
        raven.addFilter(new BukkitLogFilter());
        raven.addHelper(new BukkitRavenHelper());
        return raven;
    }
}

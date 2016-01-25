package tc.oc.bukkit.logging;

import org.bukkit.Bukkit;
import tc.oc.bukkit.BukkitVersionInspector;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.CoreRavenFactory;
import tc.oc.minecraft.logging.RavenConfiguration;

public class BukkitRavenFactory extends CoreRavenFactory {

    public BukkitRavenFactory() {
        super(new BukkitVersionInspector(Bukkit.getServer()));
    }

    @Override
    public BetterRaven createRavenInstance(RavenConfiguration configuration) {
        final BetterRaven raven = super.createRavenInstance(configuration);
        raven.addFilter(new BukkitLogFilter());
        raven.addHelper(new BukkitRavenHelper());
        return raven;
    }
}

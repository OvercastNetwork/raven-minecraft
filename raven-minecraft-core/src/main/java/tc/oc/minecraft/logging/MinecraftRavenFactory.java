package tc.oc.minecraft.logging;

import javax.inject.Inject;
import javax.inject.Provider;

import net.kencochrane.raven.dsn.Dsn;

public class MinecraftRavenFactory extends BetterRaven.Factory implements Provider<BetterRaven> {

    @Inject private CoreRavenHelper coreRavenHelper;
    @Inject private PluginVersionTagger pluginVersionTagger;
    @Inject private RavenConfiguration configuration;

    @Override
    public BetterRaven get() {
        return createRavenInstance(configuration.dsn());
    }

    @Override
    public BetterRaven createRavenInstance(Dsn dsn) {
        final BetterRaven raven = new BetterRaven.Factory().createRavenInstance(dsn);
        raven.addHelper(coreRavenHelper);
        raven.addBuilderHelper(pluginVersionTagger);
        return raven;
    }
}

package tc.oc.minecraft.logging;

import tc.oc.minecraft.VersionInspector;

public class CoreRavenFactory {

    private final VersionInspector versionInspector;

    public CoreRavenFactory(VersionInspector versionInspector) {
        this.versionInspector = versionInspector;
    }

    public BetterRaven createRavenInstance(RavenConfiguration configuration) {
        final BetterRaven raven = new BetterRaven.Factory().createRavenInstance(configuration.dsn());
        raven.addHelper(new CoreRavenHelper());
        raven.addBuilderHelper(new PluginVersionTagger(versionInspector, configuration.pluginVersionTags()));
        return raven;
    }
}

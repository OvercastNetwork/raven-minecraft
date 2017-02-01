package tc.oc.minecraft.logging;

import tc.oc.inject.ProtectedModule;

public class MinecraftRavenModule extends ProtectedModule {

    public MinecraftRavenModule() {
        super(MinecraftRavenModule.class);
    }

    @Override
    protected void configure() {
        install(new RavenApiModule());

        bind(PluginVersionTagger.class);
        bind(RavenConfiguration.class);
        bind(CoreRavenHelper.class);

        expose(PluginVersionTagger.class);
        expose(RavenConfiguration.class);
        expose(CoreRavenHelper.class);
    }
}

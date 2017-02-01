package tc.oc.bungee.logging;

import java.util.logging.Logger;
import javax.inject.Inject;

import com.google.inject.multibindings.OptionalBinder;
import net.md_5.bungee.api.ProxyServer;
import tc.oc.inject.ProtectedModule;
import tc.oc.minecraft.logging.BetterRaven;
import tc.oc.minecraft.logging.MinecraftRavenModule;

public class BungeeRavenModule extends ProtectedModule {

    public BungeeRavenModule() {
        super(BungeeRavenModule.class);
    }

    @Override
    protected void configure() {
        install(new MinecraftRavenModule());
        OptionalBinder.newOptionalBinder(publicBinder(), BetterRaven.class)
                      .setBinding().toProvider(BungeeRavenFactory.class);
        bind(Installer.class).asEagerSingleton();
    }

    private static class Installer {
        @Inject void install(BetterRaven raven) {
            raven.listen(Logger.getLogger(""));
            raven.listen(ProxyServer.getInstance().getLogger()); // Main Bungee logger does not use parent handlers
        }
    }
}

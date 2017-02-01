package tc.oc.minecraft.logging;

import com.google.inject.multibindings.OptionalBinder;
import tc.oc.inject.ProtectedModule;

public class RavenApiModule extends ProtectedModule {

    public RavenApiModule() {
        super(RavenApiModule.class);
    }

    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(publicBinder(), BetterRaven.class);
    }
}

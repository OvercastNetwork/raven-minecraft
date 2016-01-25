package tc.oc.minecraft.logging;

import java.util.Set;
import javax.annotation.Nullable;

import net.kencochrane.raven.event.EventBuilder;
import net.kencochrane.raven.event.helper.EventBuilderHelper;
import tc.oc.minecraft.VersionInspector;

public class PluginVersionTagger implements EventBuilderHelper {

    private final VersionInspector versionInspector;
    private final @Nullable Set<String> plugins;

    public PluginVersionTagger(VersionInspector versionInspector, @Nullable Set<String> plugins) {
        this.versionInspector = versionInspector;
        this.plugins = plugins;
    }

    @Override
    public void helpBuildingEvent(EventBuilder builder) {
        for(String name : versionInspector.getPluginNames()) {
            if(plugins == null || plugins.contains(name)) {
                builder.addTag(name, versionInspector.getPluginVersion(name));
            }
        }
    }
}

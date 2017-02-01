package tc.oc.minecraft.logging;

import java.util.Set;
import javax.annotation.Nullable;
import javax.inject.Inject;

import net.kencochrane.raven.event.EventBuilder;
import net.kencochrane.raven.event.helper.EventBuilderHelper;
import tc.oc.minecraft.api.plugin.Plugin;
import tc.oc.minecraft.api.plugin.PluginDescription;
import tc.oc.minecraft.api.plugin.PluginFinder;

class PluginVersionTagger implements EventBuilderHelper {

    private final PluginFinder pluginFinder;
    private final @Nullable Set<String> plugins;

    @Inject PluginVersionTagger(PluginFinder pluginFinder, RavenConfiguration config) {
        this.pluginFinder = pluginFinder;
        this.plugins = config.pluginVersionTags();
    }

    @Override
    public void helpBuildingEvent(EventBuilder builder) {
        for(Plugin plugin : pluginFinder.getAllPlugins()) {
            final PluginDescription description = plugin.getDescription();
            if(plugins == null || plugins.contains(description.getName())) {
                builder.addTag(description.getName(),
                               description.getVersion());
            }
        }
    }
}

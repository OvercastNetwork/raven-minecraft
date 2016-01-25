package tc.oc.bukkit;

import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import tc.oc.minecraft.VersionInspector;

public class BukkitVersionInspector implements VersionInspector {

    private final Server server;

    public BukkitVersionInspector(Server server) {
        this.server = server;
    }

    @Override
    public String getServerName() {
        return server.getName();
    }

    @Override
    public String getServerVersion() {
        return server.getVersion();
    }

    @Override
    public Collection<String> getPluginNames() {
        return Collections2.transform(Arrays.asList(server.getPluginManager().getPlugins()), new Function<Plugin, String>() {
            @Override public String apply(@Nullable Plugin plugin) {
                return plugin.getName();
            }
        });
    }

    @Override
    public String getPluginVersion(String pluginName) {
        final Plugin plugin = server.getPluginManager().getPlugin(pluginName);
        if(plugin == null) throw new IllegalArgumentException("No plugin named " + pluginName);
        return plugin.getDescription().getVersion();
    }
}

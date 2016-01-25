package tc.oc.minecraft;

import java.util.Collection;

public interface VersionInspector {

    String getServerName();

    String getServerVersion();

    Collection<String> getPluginNames();

    String getPluginVersion(String pluginName);
}

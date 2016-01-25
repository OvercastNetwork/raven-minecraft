package tc.oc.bungee.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class YamlConfigurationLoader {
    private static final String CONFIG_FILENAME = "config.yml";

    private final Plugin plugin;

    public YamlConfigurationLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public Configuration loadConfig() {
        final String pluginName = plugin.getDescription().getName();
        try {
            File configFile = new File(plugin.getDataFolder(), CONFIG_FILENAME);

            if(!configFile.exists()) {
                InputStream resourceStream = plugin.getResourceAsStream(CONFIG_FILENAME);
                if(resourceStream != null) {
                    plugin.getLogger().info(String.format("[{0}] No config file detected. Copying default config from jar", pluginName));

                    if(!plugin.getDataFolder().mkdirs()) {
                        throw new IOException("Failed to create plugin data folder");
                    }

                    try(OutputStream os = Files.asByteSink(configFile).openStream()) {
                        ByteStreams.copy(resourceStream, os);
                    }
                }
            }

            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, String.format("[{0}] Failed to read configuration file: {1}", pluginName, e.getMessage()), e);
            return new Configuration();
        }
    }
}

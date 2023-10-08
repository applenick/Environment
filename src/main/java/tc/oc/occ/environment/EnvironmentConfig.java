package tc.oc.occ.environment;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class EnvironmentConfig {

  private Map<String, Object> storedEnvs;

  public EnvironmentConfig(Configuration config) {
    reload(config);
  }

  public Map<String, Object> getStoredEnvs() {
    return storedEnvs;
  }

  public void reload(Configuration config) {
    this.storedEnvs = parseEnvs(config);
  }

  private Map<String, Object> parseEnvs(Configuration config) {
    ConfigurationSection section = config.getConfigurationSection("stored");
    if (section == null) {
      return new HashMap<>();
    }
    return section.getValues(false);
  }
}

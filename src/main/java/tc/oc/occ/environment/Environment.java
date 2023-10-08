package tc.oc.occ.environment;

import co.aikar.commands.BukkitCommandManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public class Environment extends JavaPlugin {

  private static Environment plugin;

  private EnvironmentConfig config;
  private BukkitCommandManager commands;

  private Map<String, Object> envVariables = new HashMap<>();

  @Override
  public void onEnable() {
    plugin = this;

    this.saveDefaultConfig();
    this.reloadConfig();

    this.config = new EnvironmentConfig(getConfig());
    this.envVariables.putAll(config.getStoredEnvs());
    getLogger().info("Set " + envVariables.size() + " env variables from the config");

    this.commands = new BukkitCommandManager(this);
    this.commands.getCommandCompletions().registerCompletion("keys", c -> envVariables.keySet());
    this.commands.registerCommand(new EnvironmentCommand());
  }

  public static Environment get() {
    return plugin;
  }

  public void setEnv(String key, Object value) {
    envVariables.put(key, value);
  }

  public Object getEnv(String key) {
    return envVariables.get(key);
  }

  public boolean deleteEnv(String key) {
    return envVariables.remove(key) != null;
  }

  public Set<String> getKeys() {
    return envVariables.keySet();
  }

  public String getString(String key) {
    Object value = getEnv(key);
    return (value instanceof String) ? (String) value : null;
  }

  public Long getLong(String key) {
    Object value = getEnv(key);
    return (value instanceof Long) ? (Long) value : null;
  }

  public Integer getInt(String key) {
    Object value = getEnv(key);
    return (value instanceof Integer) ? (Integer) value : null;
  }

  public Double getDouble(String key) {
    Object value = getEnv(key);
    return (value instanceof Double) ? (Double) value : null;
  }

  public Boolean getBoolean(String key) {
    Object value = getEnv(key);
    return (value instanceof Boolean) ? (Boolean) value : null;
  }
}

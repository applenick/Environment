package tc.oc.occ.environment;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Subcommand;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

@CommandAlias("env")
@CommandPermission("env.admin")
public class EnvironmentCommand extends BaseCommand {

  private static final String NOT_SET = ChatColor.DARK_RED + "☒ Not Set";

  @Dependency private Environment plugin;

  @Subcommand("get")
  @CommandCompletion("@keys")
  public void getEnv(CommandSender sender, String key) {
    Object value = plugin.getEnv(key);
    sender.sendMessage(
        formatKey(key) + ChatColor.GRAY + ": " + (value == null ? NOT_SET : formatValue(value)));
  }

  @Subcommand("set")
  @CommandCompletion("@keys *")
  public void set(CommandSender sender, String key, String value) {
    Object existing = plugin.getEnv(key);
    plugin.setEnv(key, value);

    if (existing != null) {
      sender.sendMessage(
          formatKey(key)
              + ChatColor.GRAY
              + " updated from "
              + formatValue(existing)
              + ChatColor.GRAY
              + " to "
              + formatValue(value));
      return;
    }

    sender.sendMessage(formatKey(key) + ChatColor.GRAY + " set to " + formatValue(value));
  }

  @Subcommand("del")
  @CommandCompletion("@keys")
  public void delete(CommandSender sender, String key) {
    boolean deleted = plugin.deleteEnv(key);

    if (!deleted) {
      sender.sendMessage(formatKey(key) + ChatColor.RED + " has not been set!");
      return;
    }

    sender.sendMessage(formatKey(key) + ChatColor.GREEN + " has been deleted");
  }

  @Default
  @Subcommand("list|ls")
  public void list(CommandSender sender) {
    Set<String> keys = plugin.getKeys();

    if (keys.isEmpty()) {
      sender.sendMessage(ChatColor.RED + "No environment variables set!");
      return;
    }

    sender.sendMessage(
        ChatColor.GOLD
            + ChatColor.BOLD.toString()
            + "Environment Variables"
            + ChatColor.GRAY
            + ChatColor.BOLD.toString()
            + ": ("
            + ChatColor.DARK_GREEN
            + ChatColor.BOLD.toString()
            + keys.size()
            + ChatColor.GRAY
            + ChatColor.BOLD.toString()
            + ")");

    for (String key : keys) {
      Object value = plugin.getEnv(key);
      String formattedValue = formatValue(value);

      sender.sendMessage(
          ChatColor.GOLD + " ● " + formatKey(key) + ChatColor.GRAY + ": " + formattedValue);
    }
  }

  private String formatValue(Object value) {
    return ChatColor.GREEN + value.toString();
  }

  private String formatKey(String key) {
    return ChatColor.YELLOW + key;
  }
}

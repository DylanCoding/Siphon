package me.dylan.siphon.commands;

import me.dylan.siphon.Siphon;
import me.dylan.siphon.locale.Locale;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SiphonCommand implements CommandExecutor {

    private final Siphon siphon;

    public SiphonCommand(Siphon siphon) {
        this.siphon = siphon;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Locale.PREFIX + Locale.WRONG_ARGS);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "enable":
                siphon.setSiphon(true);
                sender.sendMessage(Locale.PREFIX + Locale.ENABLE);
                break;
            case "disable":
                siphon.setSiphon(false);
                sender.sendMessage(Locale.PREFIX + Locale.DISABLE);
                break;
            case "toggle":
                siphon.setSiphon(!siphon.isSiphon());
                sender.sendMessage(Locale.PREFIX + (siphon.isEnabled() ? Locale.ENABLE : Locale.DISABLE));
                break;
            case "info":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8»&m-------------------------------------------------&r&8«"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7This plugin was made by &6Interuptings/Dylan&7."));
                sender.sendMessage("");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &b&l@Interuptings"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7&lDylan#6678"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8»&m-------------------------------------------------&r&8«"));
                break;
            default:
                sender.sendMessage(Locale.PREFIX + Locale.WRONG_ARGS);
                break;
        }
        return false;
    }
}

package me.dylan.siphon;

import lombok.Getter;
import lombok.Setter;
import me.dylan.siphon.commands.SiphonCommand;
import me.dylan.siphon.listeners.DeathListener;
import me.dylan.siphon.locale.Locale;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Siphon extends JavaPlugin {

    @Getter
    @Setter
    private boolean siphon;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerCommands();
        registerListeners();

        Locale.init(this);

        getLogger().info("Successfully loaded the scenario Siphon!");
    }

    private void registerCommands() {
        getCommand("siphon").setExecutor(new SiphonCommand(this));
    }

    private void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        pluginManager.registerEvents(new DeathListener(this), this);
    }
}

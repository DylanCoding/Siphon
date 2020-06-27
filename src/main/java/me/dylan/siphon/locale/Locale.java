package me.dylan.siphon.locale;

import lombok.experimental.UtilityClass;
import me.dylan.siphon.Siphon;
import org.bukkit.configuration.Configuration;

@UtilityClass
public class Locale {
    public String PREFIX;

    public String WRONG_ARGS;
    public String ENABLE;
    public String DISABLE;

    public void init(Siphon siphon) {
        Configuration configuration = siphon.getConfig();

        PREFIX = configuration.getString("messages.prefix", "&4UHC &8>>&7 ");

        WRONG_ARGS = configuration.getString("messages.args", "&cWrong Arguments! /siphon <enable/disable/toggle>");
        ENABLE = configuration.getString("messages.enabled", "&aSiphon &7has been &aenabled&7!");
        DISABLE = configuration.getString("messages.disabled", "&cSiphon &7has been &cdisabled&7!");
    }
}

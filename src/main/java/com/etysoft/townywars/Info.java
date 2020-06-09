package com.etysoft.townywars;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Info {
    public static void plugin(CommandSender p, TownyWars pl, Plugin towny)
    {
        p.sendMessage(ChatColor.WHITE + "============ " + ChatColor.AQUA + "TownyWars" + ChatColor.WHITE + " ============");
        p.sendMessage(fun.cstring("Version: " + pl.getDescription().getVersion()));
        p.sendMessage(fun.cstring("Author: &bkarlov_m"));
        p.sendMessage(fun.cstring("GitHub: &bhttps://github.com/karlovm/TownyWars"));
        if(pl.isCompatible(towny.getDescription().getVersion()))
        {
            p.sendMessage(fun.cstring(pl.getConfig().getString("msg-compatible")));
        }
        else
        {
            p.sendMessage(fun.cstring(pl.getConfig().getString("msg-nocompatible").replace("%s", towny.getDescription().getVersion())));
        }
        p.sendMessage("==============================");
    }

    public static void help(CommandSender p, TownyWars pl)
    {
        p.sendMessage(ChatColor.WHITE + "============ " + ChatColor.AQUA + fun.cstring(pl.getConfig().getString("ht")) + ChatColor.WHITE + " ============");

        p.sendMessage(fun.cstring(pl.getConfig().getString("ht1")));
        p.sendMessage(fun.cstring(pl.getConfig().getString("ht2")));
        p.sendMessage(fun.cstring(pl.getConfig().getString("ht3")));
        p.sendMessage(fun.cstring(pl.getConfig().getString("ht4")));
        p.sendMessage(fun.cstring(pl.getConfig().getString("ht5")));

    }
}

package ru.pashockerr.MoneyPluginDemo;

import org.bukkit.plugin.java.JavaPlugin;

public class MoneyPluginDemo extends JavaPlugin {
    @Override
    public void onEnable(){
        getLogger().info("MoneyDemoPlugin enabled.");
        getCommand("m").setExecutor(new MoneyPluginDemoCommandExecutor());
    }

    @Override
    public void onDisable(){
        getLogger().info("MoneyDemoPlugin disabled.");
    }
}

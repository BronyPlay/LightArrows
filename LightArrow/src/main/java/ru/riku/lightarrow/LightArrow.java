package ru.riku.lightarrow;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.riku.lightarrow.cmd.LightCustomArrows;
import ru.riku.lightarrow.handlers.CustomHandler;
import ru.riku.lightarrow.handlers.Handler;
import ru.riku.lightarrow.utills.lang.LangConfiguration;
import ru.riku.lightarrow.utills.custom.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class LightArrow extends JavaPlugin {

    private static LightArrow plugin;

    public static LightArrow getInstance() {
        return plugin;
    }

    private static HashMap<String, Item> items = new HashMap<>();

    public static HashMap<String, Item> getItems() {
        return items;
    }

    private static List<Listener> listeners = new ArrayList<>();

    public static List<Listener> getListeners() {
        return listeners;
    }

    private static void loadLangManager() {
        LangConfiguration lcfg = new LangConfiguration();
        lcfg.setUp();
        lcfg.saveLang();
        lcfg.reloadLang();
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        loadLangManager();
        getListeners().add(new Handler());
        getListeners().add(new CustomHandler());
        getInstance() .getCommand("lightarrows").setExecutor(new LightCustomArrows());
        for (String item : getInstance().getConfig().getConfigurationSection("LightArrows").getKeys(false)) {
            String patern = "LightArrows." + item + ".";
            Item mapItem = new Item(
                    getInstance().getConfig().getString(patern + "name"),
                    getInstance().getConfig().getStringList(patern + "lore"),
                    getInstance().getConfig().getString(patern + "meta"),
                    getInstance().getConfig().getString(patern + "color"),
                    getInstance().getConfig().getStringList(patern + "effects"),
                    getInstance().getConfig().getStringList(patern + "flags"));
            getItems().put(item, mapItem);
        }
        for (Listener listener : getListeners()) {
            Bukkit.getPluginManager().registerEvents(listener, getInstance());
        }
        List<String> enable = new ArrayList<>();
        enable.add("                                                                                                                           ");
        enable.add("§6 .              _       .        .                                                                                         ");
        enable.add("§6 /     `   ___. /      _/_      /|    .___  .___    __.  ,  _  /   ____             §fwas created           ");
        enable.add("§6 |     | .'   ` |,---.  |      /  \\   /   \\ /   \\ .'   \\ |  |  |  (                    §fby §6Light§eStudio     ");
        enable.add("§6 |     | |    | |'   `  |     /---'\\  |   ' |   ' |    | `  ^  '  `--.                   §fversion §61.6.0     ");
        enable.add("§6 /---/ /  `---| /    |  \\__/,'      \\ /     /      `._.'  \\/ \\/  \\___.'                     §aActivated!      ");
        enable.add("§6          \\___/                                                                                                          ");
        enable.add("                                                                                                                            ");
        for (String s : enable) {
            Bukkit.getConsoleSender().sendMessage(s);
        }
    }

}

package ru.riku.lightarrow.utills.lang;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class MessageHelper {

    public static String noPerm = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.NoPerm"));
    public static String noArgs = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.NoArgument"));
    public static String noArrowFound = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.NoArrowFound"));
    public static String configReload = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.ConfigReload"));
    public static String langReload = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.LangReload"));
    public static String noConsole = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.NoConsole"));
    public static String noPlayerFound = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.NoPlayerFound"));
    public static String arrowList = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.ArrowList"));
    public static String itemTemplate = ChatColor.translateAlternateColorCodes('&', LangConfiguration.getLangConfiguration().getLang().getString("Messages.ItemTemplate"));
    public static List<String> help = color(LangConfiguration.getLangConfiguration().getLang().getStringList("Messages.Help"));

    private static List<String> color(List<String> msg) {
        List<String> help = new ArrayList<>();
        for (String s : msg) {
            help.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return help;
    }

}

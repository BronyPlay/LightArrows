package ru.riku.lightarrow.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.riku.lightarrow.LightArrow;
import ru.riku.lightarrow.utills.ItemBuilder;
import ru.riku.lightarrow.utills.lang.LangConfiguration;
import ru.riku.lightarrow.utills.lang.MessageHelper;
import ru.riku.lightarrow.utills.custom.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LightCustomArrows implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (sender.isOp()) {
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "list": {
                        List<String> arrays = new ArrayList<>();
                        LightArrow.getItems().forEach((s, item) -> arrays.add(String.format(MessageHelper.itemTemplate, s, ItemBuilder.color(item.getName()))));
                        sender.sendMessage(String.format(MessageHelper.arrowList, arrays.toString()));
                        return true;
                    }
                    case "get": {
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            if (args[1] != null) {
                                String itemname = args[1].toUpperCase();
                                if (LightArrow.getItems().get(itemname) != null) {
                                    ItemBuilder itemBuilder = new ItemBuilder(LightArrow.getItems().get(itemname).getItem());
                                    try {
                                        itemBuilder.setAmount(Integer.parseInt(args[2]));
                                    } catch (ArrayIndexOutOfBoundsException ex) {
                                        itemBuilder.setAmount(1);
                                    }
                                    p.getInventory().addItem(itemBuilder.toItemStack());
                                } else {
                                    sender.sendMessage(MessageHelper.noArrowFound);
                                }
                            } else {
                                sender.sendMessage(MessageHelper.noArgs);
                            }
                        } else {
                            sender.sendMessage(MessageHelper.noConsole);
                        }
                        return true;
                    }
                    case "give": {
                        try {
                            Player getter = Bukkit.getPlayerExact(args[1]);
                            if (getter == null) {
                                sender.sendMessage(MessageHelper.noPlayerFound);
                                return true;
                            }
                            String itemname = args[2].toUpperCase();
                            if (LightArrow.getItems().get(itemname) != null) {
                                ItemBuilder itemBuilder = new ItemBuilder(LightArrow.getItems().get(itemname).getItem());
                                try {
                                    itemBuilder.setAmount(Integer.parseInt(args[3]));
                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    itemBuilder.setAmount(1);
                                }
                                getter.getInventory().addItem(itemBuilder.toItemStack());
                            } else {
                                sender.sendMessage(MessageHelper.noArrowFound);
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            sender.sendMessage(MessageHelper.noArgs);
                        }
                        return true;
                    }
                    case "reload": {
                        LightArrow.getInstance().reloadConfig();
                        LightArrow.getItems().clear();
                        for (String item : LightArrow.getInstance().getConfig().getConfigurationSection("LightArrows").getKeys(false)) {
                            String patern = "LightArrows." + item + ".";
                            Item mapItem = new Item(
                                    LightArrow.getInstance().getConfig().getString(patern + "name"),
                                    LightArrow.getInstance().getConfig().getStringList(patern + "lore"),
                                    LightArrow.getInstance().getConfig().getString(patern + "meta"),
                                    LightArrow.getInstance().getConfig().getString(patern + "color"),
                                    LightArrow.getInstance().getConfig().getStringList(patern + "effects"),
                                    LightArrow.getInstance().getConfig().getStringList(patern + "flags"));
                            LightArrow.getItems().put(item, mapItem);
                        }
                        LangConfiguration.getLangConfiguration().reloadLang();
                        sender.sendMessage(MessageHelper.langReload);
                        sender.sendMessage(MessageHelper.configReload);
                        return true;
                    }
                    default: {
                        sender.sendMessage(MessageHelper.noArgs);
                        return true;
                    }
                }
            } else {
                for (String s : MessageHelper.help) {
                    sender.sendMessage(s);
                }
            }
        } else {
         sender.sendMessage(MessageHelper.noPerm);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> argList = new ArrayList<>();

        if (sender.isOp()) {
            if (args.length == 1) {
                argList.add("get");
                return argList.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
            }
            if (args.length == 2) {
                if (args[0].equals("get")) {
                    List<String> items = new ArrayList<>();
                    LightArrow.getItems().forEach((s, item) -> items.add(s));
                    return items;
                }
            }
        }
        return null;
    }

}
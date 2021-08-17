package ru.riku.lightarrow.utills.lang;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.riku.lightarrow.LightArrow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LangConfiguration {

    private static LangConfiguration langConfiguration = new LangConfiguration();


    private File langFile = null;
    private FileConfiguration lang = null;

    public void setUp() {
        if (langFile == null) {
            langFile = new File(LightArrow.getInstance().getDataFolder(), "lang.yml");
        }
        lang = YamlConfiguration.loadConfiguration(langFile);
        if (!langFile.exists()) {
            try {
                lang = YamlConfiguration.loadConfiguration(langFile);
                lang.set("Messages.NoPerm", "&f<&8&l◆&f> У вас недостаточно прав для использования команд &7&lLightArrows&f.");
                lang.set("Messages.NoArgument", "&f<&8&l◆&f> Произошла ошибка. Проверьте правильность аргументов.");
                lang.set("Messages.NoArrowFound", "&f<&8&l◆&f> Произошла ошибка. Данной стрелы не существует.");
                lang.set("Messages.NoConsole", "&f<&8&l◆&f> Команда только для игрока.");
                lang.set("Messages.NoPlayerFound", "&f<&8&l◆&f> Игрок не в сети.");
                lang.set("Messages.ConfigReload", "&f<&8&l◆&f> Файл конфигурации успешно перезагружен.");
                lang.set("Messages.LangReload", "&f<&8&l◆&f> Файл локализации успешно перезагружен.");
                lang.set("Messages.ArrowList", "&f<&8&l◆&f> Доступные стрелы: §7%s");
                lang.set("Messages.ItemTemplate", "&7%s &7(%s&7)");
                List<String> help = new ArrayList<>();
                help.add("&8&l|&f Помощь по плагину &cLightArrows");
                help.add("&8&l|&f Просмотреть список стрел:&7 /la list");
                help.add("&8&l|&f Выдать себе стрелу:&7 /la get &8<Название> <Кол-во>");
                help.add("&8&l|&f Выдать игроку стрелу:&7 /la give &8<Ник> <Название> <Кол-во>");
                help.add("&8&l|&f Перезагрузить конфиг:&7 /la reload");
                help.add("&8&l| &cLightArrows &fby &5&lRiku");
                lang.set("Messages.Help", help);
                lang.save(langFile);
                Bukkit.getServer().getConsoleSender().sendMessage("[LightArrows] Файл локализации успешно создан.");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("[LightArrows] Произошла ошибка при создании файла локализации.");
            }
        }
    }

    public FileConfiguration getLang() {
        if (lang == null) {
            reloadLang();
        }
        return lang;
    }

    public void reloadLang() {
        langFile = new File(LightArrow.getInstance().getDataFolder(), "lang.yml");
        lang = YamlConfiguration.loadConfiguration(langFile);
    }

    public void saveLang() {
        try {
            getLang().save(langFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Ошибка в сохранении файла локализации: " + langFile);
        }
    }

    public static LangConfiguration getLangConfiguration() {
        return langConfiguration;
    }

}

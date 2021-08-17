package ru.riku.lightarrow.utills.custom;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.riku.lightarrow.utills.ItemBuilder;

import java.util.List;

public class Item {

    private String name;
    private List<String> lore;
    private String meta;
    private List<String> effects;
    private String color;
    private List<String> attributes;

    private ItemStack item;

    public Item(String name, List<String> lore, String meta, String color, List<String> effects, List<String> attributes) {
        this.name = name;
        this.lore = lore;
        this.meta = meta;
        this.effects = effects;
        this.color = color;
        this.attributes = attributes;

        ItemBuilder itemBuilder = new ItemBuilder(Material.TIPPED_ARROW);
        itemBuilder.setName(ItemBuilder.color(name));
        itemBuilder.setLore(ItemBuilder.color(lore));
        itemBuilder.setPotionColor(color);
       if (effects != null) {
             for (String effect : effects) {
                   String[] args = effect.split(",");
                   PotionEffectType effectType = PotionEffectType.getByName(args[0].toUpperCase());
                  int duration = Integer.parseInt(args[1]);
                  int amplifier = Integer.parseInt(args[2]);
                  itemBuilder.addPotionEffect(new PotionEffect(effectType, duration, amplifier, true));
             }
        }
       if (attributes !=null) {
           for (String attribute : attributes) {
               String itemFlag = attribute;
               itemBuilder.addItemFlag(itemFlag);
           }
       }
        this.item = itemBuilder.toItemStack();
    }

    public String getName() {
        return name;
    }
    public List<String> getLore() {
        return lore;
    }
    public String getMeta() {
        return meta;
    }
    public List<String> getEffects() {
        return effects;
    }
    public ItemStack getItem() {
        return item;
    }
    public ItemStack getColor() {return item;}
}

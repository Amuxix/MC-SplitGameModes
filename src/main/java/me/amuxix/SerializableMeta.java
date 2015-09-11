package me.amuxix;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Amuxix on 12/08/2015.
 */
public class SerializableMeta implements Serializable {
    private static final long serialVersionUID = 2959596957816114050L;
    HashMap<SerializableEnchant, Integer> enchantMap = new HashMap<>();
    private Material type;
    private Boolean hasDisplayName;
    private Boolean hasLore;
    private Boolean hasItemFlags;
    private Boolean hasEnchants;
    //private Boolean unbreakable;
    private String displayName = null;
    private List<String> lore = null;
    private Set<ItemFlag> itemFlags = null;

    public SerializableMeta(ItemMeta meta, Material type) {
        this.type = type;
        this.hasDisplayName = meta.hasDisplayName();
        if (hasDisplayName) {
            this.displayName = meta.getDisplayName();
        }
        this.hasLore = meta.hasLore();
        if (hasLore) {
            this.lore = meta.getLore();
        }
        this.hasItemFlags = !meta.getItemFlags().isEmpty();
        if (hasItemFlags) {
            this.itemFlags = meta.getItemFlags();
        }
        this.hasEnchants = meta.hasEnchants();
        if (hasEnchants) {
            Map<Enchantment, Integer> enchants = meta.getEnchants();
            for (Enchantment enchantment : enchants.keySet()) {
                this.enchantMap.put(new SerializableEnchant(enchantment), enchants.get(enchantment));
            }
        }
        //this.unbreakable = meta.spigot().isUnbreakable();
    }

    public ItemMeta toItemMeta() {
        ItemFactory itemFactory = SplitGamemodes.instance.getServer().getItemFactory();
        ItemMeta itemMeta = itemFactory.getItemMeta(type);
        if (hasDisplayName) {
            itemMeta.setDisplayName(displayName);
        }
        if (hasLore) {
            itemMeta.setLore(lore);
        }
        if (hasItemFlags) {
            for (ItemFlag itemFlag : itemFlags) {
                itemMeta.addItemFlags(itemFlag);
            }
        }
        if (hasEnchants) {
            for (Map.Entry<SerializableEnchant, Integer> enchantEntry : enchantMap.entrySet()) {
                itemMeta.addEnchant(enchantEntry.getKey().toEnchant(), enchantEntry.getValue(), true);
            }
        }
        return itemMeta;
    }
}

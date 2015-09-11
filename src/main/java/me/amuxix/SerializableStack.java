package me.amuxix;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.io.Serializable;

/**
 * Created by Amuxix on 12/08/2015.
 */
public class SerializableStack implements Serializable {
    private static final long serialVersionUID = 448151802126499903L;
    private Material type;
    private int amount;
    private byte data;
    private short durability;
    private SerializableMeta meta;

    public SerializableStack(ItemStack itemStack) {
        this.type = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.data = itemStack.getData().getData();
        this.durability = itemStack.getDurability();
        this.meta = new SerializableMeta(itemStack.getItemMeta(), type);
    }

    public ItemStack toItemStack(){
        ItemStack itemStack = new ItemStack(type, amount, durability);
        itemStack.setData(new MaterialData(type, data));
        itemStack.setItemMeta(meta.toItemMeta());
        return itemStack;
    }
}

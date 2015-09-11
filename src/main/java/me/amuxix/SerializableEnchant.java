package me.amuxix;

import org.bukkit.enchantments.Enchantment;

import java.io.Serializable;

/**
 * Created by Amuxix on 12/08/2015.
 */
public class SerializableEnchant implements Serializable {
    private static final long serialVersionUID = 5730934930449634992L;
    private final int id;

    public SerializableEnchant(Enchantment enchantment) {
        this.id = enchantment.getId();
    }

    public Enchantment toEnchant() {
        return Enchantment.getById(this.id);
    }
}

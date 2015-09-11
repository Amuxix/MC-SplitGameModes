package me.amuxix;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Amuxix on 22/08/2015.
 */
public class PlayerInfo implements Serializable{
    private static final long serialVersionUID = 4423506297068335920L;
    private UUID playerID;
    private GameMode gameMode;
    private List<SerializableStack> inventoryContents;
    private SerializableLocation location;
    private boolean isFlying;

    public PlayerInfo(Player player) {
        this.playerID = player.getUniqueId();
        this.gameMode = player.getGameMode();
        if (gameMode != GameMode.SPECTATOR) {
            this.inventoryContents = new ArrayList<>();
            for (ItemStack content : player.getInventory().getContents()) {
                if (content == null) {
                    inventoryContents.add(null);
                } else {
                    inventoryContents.add(new SerializableStack(content));
                }
            }
            this.isFlying = player.isFlying();
        }
        this.location = new SerializableLocation(player.getLocation());
    }

    public void set() {
        Player player = Bukkit.getPlayer(playerID);
        player.teleport(location.toLocation());
        if (gameMode != GameMode.SPECTATOR) {
            ItemStack[] contents = new ItemStack[inventoryContents.size()];
            for (int i = 0; i < inventoryContents.size(); i++) {
                SerializableStack serializableStack = inventoryContents.get(i);
                if (serializableStack == null) {
                    contents[i] = null;
                } else {
                    contents[i] = serializableStack.toItemStack();
                }
                player.getInventory().setContents(contents);
            }
            player.setFlying(isFlying);
        }
    }
}

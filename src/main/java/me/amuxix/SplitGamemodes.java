package me.amuxix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

/**
 * Created by Amuxix on 22/08/2015.
 */
public class SplitGamemodes extends JavaPlugin implements Listener {
    private static final String FILE_NAME = "playerInfoMap.dat";
    private static Map<List<Object>, PlayerInfo> playerInfoMap;
    public static SplitGamemodes instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        getDataFolder().mkdirs();
        playerInfoMap = new HashMap<>();
        //Load map
        loadMap();
    }

    @Override
    public void onDisable() {
        saveMap();
    }

    private void loadMap() {
        File infoMap = new File(getDataFolder(), FILE_NAME);
        if (infoMap.exists() == false) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(infoMap);
            ObjectInputStream in = new ObjectInputStream(fileInputStream) {
                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                    return Class.forName(desc.getName(), false, SplitGamemodes.instance.getClassLoader());
                }
            };
            playerInfoMap = Collections.synchronizedMap((Map<List<Object>, PlayerInfo>) in.readObject());
            in.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMap() {
        File newSaveFile = new File(getDataFolder(), FILE_NAME + "_new");
        File oldSaveFile = new File(getDataFolder(), FILE_NAME);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(newSaveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(playerInfoMap);
            out.close();
            fileOutputStream.close();
            if (oldSaveFile.exists()) oldSaveFile.delete();
            newSaveFile.renameTo(oldSaveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        if (event.getPlayer().equals(Bukkit.getPlayer(UUID.fromString("a398585d-b180-457e-a018-fcd5d04a18fc")))) {
            List<Object> key = new ArrayList<>();
            Player player = event.getPlayer();
            final UUID playerID = player.getUniqueId();
            key.add(playerID);
            key.add(player.getGameMode());
            playerInfoMap.put(key, new PlayerInfo(player));
            List<Object> newGameModeKey = new ArrayList<>();
            newGameModeKey.add(playerID);
            newGameModeKey.add(event.getNewGameMode());
            if (playerInfoMap.containsKey(newGameModeKey)) {
                playerInfoMap.get(newGameModeKey).set();
            }
        }

    }

}

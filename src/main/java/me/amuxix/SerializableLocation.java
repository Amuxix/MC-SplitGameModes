package me.amuxix;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Amuxix on 22/08/2015.
 */
public class SerializableLocation implements Serializable {
    private static final long serialVersionUID = 4761424885709508882L;
    private UUID worldID;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public SerializableLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
        worldID = location.getWorld().getUID();
        Bukkit.getLogger().info("Saved location: " + x + " y: " + y + "z: " + z);
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(worldID), x, y, z, pitch, yaw);
    }
}

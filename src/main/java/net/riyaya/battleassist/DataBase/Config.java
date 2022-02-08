package net.riyaya.battleassist.DataBase;

import net.riyaya.battleassist.BattleAssist;
import net.riyaya.battleassist.Utils.WorldBorderStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private FileConfiguration config;

    public Config(FileConfiguration config) {
        this.config = config;
    }

    public boolean isAllowTeleportLobby() {
        return config.getBoolean("lobby.teleport-lobby");
    }

    public Location getSpawnLocation() {
        return new Location(
                Bukkit.getWorld(config.getString("lobby.spawn-location.world"))
                , config.getDouble("lobby.spawn-location.x")
                , config.getDouble("lobby.spawn-location.y")
                , config.getDouble("lobby.spawn-location.z")
                , config.getInt("lobby.spawn-location.yaw")
                , config.getInt("lobby.spawn-location.pitch")
        );
    }

    public boolean isAllowDeadSpectator() {
        return config.getBoolean("game.dead-spectator.enable");
    }

    public Location getStartLocation() {
        return new Location(
                Bukkit.getWorld(config.getString("game.start.world"))
                , config.getDouble("game.start.x")
                , config.getDouble("game.start.y")
                , config.getDouble("game.start.z")
                , config.getInt("game.start.yaw")
                , config.getInt("game.start.pitch")
        );
    }

    public Location getDeadSpectatorLocation() {
        return new Location(
                Bukkit.getWorld(config.getString("game.dead-spectator.world"))
                , config.getDouble("game.dead-spectator.x")
                , config.getDouble("game.dead-spectator.y")
                , config.getDouble("game.dead-spectator.z")
                , config.getInt("game.dead-spectator.yaw")
                , config.getInt("game.dead-spectator.pitch")
        );
    }

    public int getRemoveElytraSeconds() {
        return config.getInt("game.remove-elytra-seconds");
    }

    public int getMaxBorderSize() {
        return config.getInt("world-border.max-size");
    }

    public int getStartBorderShrinkageSeconds() {
        return config.getInt("world-border.border-shrinkage-start-seconds");
    }

    public WorldBorderStatus getFirstBorder() {
        return new WorldBorderStatus()
                .setShrinkageStartSeconds(config.getInt("world-border.first-size.seconds"))
                .setWaitSeconds(config.getInt("world-border.first-size.wait-seconds"))
                .setSize(config.getInt("world-border.first-size.size"));
    }

    public WorldBorderStatus getSecondBorder() {
        return new WorldBorderStatus()
                .setShrinkageStartSeconds(config.getInt("world-border.second-size.seconds"))
                .setWaitSeconds(config.getInt("world-border.second-size.wait-seconds"))
                .setSize(config.getInt("world-border.second-size.size"));
    }

    public WorldBorderStatus getThirdBorder() {
        return new WorldBorderStatus()
                .setShrinkageStartSeconds(config.getInt("world-border.third-size.seconds"))
                .setWaitSeconds(config.getInt("world-border.third-size.wait-seconds"))
                .setSize(config.getInt("world-border.third-size.size"));
    }

    public int getMaxTime() {
        return BattleAssist.config.getRemoveElytraSeconds()
                + BattleAssist.config.getStartBorderShrinkageSeconds()
                + BattleAssist.config.getFirstBorder().getShrinkageStartSeconds()
                + BattleAssist.config.getFirstBorder().getWaitSeconds()
                + BattleAssist.config.getSecondBorder().getShrinkageStartSeconds()
                + BattleAssist.config.getSecondBorder().getWaitSeconds()
                + BattleAssist.config.getThirdBorder().getShrinkageStartSeconds()
                + BattleAssist.config.getThirdBorder().getWaitSeconds();
    }

}

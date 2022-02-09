package net.riyaya.battleassist.Listener;

import net.riyaya.battleassist.BattleAssist;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(BattleAssist.config.getSpawnLocation());

        if(BattleAssist.gameStatus.getStatus()) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }else {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }

        if(!event.getPlayer().isOp()) {
            return;
        }
        event.getPlayer().sendMessage(ChatColor.GREEN + "§l[BattleAssist] このプラグインを利用していただきありがとうございます");
        event.getPlayer().sendMessage(ChatColor.YELLOW + "§l[BattleAssist] バグや不具合等がありましたらTwitterでの@riyaya1528に連絡をください");
    }
}

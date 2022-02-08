package net.riyaya.battleassist.Listener;

import net.riyaya.battleassist.BattleAssist;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeadListener implements Listener {

    @EventHandler
    public void onDeadPlayer(PlayerDeathEvent event) {

        if(!event.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }
        event.setCancelled(true);
        event.getEntity().setHealth(event.getEntity().getMaxHealth());
        event.getEntity().teleport(BattleAssist.config.getSpawnLocation());
        if(!BattleAssist.gameStatus.getStatus()) {
            return;
        }
        Player player = event.getEntity();

        if(BattleAssist.config.isAllowDeadSpectator()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(BattleAssist.config.getDeadSpectatorLocation());
        }
    }
}

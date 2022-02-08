package net.riyaya.battleassist.Commands;

import net.riyaya.battleassist.BattleAssist;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            return true;
        }

        Player player = (Player) sender;

        if(args[0].equals("start")) {
            if(!player.hasPermission("BattleAssist.game.start")) {
                player.sendMessage("You don't have permission to do this.");
                return true;
            }
            BattleAssist.start();
        }else if(args[0].equals("stop")) {
            if(!player.hasPermission("BattleAssist.game.stop")) {
                player.sendMessage("You don't have permission to do this.");
                return true;
            }
            BattleAssist.stop();
        }
        return true;
    }
}

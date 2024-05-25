package it.samuconfaa.betterrepair;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FixCommand implements CommandExecutor {
    private final BetterRepair plugin;


    public FixCommand(BetterRepair plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
        if(args.length == 1){
            if(sender.hasPermission("fix.reload")){
                ConfigurationManager.reloadConfig();
                sender.sendMessage("Config ricaricato con successo");
            }else {sender.sendMessage("NO perms");}
        }else if(args.length == 0){
            if(sender instanceof Player){
                Player player = (Player) sender;
                plugin.getFixListener().openGUI(player);
                return true;
            } else {sender.sendMessage("Solo i giocatori possono fare questo comando!");}
        }
        return false;
    }
}
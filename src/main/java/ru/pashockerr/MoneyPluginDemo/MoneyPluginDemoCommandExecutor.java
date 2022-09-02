package ru.pashockerr.MoneyPluginDemo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MoneyPluginDemoCommandExecutor implements CommandExecutor {
    private HashMap<String, Double> playersBalance;

    MoneyPluginDemoCommandExecutor(){
        playersBalance = new HashMap<String, Double>();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        /*
            Commands:
                pay - pay money to player
                balance - get balance
                setMoney(only for OP) - set money to player
                addPlayer(only for OP) - create bank account
         */


        String pluginCommand = null;
        if (label.equals("m")) {
            pluginCommand = args[0];
            Player player;
            Player player1;
            World world;
            Location location;
            Location location1;

            if (pluginCommand.equals("addPlayer")) {
                var playerName = args[1];
                player = (Player) commandSender;
                if (Bukkit.getOnlinePlayers().stream().anyMatch(o -> o.getName().equals(playerName)) && player.isOp() && !playersBalance.containsKey(playerName)) {
                    playersBalance.put(playerName, 0.0);
                    player.sendMessage("Successfully created bank account for " + playerName + ".");
                } else {
                    player.sendMessage("There's no player with that name on this server or he's already has bank account.");
                }
                return true;
            }

            if (pluginCommand.equals("pay")) {
                player = (Player) commandSender;
                var playerName = args[1];
                double transactionMoney = Double.parseDouble(args[2]);
                if (playersBalance.containsKey(player.getName())) {
                    if (playersBalance.get(player.getName()) >= transactionMoney) {
                        if (playersBalance.containsKey(playerName)) {
                            playersBalance.put(player.getName(), playersBalance.get(player.getName()) - transactionMoney);
                            playersBalance.put(playerName, playersBalance.get(playerName) + transactionMoney);
                            player.sendMessage("You successfully paid " + transactionMoney + " to " + playerName + ".");
                        } else {
                            player.sendMessage("Player with that name have no bank account.");
                        }
                    } else {
                        player.sendMessage("You have not enough money for that transaction.");
                    }
                } else {
                    player.sendMessage("You have no bank account. Contact server OP to create it.");
                }
                return true;
            }

            if (pluginCommand.equals("balance")){
                player = (Player) commandSender;
                if(playersBalance.containsKey(player.getName())){
                    player.sendMessage("Your balance is : " + playersBalance.get(player.getName()));
                }
                else{
                    player.sendMessage("You have no bank account. Contact server OP to create it.");
                }
                return true;
            }

            if(pluginCommand.equals("setMoney")){
                player = (Player) commandSender;
                String playerName = args[1];
                double money = Double.parseDouble(args[2]);
                if(player.isOp() && playersBalance.containsKey(playerName)){
                    playersBalance.put(playerName, money);
                    player.sendMessage("You successfully setted money to " + money + " for player " + playerName + ".");
                }
                else{
                    player.sendMessage("There's no players with that name.");
                }
                return true;
            }

        }

        return false;
    }
}

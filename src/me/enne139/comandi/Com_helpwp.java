package me.enne139.comandi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Com_helpwp implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player){                // se chi esegue il comando è un player
        Player player = (Player) commandSender;               // ottiene il player

            player.sendMessage(ChatColor.YELLOW + "Commandi ---------------------------------------------");
            player.sendMessage("/wpadd [nome] <privato/pubblico>");
            player.sendMessage("/wpdel [privato/pubblico] [nome] [conferma_nome]");
            player.sendMessage("/wplist <privato/pubblico> <info>");
            player.sendMessage("/wphelp");
            player.sendMessage(ChatColor.YELLOW + "-----------------------------------------------------");

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();              // creazione lista di auto completamento

        return arg;
    }
}

package me.enne139.comandi;

import me.enne139.PluginMain;
import me.enne139.ogg.Waiponint;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Com_listwp implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player ) {                // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottine il player
            String file = new String();
            boolean lungo = false;                              // se il formato di autput deve essere lungo

            if ( strings.length>2 ) {                           // le ha più di due argomneto ritorna la sintassi
                player.sendMessage(ChatColor.YELLOW + "/listwp <privato/publico> <info>");
                return true;
            }

            if ( strings.length==0 ) {                          // se non ha argomenti
                file = player.getUniqueId().toString();         // imposta il nome del file come l'uudi del utente

            } else if ( strings.length>=1 ) {                   // se piu di un argomento
                if ( strings[0].equals("privato") ) {           // se l'argomento 1 è "privato"
                    file = player.getUniqueId().toString();     // imposta il nome del file come l'uudi del utente

                } else if ( strings[0].equals("publico") ) {    // se l'argomento 1 è "publico"
                    file = "GLOBAL";                            // imposta il nome del file come GLOBALE
                } else {                                        // se non è nessuno dei due manda la sintassi
                    player.sendMessage(ChatColor.YELLOW + "/listwp <privato/publico> <info>");
                    return true;
                }
            }

            if ( strings.length==2 ) {                         // se ha 2 argoemnti
                if ( strings[1].equals("info") ) {             // se l'agomneto 2 è "info" imposta formato lungo
                    lungo = true;
                } else {                                       // se non è uguale a "info" manda la sinstassi
                    player.sendMessage(ChatColor.YELLOW + "/listwp <privato/publico> <info>");
                    return true;
                }
            }

            List<Waiponint> wp = PluginMain.leggi_waypoint( file);      // legge i waypoint

            player.sendMessage(ChatColor.GOLD + "List waypoint -----------------");
            if ( lungo ) {                                              // se il formato è lungo

                for ( int i=0; i<wp.size(); i++ ) {                     // stampa ogni waypoint
                    player.sendMessage( ChatColor.GREEN + wp.get(i).toString() );
                }

            } else {                                                    // se il formato è corto

                for ( int i=0; i<wp.size(); i++ ) {                     // stampa ogni waypoint
                    player.sendMessage(ChatColor.GREEN + wp.get(i).nome );
                }

            }
            player.sendMessage(ChatColor.GOLD + "-------------------------------");


        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();              // creazione lista di autocompletamento

        if ( commandSender instanceof Player ) {            // se chi esegue il comando è un player

            if ( strings.length == 1 ) {                    // se che un argomento aggiunte privato e publico
                arg.add("privato");
                arg.add("publico");
            } else if ( strings.length == 2 ) {             // se ci sono 2 argomenti aggiunge info
                arg.add("info");
            }

        }

        return arg;                                         // ritorna la lista con le possibile scelte
    }
}

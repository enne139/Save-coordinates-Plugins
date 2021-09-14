package me.enne139.comandi;

import me.enne139.PluginMain;
import me.enne139.ogg.Gruppo;
import me.enne139.ogg.Waypoint;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Com_wplist implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player ) {                // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottiene il player
            String file = new String();
            boolean lungo = false;                              // se il formato di output deve essere lungo


            if ( strings.length>2 ) {                           // le ha più di due argomento ritorna la sintassi
                player.sendMessage(ChatColor.YELLOW + "/listwp <privato/pubblico> <info>");
                return true;
            }

            if ( strings.length==0 ) {                          // se non ha argomenti
                file = player.getUniqueId().toString();         // imposta il nome del file come l'uudi del utente

            } else if ( strings.length>=1 ) {                   // se piu di un argomento
                if ( strings[0].equals("privato") ) {           // se l' argomento 1 è "privato"
                    file = player.getUniqueId().toString();     // imposta il nome del file come l'uudi del utente

                } else if ( strings[0].equals("pubblico") ) {    // se l' argomento 1 è "pubblico"
                    file = "GLOBAL";                            // imposta il nome del file come GLOBALE
                } else {                                            // se non è nessuno dei due manda la sintassi
                    Gruppo salvGruppo = Gruppo.get_gruppo( strings[0]);                            // ottiene l' oggetto del gruppo
                    if ( !Objects.isNull( salvGruppo) ) {                                          // se l' oggetto non è nullo
                        if ( !salvGruppo.puo_aggiungere( player.getName()) ) {                     // se il player non può aggiunge wp
                            player.sendMessage(ChatColor.RED + "gruppo esistente");
                            return true;
                        }
                        file = "#" + salvGruppo.nome;                                               // imposta l' indirizzo del file

                    } else {                                                                        // se è un valore non valido manda la sintassi
                        player.sendMessage(ChatColor.YELLOW + "/wpget [privato/pubblico] [nome]");
                        return true;
                    }
                }
            }

            if ( strings.length==2 ) {                         // se ha 2 argomento
                if ( strings[1].equals("info") ) {             // se l' argomento 2 è "info" imposta formato lungo
                    lungo = true;
                } else {                                       // se non è uguale a "info" manda la sintassi
                    player.sendMessage(ChatColor.YELLOW + "/wpget [privato/pubblico] [nome]");
                    return true;
                }
            }

            List<Waypoint> wp = PluginMain.leggi_waypoints( file);      // legge i waypoint

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

        List<String> arg = new ArrayList<String>();              // creazione lista di auto completamento
        Player player = (Player) commandSender;                // ottiene il player
        List<Gruppo> gruppi;                                   // lista di gruppi

        if ( commandSender instanceof Player ) {            // se chi esegue il comando è un player

            if ( strings.length == 1 ) {                    // se che un argomento aggiunte privato e pubblico
                arg.add("privato");
                arg.add("pubblico");

                gruppi = Gruppo.get_grup_wp_add( player.getName());// ottiene i gruppi in cui puo aggiungere wp

                for ( int i=0; i<gruppi.size(); i++) {
                    arg.add( gruppi.get(i).nome );
                }


            } else if ( strings.length == 2 ) {             // se ci sono 2 argomenti aggiunge info
                arg.add("info");
            }

        }

        return arg;                                         // ritorna la lista con le possibile scelte
    }
}

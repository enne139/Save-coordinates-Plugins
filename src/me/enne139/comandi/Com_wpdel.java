package me.enne139.comandi;

import me.enne139.PluginMain;
import me.enne139.ogg.Waypoint;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Com_wpdel implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ( commandSender instanceof Player) {                     // se chi esegue il comando è un player
            Player player = (Player) commandSender;                 // ottiene il player
            String file;

            if ( strings.length==0 ) {                          // se ci sono 0 argomenti
                player.sendMessage(ChatColor.YELLOW + "/wpdel [privato/pubblico] [nome] [conferma_nome]");
                return true;
            }

            if ( strings.length!=3 ) {                              // se non ha 3 argomenti manda la sintassi
                player.sendMessage(ChatColor.YELLOW + "/wpdel [privato/pubblico] [nome] [conferma_nome]");
                return true;
            }

            if ( strings[0].equals("pubblico") ) {                   // controlla sei il primo argomento è "pubblico"
                file = "GLOBAL";                                    // imposta il nome del file come GLOBAL
            } else if ( strings[0].equals("privato") ){             // controlla sei il primo argomento è "privato"
                file = player.getUniqueId().toString();             // imposta il nome del file come l'uudi del utente
            } else {                                                // se non è nessuno dei due manda la sintassi
                player.sendMessage(ChatColor.YELLOW + "/wpdel [privato/pubblico] [nome] [conferma_nome]");
                return true;
            }


            if ( !strings[1].equals( strings[2] ) ) {               // controlla che il 2 e il 3 argomento nono son uguali
                player.sendMessage(ChatColor.YELLOW + "/wpdel [privato/pubblico] [nome] [conferma_nome]");
                return true;
            }

            String nome = strings[1];                               // nome del waypoint da eliminare
            boolean trovato = false;
            String val;

            List<Waypoint> p = PluginMain.leggi_waypoints( file);   // ottiene i waypoint salvati
            for ( int i=0; i<p.size(); i++) {                       // scorre la lista
                val = (p.get(i)).nome;
                if ( val.equals(nome) ) {                           // se trova il waypoint
                    trovato = true;
                    break;
                }
            }

            if ( trovato ) {                                        // se esiste

                boolean ess = PluginMain.cancella_waypoint( file, nome);;   // prova a cancellarlo
                if ( ess ) {                                        // se riesce
                    player.sendMessage(ChatColor.GREEN + "waypoint rimosso");
                } else {                                            // se non riesce
                    player.sendMessage(ChatColor.RED + "rimozione waypoint fallita");
                }

            } else {                                                // se non esiste
                player.sendMessage(ChatColor.RED + "waypoin non esistente");
            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();                     // creazione lista di auto completamento

        if ( commandSender instanceof Player) {                    // se chi esegue il comando è un player

            Player player = (Player) commandSender;                // ottiene il player
            List<Waypoint> waypoints;                            // lista di waypoint
            String file;

            if ( strings.length == 1 ) {                           // se c'è solo un argomento
                arg.add( "privato");                               // aggiunge valori alla lista
                arg.add( "pubblico");
                return arg;                                        // ritorna la lista con le possibile scelte

            } else if ( strings.length == 2) {                     // se ci sono 2 argomenti

                String tipo = strings[0];                          // tipologia waypoint

                if ( tipo.equals("privato") ) {                    // se è privato
                    file = player.getUniqueId().toString();        // imposta il nome del file come l'uudi del utente

                } else if ( tipo.equals("pubblico") ) {            // se è pubblico
                    file = "GLOBAL";                               // imposta il nome del file come GLOBAL

                } else {                                           // se e un valore non valido
                    arg.add( "errore_sintassi");
                    return arg;                                    // ritorna la lista con le possibile scelte
                }

                waypoints = PluginMain.leggi_waypoints( file);

                for ( int i=0; i< waypoints.size(); i++) {
                    arg.add( waypoints.get(i).nome );
                }

            }

        }

        return arg;                                                // ritorna la lista con le possibile scelte
    }
}

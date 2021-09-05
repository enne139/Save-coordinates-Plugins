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

public class Com_delwp implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ( commandSender instanceof Player) {                     // se chi esegue il comando è un player
            Player player = (Player) commandSender;                 // ottine il player
            String file;

            if ( strings.length!=3 ) {                              // se non ha 3 argomenti manda la sintassi
                player.sendMessage(ChatColor.YELLOW + "/delwp [privato/publico] [nome] [conferma_nome]");
                return true;
            }

            if ( strings[0].equals("publico") ) {                   // controlla sei il primo argomento è "publico"
                file = "GLOBAL";                                    // imposta il nome del file come GLOBAL
            } else if ( strings[0].equals("privato") ){             // controlla sei il primo argomento è "privato"
                file = player.getUniqueId().toString();             // imposta il nome del file come l'uudi del utente
            } else {                                                // se non è nessuno dei due manda la sintassi
                player.sendMessage(ChatColor.YELLOW + "/delwp [privato/publico] [nome] [conferma_nome]");
                return true;
            }


            if ( !strings[1].equals( strings[2] ) ) {               // controlla che il 2 e il 3 argoemnto nono son uguali
                player.sendMessage(ChatColor.YELLOW + "/delwp [privato/publico] [nome] [conferma_nome]");
                return true;
            }

            String nome = strings[1];                               // nome del waypoint da eliminare
            boolean trovato = false;
            String val;

            List<Waiponint> p = PluginMain.leggi_waypoint( file);   // ottine i waypoint salvati
            for ( int i=0; i<p.size(); i++) {                       // scorre la lista
                val = (p.get(i)).nome;
                if ( val.equals(nome) ) {                           // se trova il waypoint
                    trovato = true;
                    break;
                }
            }

            if ( trovato ) {                                        // se esisite

                boolean ess = PluginMain.cancella_waypoint( file, nome);;   // prova a cancelarlo
                if ( ess ) {                                        // se riesce
                    player.sendMessage(ChatColor.GREEN + "waypoint rimosso");
                } else {                                            // se non riesce
                    player.sendMessage(ChatColor.RED + "rimozione waypoint fallita");
                }

            } else {                                                // se non esite
                player.sendMessage(ChatColor.RED + "waypoin non esistente");
            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();                     // creazione lista di autocompletamento

        if ( commandSender instanceof Player) {                    // se chi esegue il comando è un player

            Player player = (Player) commandSender;                // ottine il player
            List<Waiponint> waiponints;                            // lista di wayponit
            String file;

            if ( strings.length == 1 ) {                           // se c'è solo un argomento
                arg.add( "privato");                               // aggiunge valoria alla lista
                arg.add( "publico");
                return arg;                                        // ritorna la lista con le possibile scelte

            } else if ( strings.length == 2) {                     // se ci sono 2 argoemnti

                String tipo = strings[0];                          // tipologia waypoint

                if ( tipo.equals("privato") ) {                    // se è privato
                    file = player.getUniqueId().toString();        // imposta il nome del file come l'uudi del utente

                } else if ( tipo.equals("publico") ) {             // se è publico
                    file = "GLOBAL";                               // imposta il nome del file come GLOBAL

                } else {                                           // se e un valore non valido
                    arg.add( "errrore_sinatassi");
                    return arg;                                    // ritorna la lista con le possibile scelte
                }

                waiponints = PluginMain.leggi_waypoint( file);

                for ( int i=0; i< waiponints.size(); i++) {
                    arg.add( waiponints.get(i).nome );
                }

            }

        }

        return arg;                                                // ritorna la lista con le possibile scelte
    }
}

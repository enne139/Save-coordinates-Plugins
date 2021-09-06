package me.enne139.comandi;

import me.enne139.PluginMain;
import me.enne139.ogg.Waypoint;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class Com_addwp implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player) {                 // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottiene il player
            Location pos = player.getLocation();                // ottiene la sua posizione
            String file = new String();

            if ( strings.length==0 ) {                          // se ci sono 0 argomenti
                player.sendMessage(ChatColor.YELLOW + "/addwp [nome] <privato/pubblico>");
                return true;
            }

            if ( strings.length>2 ) {                           // se ha più di due argomenti manda la sintassi
                player.sendMessage(ChatColor.YELLOW + "/addwp [nome] <privato/pubblico>");
                return true;
            }

            if ( strings.length==2 ) {                          // se ha due argomenti imposta il file
                if ( strings[1].equals("pubblico") ) {           // se il secondo argomento è "pubblico"
                    file = "GLOBAL";                            // imposta il nome del file come GLOBAL
                } else if ( strings[1].equals("privato") ){     // se il secondo argomento è "privato"
                    file = player.getUniqueId().toString();     // imposta il nome del file come l'uudi del utente
                } else {                                        // se è un valore non valido manda la sintassi
                    player.sendMessage(ChatColor.YELLOW + "/addwp [nome] <privato/pubblico>");
                    return true;
                }
            }

            if ( strings.length==1 ) {                          // se c'è un argomento solo
                file = player.getUniqueId().toString();         // imposta il nome del file come l'uudi del utente
            }


            String nome = strings[0];                            // il primo argomento indica il nome
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();
            World world = pos.getWorld();
            boolean trovato = false;
            String val;

            List<Waypoint> p = PluginMain.leggi_waypoint( file);   // ottiene i waypoint salvati
            for ( int i=0; i<p.size(); i++) {                       // scorre la lista
                val = (p.get(i)).nome;
                if ( val.equals(nome) ) {                           // se trova il waypoint
                    trovato = true;
                    break;
                }
            }

            if ( trovato ) {                                        // se esiste già il waypoint ritorna un messaggio
                player.sendMessage(ChatColor.RED + "waypoint già esistente");
                return true;
            } else {                                                // prova a scrivere aggiungere il waypoint

                boolean ess = PluginMain.append_file( file, pos, strings[0]);
                if ( ess ) {                                        // se riesce
                    player.sendMessage(ChatColor.GREEN + "waypoint aggiunto");
                } else {                                            // se non riesce
                    player.sendMessage(ChatColor.RED + "aggiunta waypoint fallita");
                }

            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();                 // creazione lista di auto completamento

        if ( commandSender instanceof Player) {                     // se chi esegue il comando è un player

            if ( strings.length == 1 ) {                            // se che solo un argomento
                arg.add( "nome");

            } else if ( strings.length == 2) {                      // se ci sono due argomenti
                arg.add( "privato");
                arg.add( "pubblico");
            }

        }

        return arg;                                                 // ritorna la lista con le possibile scelte
    }
}

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

public class Com_wpdis implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player) {                 // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottiene il player
            Location pos = player.getLocation();                // ottiene la sua posizione
            String file = new String();

            if ( strings.length==0 ) {                          // se ci sono 0 argomenti
                player.sendMessage(ChatColor.YELLOW + "/wpdis [privato/pubblico] [nome]");
                return true;
            }

            if ( strings.length>2 ) {                           // le ha più di due argomento ritorna la sintassi
                player.sendMessage(ChatColor.YELLOW + "/wpdis [privato/pubblico] [nome]");
                return true;
            }

            if ( strings[0].equals("privato") ) {               // se l' argomento 1 è "privato"
                file = player.getUniqueId().toString();         // imposta il nome del file come l'uudi del utente

            } else if ( strings[0].equals("pubblico") ) {       // se l' argomento 1 è "pubblico"
                file = "GLOBAL";                                // imposta il nome del file come GLOBALE
            } else {                                            // se non è nessuno dei due manda la sintassi
                player.sendMessage(ChatColor.YELLOW + "/wpdis [privato/pubblico] [nome]");
                return true;

            }


            List<Waypoint> wp = PluginMain.leggi_waypoints( file);  // legge i waypoint

            String nome = strings[1];
            String val;

            List<Waypoint> p = PluginMain.leggi_waypoints( file);   // ottiene i waypoint salvati
            for ( int i=0; i<p.size(); i++) {                       // scorre la lista
                val = (p.get(i)).nome;
                if ( val.equals(nome) ) {                           // se trova il waypoint
                    Waypoint wpoint = wp.get(i);
                    String output = ChatColor.GREEN + wpoint.nome;

                    int x = (int) pos.getX();
                    int z = (int) pos.getZ();
                    World world = pos.getWorld();

                    if ( !world.getName().equals(wpoint.nome)) {    // se non è nello stesso mondo
                        player.sendMessage("waypoint in un altro mondo");
                    }

                    int disx = wpoint.getDisx( x);
                    int disz = wpoint.getDisz( z);

                    if ( z > wpoint.z ) {
                        output += " | z : -" + Integer.toString( disz);
                    } else if ( z < wpoint.z ) {
                        output += " | z : +" + Integer.toString( disz);
                    }

                    if ( x > wpoint.x ) {
                        output += " | x : -" + Integer.toString( disx);
                    } else if ( x < wpoint.x ) {
                        output += " | x : +" + Integer.toString( disx);
                    }


                    float dis = (float) Math.sqrt( Math.pow(disx, 2) + Math.pow(disz, 2) );

                    output += " | distanza : " + Float.toString(dis);

                    player.sendMessage(output );                    // stampa waypoint
                    return true;
                }
            }

            player.sendMessage("waypoint non esistente");        // se non viene trovato il waypoint


        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();                     // creazione lista di auto completamento

        if ( commandSender instanceof Player) {                    // se chi esegue il comando è un player

            Player player = (Player) commandSender;                // ottiene il player
            List<Waypoint> waypoints;                             // lista di waypoint
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

package me.enne139.comandi;
import me.enne139.PluginMain;
import me.enne139.ogg.Gruppo;
import me.enne139.ogg.Waypoint;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Com_wpshow implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player) {                 // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottiene il player
            String file = new String();


            if (strings.length == 0) {                          // se ci sono 0 argomenti
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                return true;
            }

            if (strings.length > 2) {                           // le ha più di due argomento ritorna la sintassi
                player.sendMessage(ChatColor.YELLOW + "/wpshow [privato/pubblico] [nome]");
                return true;
            }

            if (strings[0].equals("privato")) {               // se l' argomento 1 è "privato"
                file = player.getUniqueId().toString();         // imposta il nome del file come l'uudi del utente

            } else if (strings[0].equals("pubblico")) {       // se l' argomento 1 è "pubblico"
                file = "GLOBAL";                                // imposta il nome del file come GLOBALE
            } else {                                            // se non è nessuno dei due manda la sintassi
                Gruppo salvGruppo = Gruppo.get_gruppo(strings[0]);                            // ottiene l' oggetto del gruppo
                if (!Objects.isNull(salvGruppo)) {                                          // se l' oggetto non è nullo
                    if (!salvGruppo.puo_aggiungere(player.getName())) {                     // se il player non può aggiunge wp
                        player.sendMessage(ChatColor.RED + "non gruppo esistente");
                        return true;
                    }
                    file = "#" + salvGruppo.nome;                                               // imposta l' indirizzo del file

                } else {                                                                        // se è un valore non valido manda la sintassi
                    player.sendMessage(ChatColor.YELLOW + "/wpshow [privato/pubblico] [nome]");
                    return true;
                }
            }


            List<Waypoint> wp = PluginMain.leggi_waypoints(file);  // legge i waypoint

            String nome = strings[1];
            String val;

            List<Waypoint> p = PluginMain.leggi_waypoints(file);   // ottiene i waypoint salvati
            for (int i = 0; i < p.size(); i++) {                       // scorre la lista
                val = (p.get(i)).nome;
                if (val.equals(nome)) {                           // se trova il waypoint

                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard = manager.getNewScoreboard();

                    Objective objective = scoreboard.registerNewObjective("show", "dummy", p.get(i).nome);
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    Score r1 = objective.getScore("x : " + Math.round( p.get(i).x ));
                    Score r2 = objective.getScore("z : " + Math.round( p.get(i).z ));
                    Score r3 = objective.getScore("y : " + Math.round( p.get(i).y ));

                    r1.setScore(3);
                    r2.setScore(2);
                    r3.setScore(1);

                    player.setScoreboard(scoreboard);

                    return true;
                }
            }

            player.sendMessage("waypoint non esistente");        // se non viene trovato il waypoint

        }

        return true;


    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();                // creazione lista di auto completamento

        if ( commandSender instanceof Player) {                    // se chi esegue il comando è un player

            Player player = (Player) commandSender;                // ottiene il player
            List<Waypoint> waypoints;                              // lista di waypoint
            List<Gruppo> gruppi;                                   // lista di gruppi
            String file;

            if ( strings.length == 1 ) {                           // se c'è solo un argomento
                arg.add( "privato");                               // aggiunge valori alla lista
                arg.add( "pubblico");

                gruppi = Gruppo.get_grup_wp_add( player.getName());// ottiene i gruppi in cui puo aggiungere wp

                for ( int i=0; i<gruppi.size(); i++) {
                    arg.add( gruppi.get(i).nome );
                }

                return arg;                                        // ritorna la lista con le possibile scelte

            } else if ( strings.length == 2) {                     // se ci sono 2 argomenti

                String tipo = strings[0];                          // tipologia waypoint

                if ( tipo.equals("privato") ) {                    // se è privato
                    file = player.getUniqueId().toString();        // imposta il nome del file come l'uudi del utente

                } else if ( tipo.equals("pubblico") ) {            // se è pubblico
                    file = "GLOBAL";                               // imposta il nome del file come GLOBAL

                } else {                                           // se e un valore non valido
                    Gruppo salvGruppo = Gruppo.get_gruppo( tipo);                                  // ottiene l' oggetto del gruppo
                    if ( !Objects.isNull( salvGruppo) ) {                                          // se l' oggetto non è nullo
                        if ( !salvGruppo.puo_aggiungere( player.getName()) ) {                     // se il player non può aggiunge wp
                            return arg;
                        }
                        file = "#" + salvGruppo.nome;                                              // imposta l' indirizzo del file
                    } else {
                        return arg;
                    }
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

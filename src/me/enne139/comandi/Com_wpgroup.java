package me.enne139.comandi;

import me.enne139.PluginMain;
import me.enne139.ogg.Gruppo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Com_wpgroup implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {                  // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottiene il player
            List<Gruppo> gruppi;                                // lista di gruppi
            boolean trovato;

            if (strings.length == 0) {                          // se ci sono 0 argomenti
                player.sendMessage(ChatColor.YELLOW + "/wpgroup [comando] <gruppoNome> <confermaGruppoNome>");

            } else if (strings.length == 1) {                   // se ci sono 1 argomenti
                if ( strings[0].equals("list") ) {              // se il primo argomento è list

                    player.sendMessage( "-----------------------------------------------------");

                    gruppi = Gruppo.get_grup_pro( player.getName());    // ottiene i gruppi del player

                    player.sendMessage( "mie : ");
                    for ( int i=0; i<gruppi.size(); i++) {      // scorre i gruppi del player
                        player.sendMessage( "      " + gruppi.get(i).nome );
                    }

                    gruppi = Gruppo.get_grup_inside( player.getName()); // ottiene i gruppi in cui è presente il player

                    player.sendMessage( "altri : ");
                    for ( int i=0; i<gruppi.size(); i++) {      // scorre i gruppi in cui è presente il player
                        if ( !gruppi.get(i).proprietario.substring(1).equals( player.getName())) { // se il player non è il proprietario del gruppo
                            player.sendMessage( "      " + gruppi.get(i).nome);
                        }
                    }


                    player.sendMessage( "-----------------------------------------------------");

                } else {
                    player.sendMessage(ChatColor.YELLOW + "/wpgroup [comando] <gruppoNome> <confermaGruppoNome>");
                }

            } else if (strings.length == 2) {                   // se ci sono 2 argomenti
                if ( strings[0].equals("create") ) {            // se il primo parametro è create

                    if ( strings[1].equals("privato") || strings[1].equals("pubblico") ) {
                        player.sendMessage(ChatColor.RED+ "nome non valido");
                        return true;
                    }

                    trovato = Gruppo.gruppo_esiste( strings[1]);

                    if ( trovato ) {            // se esiste già il gruppo
                        player.sendMessage(ChatColor.RED+ "gruppo già esistente");

                    } else {

                        trovato = PluginMain.append_gruppi( strings[1], player.getName());

                        if ( trovato ) {            // se riesce ad aggiungere il gruppo
                            player.sendMessage(ChatColor.GREEN + "aggiunta gruppo effettuata");

                        } else {                    // se non riesce ad aggiungere il gruppo
                            player.sendMessage(ChatColor.RED + "aggiunta gruppo fallita");

                        }

                    }

                } else {
                    player.sendMessage(ChatColor.YELLOW + "/wpgroup [comando] <gruppoNome> <confermaGruppoNome>");
                }

            } else if (strings.length == 3) {                   // se ci sono 3 argomenti
                if ( strings[0].equals("delete") ) {            // se il primo parametro è delete

                    if ( !strings[1].equals( strings[2])) {      // se non viene confermato il nome
                        player.sendMessage(ChatColor.RED + "confermare il nome");
                        return true;
                    }

                    Gruppo salvGruppo = Gruppo.get_gruppo( strings[1]);

                    if ( Objects.isNull(salvGruppo) ) {                  // se l' oggetto e nullo
                        player.sendMessage(ChatColor.RED+ "gruppo non esistente 1");
                        return true;
                    }
                    if ( !salvGruppo.proprietario.substring(1).equals( player.getName() ) ) { // se il gruppo non è del player
                        player.sendMessage(ChatColor.RED+ "gruppo non esistente 2");
                        return true;
                    }


                    trovato = PluginMain.cancella_gruppo( strings[1]);

                    if ( trovato ) {            // se riesce a cancellare il gruppo
                        player.sendMessage(ChatColor.GREEN+ "gruppo cancellato");
                    } else {
                        player.sendMessage(ChatColor.RED+ "errore nella cancellazione del gruppo");
                    }

                } else {
                    player.sendMessage(ChatColor.YELLOW + "/wpgroup [comando] <gruppoNome> <confermaGruppoNome>");
                }

            } else {
                player.sendMessage(ChatColor.YELLOW + "/wpgroup [comando] <gruppoNome> <confermaGruppoNome>");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();                      // creazione lista di auto completamento

        if (commandSender instanceof Player) {                      // se chi esegue il comando è un player

            Player player = (Player) commandSender;                 // ottiene il player
            List<Gruppo> gruppi;                                    // lista di gruppi

            if (strings.length == 1) {                              // se c'è un argomento

                arg.add("create");
                arg.add("delete");
                arg.add("list");

            } else if (strings.length == 2) {                       // se ci sono 2 argomenti

                if ( strings[0].equals("delete") ) {

                    gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene i gruppi del player

                    for ( int i=0; i<gruppi.size(); i++) {
                        arg.add( gruppi.get(i).nome);
                    }

                }

            }
        }
        return arg;
    }
}

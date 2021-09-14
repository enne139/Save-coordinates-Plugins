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

public class Com_wpmebers implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player) {                  // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottiene il player
            List<Gruppo> gruppi;                                // lista di gruppi
            boolean trovato;

            if (strings.length == 0) {                          // se ci sono 0 argomenti
                player.sendMessage(ChatColor.YELLOW + "/wpmebers [comando] [gruppoNome] <argomenti>");
                return true;

            } else if (strings.length == 2) {                   // se ci sono 2 argomenti

                if (strings[0].equals("list")) {              // se il primo argomento è list

                    gruppi = Gruppo.get_grup_inside(player.getName());
                    trovato = false;
                    Gruppo salGruppo = null;

                    for (int i = 0; i < gruppi.size(); i++) {              // ceca se esiste il gruppo

                        if (gruppi.get(i).nome.equals(strings[1])) {  // se il gruppo esiste
                            trovato = true;
                            salGruppo = gruppi.get(i);
                        }

                    }

                    if (trovato) {                                    // se viene trovato il gruppo

                        player.sendMessage("-----------------------------------------------------");

                        player.sendMessage(salGruppo.proprietario);        // manda il proprietario

                        for (int i = 0; i < salGruppo.membri.size(); i++) {    // scorre i membri

                            player.sendMessage(salGruppo.membri.get(i));   // manda i singoli membri

                        }

                        player.sendMessage("-----------------------------------------------------");

                    } else {                                            // se il gruppo non esiste
                        player.sendMessage(ChatColor.RED + "gruppo non esistente");
                    }

                } else {                                                // se la sintassi del comando è sbagliata
                    player.sendMessage(ChatColor.YELLOW + "/wpmebers [comando] [gruppoNome] <argomenti>");
                }
                return true;

            } else if (strings.length == 3) {                   // se ci sono 3 argomenti

                if ( strings[0].equals("add") ) {               // se il primo parametro è add

                    gruppi = Gruppo.get_grup_pro( player.getName());       // ottiene i gruppi di cui il player è proprietario
                    trovato = false;
                    Gruppo salGruppo = null;

                    for (int i = 0; i < gruppi.size(); i++) {              // ceca se esiste il gruppo

                        if (gruppi.get(i).nome.equals(strings[1])) {       // se il gruppo esiste
                            trovato = true;
                            salGruppo = gruppi.get(i);
                        }

                    }

                    if ( trovato ) {  // se esiste il gruppo

                        trovato = false;
                        for (int i = 0; i < salGruppo.membri.size(); i++) {    // scorre i membri

                            if ( salGruppo.is_inside( strings[2]) ) {    // se trova il membro nel gruppo
                                trovato = true;
                            }

                        }

                        if ( trovato ) {// se è già presente il player

                            player.sendMessage(ChatColor.RED + "player già nel gruppo");

                        } else {        // se non esiste già lo aggiunge

                            trovato = PluginMain.append_membro_gruppo( strings[1], strings[2], "-");

                            if ( trovato ) {   // se l' aggiunta è riuscita
                                player.sendMessage(ChatColor.GREEN + "aggiunta player nel gruppo");
                            } else {           // se l' aggiunta non è riuscita
                                player.sendMessage(ChatColor.RED + "aggiunta player nel gruppo fallita");
                            }

                        }

                    } else {                                    // se non esiste il gruppo
                        player.sendMessage(ChatColor.RED + "gruppo non esistente");
                    }

                } else if ( strings[0].equals("remove") ) {     // se il primo parametro è remove

                    gruppi = Gruppo.get_grup_pro( player.getName());       // ottiene i gruppi di cui il player è proprietario
                    trovato = false;
                    Gruppo salGruppo = null;

                    for (int i = 0; i < gruppi.size(); i++) {              // ceca se esiste il gruppo

                        if (gruppi.get(i).nome.equals(strings[1])) {       // se il gruppo esiste
                            trovato = true;
                            salGruppo = gruppi.get(i);
                        }

                    }

                    if ( trovato ) {  // se esiste il gruppo

                        trovato = false;
                        for (int i = 0; i < salGruppo.membri.size(); i++) {    // scorre i membri

                            if ( salGruppo.is_inside( strings[2]) ) {    // se trova il membro nel gruppo
                                trovato = true;
                            }

                        }

                        if ( trovato ) {// se è già presente il player

                            trovato = PluginMain.remove_membro_gruppo( strings[1], strings[2]);

                            if ( trovato ) {   // se la rimozione è riuscita
                                player.sendMessage(ChatColor.GREEN + " player rimosso dal gruppo");
                            } else {           // se la rimozione è riuscita
                                player.sendMessage(ChatColor.RED + "rimozione player dal gruppo fallita");
                            }

                        } else {        // se esiste gia nel gruppo

                            player.sendMessage(ChatColor.RED + "player già nel gruppo");

                        }

                    } else {                                    // se non esiste il gruppo
                        player.sendMessage(ChatColor.RED + "gruppo non esistente");
                    }

                } else {                                                // se la sintassi del comando è sbagliata
                    player.sendMessage(ChatColor.YELLOW + "/wpmebers [comando] [gruppoNome] <argomenti>");
                }
                return true;


            } else if (strings.length == 4) {                   // se ci sono 4 argomenti

                if ( strings[0].equals("add") ) {               // se il primo parametro è add

                    gruppi = Gruppo.get_grup_pro( player.getName());       // ottiene i gruppi di cui il player è proprietario
                    trovato = false;
                    Gruppo salGruppo = null;
                    String permesso = "";

                    if ( strings[3].equals("aggiunta")  ) {
                        permesso = "+";

                    } else if ( strings[3].equals("nessuno") ) {
                        permesso = "-";

                    } else {                                 // se il 4 argomento è diverso da aggiunta o nessuno
                        player.sendMessage(ChatColor.RED + "permessi non validi");
                        return true;
                    }

                    for (int i = 0; i < gruppi.size(); i++) {              // ceca se esiste il gruppo

                        if (gruppi.get(i).nome.equals(strings[1])) {       // se il gruppo esiste
                            trovato = true;
                            salGruppo = gruppi.get(i);
                        }

                    }

                    if ( trovato ) {  // se esiste il gruppo

                        trovato = false;
                        for (int i = 0; i < salGruppo.membri.size(); i++) {    // scorre i membri

                            if ( salGruppo.is_inside( strings[2]) ) {    // se trova il membro nel gruppo
                                trovato = true;
                            }

                        }

                        if ( trovato ) {// se è già presente il player

                            player.sendMessage(ChatColor.RED + "player già nel gruppo");

                        } else {        // se non esiste già lo aggiunge

                            trovato = PluginMain.append_membro_gruppo( strings[1], strings[2], permesso);

                            if ( trovato ) {   // se l' aggiunta è riuscita
                                player.sendMessage(ChatColor.GREEN + "aggiunta player nel gruppo");
                            } else {           // se l' aggiunta non è riuscita
                                player.sendMessage(ChatColor.RED + "aggiunta player nel gruppo fallita");
                            }

                        }

                    } else {                                    // se non esiste il gruppo
                        player.sendMessage(ChatColor.RED + "gruppo non esistente");
                    }

                } else {                                                // se la sintassi del comando è sbagliata
                    player.sendMessage(ChatColor.YELLOW + "/wpmebers [comando] [gruppoNome] <argomenti>");
                }
                return true;

            } else {                                                // se la sintassi del comando è sbagliata
                player.sendMessage(ChatColor.YELLOW + "/wpmebers [comando] [gruppoNome] <argomenti>");
                return true;
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

            if ( strings.length==1 ) {                              // se c'è un argomento

                arg.add( "add");
                arg.add( "remove");
                arg.add( "list");

            } else if ( strings.length==2 ) {                       // se ci sono 2 argomenti

                if ( strings[0].equals("add") || strings[0].equals("remove") ) {  // se il primo parametro è uguale ad add o remove

                    gruppi = Gruppo.get_grup_pro( player.getName());    // ottiene solo i propri gruppi

                    for ( int i=0; i<gruppi.size(); i++ ) {

                        arg.add( gruppi.get(i).nome );

                    }

                } else if ( strings[0].equals("list") ) {

                    gruppi = Gruppo.get_grup_inside( player.getName());    // ottiene solo i propri gruppi

                    for ( int i=0; i<gruppi.size(); i++ ) {

                        arg.add( gruppi.get(i).nome );

                    }

                }


            } else if ( strings.length==3 ) {                       // se ci sono 3 argomenti

                if ( strings[0].equals("add") ) {    // se il primo parametro è add

                    List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());  // ottiene lista player online

                    for (int i = 0; i < players.size(); i++) {
                        if ((players.get(i).getName()).equals(player.getName())) {   // non inserisce se stesso nelle possibilità
                            continue;
                        }
                        arg.add(players.get(i).getName());        // aggiunge il nome alle possibilità
                    }

                } else if ( strings[0].equals("remove") ) {

                    Gruppo salGruppo = Gruppo.get_gruppo( strings[1] ); // ottiene l' oggetto del gruppo

                    if ( !Objects.isNull( salGruppo) ) {                    // se l' oggetto non è null

                        for ( int i=0; i<salGruppo.membri.size(); i++ ) {   // aggiunge i possibili player
                            arg.add( salGruppo.membri.get(i).substring(1));
                        }

                    }

                }


            } else if ( strings.length==4 ) {                       // se ci sono 4 argomenti

                if ( strings[0].equals("add") ) {                   // se il primo argomento è add
                    arg.add( "aggiunta");
                    arg.add( "nessuno");
                }

            }


        }
        return arg;
    }
}

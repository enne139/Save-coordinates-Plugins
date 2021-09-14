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

public class Com_wpgrup2 implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if ( commandSender instanceof Player){                  // se chi esegue il comando è un player
            Player player = (Player) commandSender;             // ottiene il player
            List<Gruppo> gruppi;                                // lista di gruppi
            boolean trovato;

            if ( strings.length==0 ) {                          // se ci sono 0 argomenti
                player.sendMessage(ChatColor.YELLOW + "/wpgrup [comando]");
                return true;

            } else if ( strings.length==1 ) {                   // se ci sono 1 argomenti

                if ( strings[0].equals("list") ) {              // se l' argomento 1 è list

                    gruppi = PluginMain.leggi_gruppi();                      // legge tutti i gruppi

                    player.sendMessage( "-----------------------------------------------------");

                    player.sendMessage( "altri : ");
                    for ( int i=0; i<gruppi.size(); i++) {      // manda miei gruppi
                        if ( gruppi.get(i).is_inside( player.getName())) {
                            player.sendMessage( "      " + gruppi.get(i).nome);
                        }
                    }

                    gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi

                    player.sendMessage( "mie : ");
                    for ( int i=0; i<gruppi.size(); i++) {      // manda miei gruppi
                        player.sendMessage( "      " + gruppi.get(i).nome );
                    }

                    player.sendMessage( "-----------------------------------------------------");

                } else if ( strings[0].equals("create") ) {     // se l' argomento 1 è create
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup create [nome]");

                } else if ( strings[0].equals("delete") ) {     // se l' argomento 1 è delete
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup delete [nome] [conferma_nome]");

                } else if ( strings[0].equals("members") ) {    // se l' argomento 1 è members
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup members [comando]");

                } else {                                        // se l' argomento 1 non è valido
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup [comando]");
                }

                return true;

            } else if ( strings.length==2 ) {                   // se ci sono 2 argomenti

                if ( strings[0].equals("create") ) {     // se l' argomento 1 è create

                    gruppi = PluginMain.leggi_gruppi();  // legge tutti i gruppi
                    trovato = false;

                    for ( int i=0; i<gruppi.size(); i++) {
                        if ( gruppi.get(i).nome.equals( strings[1]) ) {
                            trovato = true;
                            break;
                        }
                    }

                    if ( trovato ) {                    // se viene trovato gia un gruppo con questo nome
                        player.sendMessage(ChatColor.RED + "gruppo già esistente");

                    } else {
                        trovato = PluginMain.append_gruppi( strings[1], player.getName());  // prova ad aggiungere il gruppo

                        if ( trovato ) {                // se riesce ad aggiungere
                            player.sendMessage(ChatColor.GREEN + "gruppo aggiunto");
                        } else {
                            player.sendMessage(ChatColor.RED + "aggiunta gruppo fallita");
                        }
                    }

                } else {                                        // se l' argomento 1 non è valido
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup [comando]");
                }

                return true;

            } else if ( strings.length==3) {                   // se ci sono 3 argomenti

                if ( strings[0].equals("delete") ) {     // se l' argomento 1 è delete

                    if ( !strings[1].equals( strings[2]) ) {        // se il primo nome non corrispondono non corrisponde al secondo
                        player.sendMessage(ChatColor.YELLOW + "confermare il nome del gruppo");
                        return true;
                    }

                    gruppi = PluginMain.leggi_gruppi();  // legge tutti i gruppi
                    gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi
                    trovato = false;

                    for ( int i=0; i<gruppi.size(); i++) {
                        if ( gruppi.get(i).nome.equals( strings[1]) ) {
                            trovato = true;
                            break;
                        }
                    }

                    if ( trovato ) {                    // se esiste il gruppo

                        trovato = PluginMain.cancella_gruppo( strings[1]);      // prova a cancellarlo

                        if ( trovato ) {            // se riesce a cancellare il gruppo
                            player.sendMessage(ChatColor.GREEN + "gruppo cancellato");
                        } else {
                            player.sendMessage(ChatColor.RED + "cancellazione gruppo fallita");
                        }

                    } else {
                        player.sendMessage(ChatColor.RED + "gruppo non esistente");
                    }

                } else if ( strings[0].equals("members") ) {    // se l' argomento 1 è members

                    if ( strings[1].equals("list") ) {       // se il 2 argomento è list

                        gruppi = PluginMain.leggi_gruppi();  // legge tutti i gruppi
                        trovato = false;
                        Gruppo questo = null;

                        for ( int i=0; i<gruppi.size(); i++) {
                            if ( gruppi.get(i).nome.equals( strings[2]) ) {
                                trovato = true;
                                questo = gruppi.get(i);
                                break;
                            }
                        }

                        if ( trovato ) {                    // se esiste il gruppo

                            player.sendMessage( "-----------------------------------------------------");

                            player.sendMessage( "membri : ");
                            for ( int i=0; i<questo.membri.size(); i++) {      // manda membri del gruppo gruppi

                                player.sendMessage( "      " + questo.membri.get(i).substring(1) );

                            }


                            player.sendMessage( "-----------------------------------------------------");

                        } else {
                            player.sendMessage(ChatColor.RED + "gruppo non esistente");
                        }

                    } else {
                        player.sendMessage(ChatColor.YELLOW + "/wpgrup members [comando]");
                    }

                } else {                                        // se l' argomento 1 non è valido
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup [comando]");
                }

                return true;

            }  else if ( strings.length==4 ) {                   // se ci sono 4 argomenti

                if ( strings[0].equals("members") ) {    // se l' argomento 1 è members

                    if ( strings[1].equals("add") ) {    // se il 2 argomento è add

                        gruppi = PluginMain.leggi_gruppi();  // legge tutti i gruppi
                        gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi
                        trovato = false;
                        Gruppo questo = null;

                        for ( int i=0; i<gruppi.size(); i++) {
                            if ( gruppi.get(i).nome.equals( strings[2]) ) {
                                trovato = true;
                                questo = gruppi.get(i);
                                break;
                            }
                        }
                        if ( trovato ) {                    // se esiste il gruppo

                            if ( questo.is_inside( strings[3]) ) { // se il player è gia nel gruppo
                                player.sendMessage(ChatColor.RED + "player non nel gruppo");
                                return true;
                            }

                            trovato = PluginMain.append_membro_gruppo( strings[2], strings[3], "-");      // prova a cancellarlo

                            if ( trovato ) {            // se riesce a cancellare il gruppo
                                player.sendMessage(ChatColor.GREEN + "membro aggiunta al gruppo");

                            } else {
                                player.sendMessage(ChatColor.RED + "aggiunta membro al gruppo fallita");
                            }

                        } else {
                            player.sendMessage(ChatColor.RED + "gruppo non esistente");
                        }

                    } else if ( strings[1].equals("remove") ) {

                        gruppi = PluginMain.leggi_gruppi();  // legge tutti i gruppi
                        gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi
                        trovato = false;
                        Gruppo questo = null;

                        for ( int i=0; i<gruppi.size(); i++) {
                            if ( gruppi.get(i).nome.equals( strings[2]) ) {
                                trovato = true;
                                questo = gruppi.get(i);
                                break;
                            }
                        }
                        if ( trovato ) {                    // se esiste il gruppo

                            if ( !questo.is_inside( strings[3]) ) { // se il player non è ne gruppo
                                player.sendMessage(ChatColor.RED + "player non nel gruppo");
                                return true;
                            }

                            trovato = PluginMain.remove_membro_gruppo( strings[2], strings[3]);      // prova a cancellarlo

                            if ( trovato ) {            // se riesce a cancellare il gruppo
                                player.sendMessage(ChatColor.GREEN + "membro rimosso dal gruppo");

                            } else {                // se non riesce a cancelare il gruppo
                                player.sendMessage(ChatColor.RED + "rimozione del membro dal gruppo fallita");
                            }

                        } else {                // se non esistei il gruppo
                            player.sendMessage(ChatColor.RED + "gruppo non esistente");
                        }

                    } else {
                        player.sendMessage(ChatColor.YELLOW + "/wpgrup members [comando]");
                    }


                } else {                                        // se l' argomento 1 non è valido
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup [comando]");
                }

            }  else if ( strings.length==5 ) {                   // se ci sono 5 argomenti

                if ( strings[0].equals("members") ) {    // se l' argomento 1 è members

                    if ( strings[1].equals("add") ) {    // se il 2 argomento è add

                        if ( !strings[4].equals( "aggiunta") && !strings[4].equals( "nessuno")) {   // se il 5 parametro è diverso a da aggiunta o nessuno
                            player.sendMessage(ChatColor.YELLOW + "permesso non valido");
                            return true;
                        }

                        gruppi = PluginMain.leggi_gruppi();  // legge tutti i gruppi
                        gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi
                        trovato = false;
                        Gruppo questo = null;
                        String permesso = "-";

                        for ( int i=0; i<gruppi.size(); i++) {
                            if ( gruppi.get(i).nome.equals( strings[2]) ) {
                                trovato = true;
                                questo = gruppi.get(i);
                                break;
                            }
                        }
                        if ( trovato ) {                    // se esiste il gruppo

                            if ( questo.is_inside( strings[3]) ) { // se il player è gia nel gruppo
                                player.sendMessage(ChatColor.RED + "player non nel gruppo");
                                return true;
                            }

                            if ( strings[4].equals( "aggiunta") ) {
                                permesso = "+";
                            }

                            trovato = PluginMain.append_membro_gruppo( strings[2], strings[3], permesso);      // prova a cancellarlo

                            if ( trovato ) {            // se riesce a cancellare il gruppo
                                player.sendMessage(ChatColor.GREEN + "membro aggiunta al gruppo");

                            } else {
                                player.sendMessage(ChatColor.RED + "aggiunta membro al gruppo fallita");
                            }

                        } else {
                            player.sendMessage(ChatColor.RED + "gruppo non esistente");
                        }

                    }

                } else {                                        // se l' argomento 1 non è valido
                    player.sendMessage(ChatColor.YELLOW + "/wpgrup [comando]");
                }

            } else {
                player.sendMessage(ChatColor.YELLOW + "/wpgrup members [comando]");
            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();                      // creazione lista di auto completamento

        if ( commandSender instanceof Player ) {                    // se chi esegue il comando è un player

            Player player = (Player) commandSender;                 // ottiene il player
            List<Gruppo> gruppi;                                    // lista di gruppi

            if ( strings.length==1 ) {                              // 1 argomento
                arg.add("create");
                arg.add("delete");
                arg.add("members");
                arg.add("list");

                return arg;

            } else if ( strings.length==2 ) {                       // 2 argomenti

                if ( strings[0].equals("delete") ) {                // se il primo parametro è delete

                    gruppi = PluginMain.leggi_gruppi();                      // legge tutti i gruppi
                    gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi

                    for ( int i=0; i< gruppi.size(); i++) {         // aggiunge i nomi alla possibilità
                        arg.add( gruppi.get(i).nome );
                    }

                    return arg;

                } else if ( strings[0].equals("members") ) {        // se il primo parametro è members
                    arg.add("add");
                    arg.add("remove");
                    arg.add("list");

                    return arg;
                }

            } else if ( strings.length==3 ) {                       // 3 argomenti

                if ( strings[0].equals("members") ) {               // se il primo parametro è members

                    if ( strings[1].equals("add") ) {               // se il secondo parametro è add

                        gruppi = PluginMain.leggi_gruppi();                      // legge tutti i gruppi
                        gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi

                        for ( int i=0; i< gruppi.size(); i++) {         // aggiunge i nomi alla possibilità
                            arg.add( gruppi.get(i).nome );
                        }

                        return arg;

                    } else if ( strings[1].equals("remove") ) {     // se il secondo parametro è remove

                        gruppi = PluginMain.leggi_gruppi();                      // legge tutti i gruppi
                        gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi

                        for ( int i=0; i< gruppi.size(); i++) {         // aggiunge i nomi alla possibilità
                            arg.add( gruppi.get(i).nome );
                        }

                        return arg;

                    } else if ( strings[1].equals("list") ) {       // se il secondo parametro è list

                        gruppi = PluginMain.leggi_gruppi();                      // legge tutti i gruppi
                        gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi

                        for ( int i=0; i< gruppi.size(); i++) {         // aggiunge i nomi alla possibilità
                            arg.add( gruppi.get(i).nome );
                        }

                        return arg;

                    }

                }

            } else if ( strings.length==4 ) {                       // 4 argomenti

                if ( strings[0].equals("members") ) {               // se il primo parametro è members

                    if ( strings[1].equals("add") ) {               // se il secondo parametro è add

                        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());  // ottiene lista player online

                        for (int i = 0; i < players.size(); i++) {
                            if ((players.get(i).getName()).equals(player.getName())) {   // non inserisce se stesso nelle possibilità
                                continue;
                            }
                            arg.add(players.get(i).getName());        // aggiunge il nome alle possibilità
                        }

                        return arg;

                    } else if ( strings[1].equals("remove") ) {

                        gruppi = PluginMain.leggi_gruppi();                      // legge tutti i gruppi
                        gruppi = Gruppo.get_grup_pro( player.getName()); // ottiene solo i propri gruppi

                        for ( int i=0; i< gruppi.size(); i++) {                 // cerca se il gruppo nella secondo argomento esiste
                            if ( gruppi.get(i).nome.equals( strings[2]) ) {

                                for ( int y=0; y<gruppi.get(i).membri.size(); y++) { // se lo trova aggiunge i nomi dei player rimovibili
                                    arg.add( gruppi.get(i).membri.get(y));
                                }

                            }
                        }

                        return arg;

                    }
                }

            } else if ( strings.length==5 ) {                       // 4 argomenti

                if ( strings[0].equals("members") ) {               // se il primo parametro è members

                    if ( strings[1].equals("add") ) {               // se il secondo parametro è add

                        arg.add("aggiunta");
                        arg.add("nessuno");

                        return arg;

                    }

                }

            }

        }

        return arg;
    }
}

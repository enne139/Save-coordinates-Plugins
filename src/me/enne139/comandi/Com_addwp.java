package me.enne139.comandi;

import me.enne139.PluginMain;
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


public class Com_savewp implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ( commandSender instanceof Player)
        {
            Player player = (Player) commandSender;                   // ottine player che ha usato il comando
            Location pos = player.getLocation();                      // ottine la posizione del player
            String uuid = new String();

            if ( !( strings.length==1 || strings.length==2 ) ) {      // se non ha 1 o 2 argomenti stampa la sntassi
                player.sendMessage(ChatColor.YELLOW + "/addwp nome [privato]");
                return false;
            }

            if ( strings.length==2 ) {                                // se ci sono 2 argoemnti
                if ( strings[1].equals("publico") ) {                 // se il secondo argoemnto
                    uuid = "GLOBAL";
                } else if ( strings[1].equals("privato") ){
                    uuid = player.getUniqueId().toString();
                } else {                                              // se il secondo argomento non è una scelta valida
                    player.sendMessage(ChatColor.YELLOW + command.toString() + " nome [privato]");
                    return false;
                }
            }

            if ( strings.length==1 ) {                                // senza specifica è privato
                uuid = player.getUniqueId().toString();
            }


            String nome = strings[0];
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();
            World world = pos.getWorld();
            boolean trovato = false;

            List<String> p = PluginMain.legge_file( uuid);
            for ( int i=0; i<p.size(); i++) {
                if ( (p.get(i)).split( ";")[0].equals(nome) ) {
                    trovato = true;
                    break;
                }
            }
            if ( trovato ) {
                player.sendMessage(ChatColor.RED + "waypoin già esistente");
            } else {
                PluginMain.scrivi_file( uuid, pos, strings[0]);
                player.sendMessage(ChatColor.GREEN + "waypoint aggiunto");
            }

        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> arg = new ArrayList<String>();

        if ( commandSender instanceof Player)
        {
            Player player = (Player) commandSender;

            if ( strings.length == 1 ) {
                arg.add( "nome");

            } else if ( strings.length == 2) {
                arg.add( "privato");
                arg.add( "publico");
            }


            //player.sendMessage( Integer.toString(strings.length) );
        }

        return arg;
    }
}

package me.enne139;


import me.enne139.comandi.Com_savewp;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PluginMain extends JavaPlugin {

    @Override
    public void onEnable() {

        //controllo file
        crea_cartella();

        //set eventi
        //getServer().getPluginManager().registerEvents( new Playermovelistener(), this);

        //set comandi
        this.getCommand( "savewp").setExecutor( new Com_savewp() );
        this.getCommand( "savewp").setTabCompleter( new Com_savewp() );

    }

    public void crea_cartella() {
        // Definiamo il path della cartella da creare
        String folderName = "plugins/saveCord";

        // Creiamo l'oggetto File
        File folder = new File(folderName);

        // Verifichiamo che non sia già esistente come cartella
        if(!folder.isDirectory()) {
            // In caso non sia già presente, la creiamo
            folder.mkdir();
        }
    }

    public static void scrivi_file(String utente, Location pos, String nome )  {
        String path = "plugins/saveCord/" + utente;
        String dati = nome +";"+ pos.getX() +";"+ pos.getY() +";"+ pos.getZ() +";"+ pos.getWorld().getName() + "\n";
        try {
            File f1 = new File( path);
            if(!f1.exists()) {
                f1.createNewFile();
            }

            FileWriter fileWritter = new FileWriter( path,true);
            BufferedWriter bw = new BufferedWriter(fileWritter);
            bw.append(dati);
            bw.close();

        } catch(IOException e){
            e.printStackTrace();
        }


    }

    public static List<String> legge_file(String utente) {
        String path = "plugins/saveCord/" + utente;
        List<String> list = new ArrayList<String>();
        try {
            File f1 = new File( path);
            if(!f1.exists()) {
                f1.createNewFile();
            }

            FileReader fileWritter = new FileReader( path);
            BufferedReader bw = new BufferedReader(fileWritter);
            while ( bw.ready() ) {
                list.add( bw.readLine() );
            }
            bw.close();

        } catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }

}

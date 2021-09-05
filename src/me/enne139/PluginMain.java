package me.enne139;


import me.enne139.comandi.Com_addwp;
import me.enne139.comandi.Com_delwp;
import me.enne139.comandi.Com_listwp;
import me.enne139.ogg.Waiponint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        this.getCommand( "addwp").setExecutor( new Com_addwp() );
        this.getCommand( "addwp").setTabCompleter( new Com_addwp() );

        this.getCommand( "delwp").setExecutor( new Com_delwp() );
        this.getCommand( "delwp").setTabCompleter( new Com_delwp() );

        this.getCommand( "listwp").setExecutor( new Com_listwp() );
        this.getCommand( "listwp").setTabCompleter( new Com_listwp() );
    }

    public void crea_cartella() {
        String path = "plugins/saveCord";                           // indirizzo della cartella
        boolean check;

        File cartella = new File( path);

        if( !cartella.isDirectory()) {                              // se non esiste
            check = cartella.mkdir();                               // prova a creare la cartella
            if ( check ) {                                          // se non è riscuto a creare la cartella
                Cwarning( "Cartella creata");                  // manda un messagio di attenzione in console

            } else {                                                // se ha creato la cartella
                Cinfo( "Cartella non creata");
            }
        }
    }

    public static boolean append_file(String file, Location pos, String nome )  {
        String path = "plugins/saveCord/" + file;                       // percoso del file
        String dati = nome +";"+ pos.getX() +";"+ pos.getY() + ";"+     // dati da aggiungere
                      pos.getZ() +";"+ pos.getWorld().getName() + "\n";

        try {
            File fw = new File( path);
            if(!fw.exists()) {                                          // controlla se il file non esiste
                fw.createNewFile();                                     // crea il file
            }

            BufferedWriter bw = new BufferedWriter( new FileWriter( fw, true));
            bw.append(dati);                                            // aggiunge i dati
            bw.close();                                                 // chide il file

        } catch(IOException e){
            e.printStackTrace();
            Cwarning("aggiunta dati fallita");                      // segnala problemma sulla console
            return false;
        }
        return true;
    }

    public static List<String> legge_file(String file) {
        String path = "plugins/saveCord/" + file;           // percorso del file
        List<String> list = new ArrayList<String>();
        try {
            File fr = new File( path);
            if(!fr.exists()) {                              // controlla se il file non esiste
                return list;                                // ritorna lista vuota
            }

            BufferedReader br = new BufferedReader( new FileReader( fr));
            while ( br.ready() ) {                          // legge fino alla fine del file
                list.add( br.readLine() );                  // aggiunge rige alla lista
            }
            br.close();                                     // chiude file

        } catch(IOException e){
            e.printStackTrace();
            Cwarning("lettura dati fallita");          // segnala problemma sulla console
        }
        return list;                                        // restituisce lista rige
    }

    public static List<Waiponint> leggi_waypoint(String utente) {

        List<Waiponint> waiponints = new ArrayList<Waiponint>() ;
        List<String> liene = legge_file( utente) ;                  // ottine rige di un file


        for ( int i=0; i<liene.size(); i++){                       // scorre il file di rige
            waiponints.add( new Waiponint( liene.get(i) ) );       // crea e aggiuge alla lista dei weypoint
        }

        return waiponints;                                         // ristorna lista waypoint
    }

    public static boolean cancella_waypoint( String utente, String nome) {
        String path_u = "plugins/saveCord/" + utente;           // indirizzo file sorgente
        String path_t = "plugins/saveCord/temp" ;               // indirizzo file destinazione
        String liena;
        String val;
        String originale;

        try {
            File fr = new File( path_u);
            File fw = new File( path_t);

            if( fw.exists()) {                                  // controlla se esiste il file di destinazione
                fw.delete();                                    // cancella il file di out
                fw.createNewFile();                             // ricrea il file di out
            }

            BufferedReader br = new BufferedReader( new FileReader(fr));
            BufferedWriter bw = new BufferedWriter( new FileWriter(fw));

            while ( br.ready() ) {                              // legge fino alla fine del file di in

                liena = br.readLine();
                originale = liena.trim();
                val =  originale.split(";")[0];

                if ( nome.equals( val) ) {                      // controlla se è la gia da cancellare
                    continue;                                   // salta la colpia della riga
                }

                bw.append( liena+"\n");                         // copia la riga nel file di out
            }
            bw.close();                                         // chiude file in e out
            br.close();

            fr.delete();                                        // cancella il file di in
            fw.renameTo( fr);                                   // rinomina il file di out come quello di in


        } catch(IOException e){
            e.printStackTrace();
            Cwarning("cancelazione dati fallita");          // segnala problemma sulla console
            return false;
        }
        return true;
    }

    public static void Cwarning ( String msg ) {                       // manda un messagio di pericolo in console
        Bukkit.getServer().getLogger().warning( "[Save-coordinates-Plugins] " + msg);
    }

    public static void Csevere ( String msg ) {                        // manda un messagio di errore in console
        Bukkit.getServer().getLogger().severe( "[Save-coordinates-Plugins] " + msg);
    }

    public static void Cinfo ( String msg ) {                          // manda un messagio di info in console
        Bukkit.getServer().getLogger().info( "[Save-coordinates-Plugins] " + msg);
    }

}

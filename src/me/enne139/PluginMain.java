package me.enne139;
import me.enne139.comandi.*;
import me.enne139.ogg.*;

import org.bukkit.Bukkit;
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
        this.getCommand( "wpadd").setExecutor( new Com_wpadd() );
        this.getCommand( "wpadd").setTabCompleter( new Com_wpadd() );

        this.getCommand( "wpdel").setExecutor( new Com_wpdel() );
        this.getCommand( "wpdel").setTabCompleter( new Com_wpdel() );

        this.getCommand( "wplist").setExecutor( new Com_wplist() );
        this.getCommand( "wplist").setTabCompleter( new Com_wplist() );

        this.getCommand( "wphelp").setExecutor( new Com_wphelp() );
        this.getCommand( "wphelp").setTabCompleter( new Com_wphelp() );

        this.getCommand( "wpget").setExecutor( new Com_wpget() );
        this.getCommand( "wpget").setTabCompleter( new Com_wpget() );

        this.getCommand( "wpdis").setExecutor( new Com_wpdis() );
        this.getCommand( "wpdis").setTabCompleter( new Com_wpdis() );

        this.getCommand( "wpgroup").setExecutor( new Com_wpgroup() );
        this.getCommand( "wpgroup").setTabCompleter( new Com_wpgroup() );

        this.getCommand( "wpmebers").setExecutor( new Com_wpdis() );
        this.getCommand( "wpmebers").setTabCompleter( new Com_wpdis() );

        this.getCommand( "wpshow").setExecutor( new Com_wpshow() );
        this.getCommand( "wpshow").setTabCompleter( new Com_wpshow() );
    }

    public void crea_cartella() {
        String path = "plugins/saveCord";                           // indirizzo della cartella
        boolean check;

        File cartella = new File( path);

        if( !cartella.isDirectory()) {                              // se non esiste
            check = cartella.mkdir();                               // prova a creare la cartella
            if ( check ) {                                          // se non ?? riuscito a creare la cartella
                Cwarning( "Cartella creata");                  // manda un messaggio di attenzione in console

            } else {                                                // se ha creato la cartella
                Cinfo( "Cartella non creata");
            }
        }
    }

    public static boolean append_file(String path, String dati)  {

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
            Cwarning("aggiunta dati fallita");                      // segnala problema sulla console
            return false;
        }
        return true;
    }

    public static List<String> legge_file(String path) {
        List<String> list = new ArrayList<String>();
        try {
            File fr = new File( path);
            if(!fr.exists()) {                              // controlla se il file non esiste
                return list;                                // ritorna lista vuota
            }

            BufferedReader br = new BufferedReader( new FileReader( fr));
            while ( br.ready() ) {                          // legge fino alla fine del file
                list.add( br.readLine() );                  // aggiunge righe alla lista
            }
            br.close();                                     // chiude file

        } catch(IOException e){
            e.printStackTrace();
            Cwarning("lettura dati fallita");          // segnala problema sulla console
        }
        return list;                                        // restituisce lista righe
    }

    public static List<Waypoint> leggi_waypoints(String file) {

        String path = "plugins/saveCord/" + file;                 // percorso del file
        List<Waypoint> waypoints = new ArrayList<Waypoint>() ;
        List<String> linee = legge_file( path) ;                  // ottiene righe di un file


        for ( int i=0; i<linee.size(); i++){                       // scorre il file di righe
            waypoints.add( new Waypoint( linee.get(i) ) );       // crea e aggiunge alla lista dei waypoint
        }

        return waypoints;                                         // ristorna lista waypoint
    }

    public static boolean append_waypoint(String file, Location pos, String nome )  {
        String path = "plugins/saveCord/" + file;                       // percorso del file
        String dati = nome +";"+ pos.getX() +";"+ pos.getY() + ";"+     // dati da aggiungere
                pos.getZ() +";"+ pos.getWorld().getName() + "\n";

        append_file( path, dati);

        return true;
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

                if ( nome.equals( val) ) {                      // controlla se va cancellata la riga
                    continue;                                   // salta la copia della riga
                }

                bw.append( liena+"\n");                         // copia la riga nel file di out
            }
            bw.close();                                         // chiude file in e out
            br.close();

            fr.delete();                                        // cancella il file di in
            fw.renameTo( fr);                                   // rinomina il file di out come quello di in


        } catch(IOException e){
            e.printStackTrace();
            Cwarning("cancellazione dati fallita");         // segnala problema sulla console
            return false;
        }
        return true;
    }

    public static boolean append_gruppi(String nome, String proprietario )  {
        String path = "plugins/saveCord/GRUP";                          // percorso del file
        String dati = nome + ";*" + proprietario + "\n";                // dati da aggiungere

        append_file( path, dati);

        return true;
    }

    public static List<Gruppo> leggi_gruppi() {

        String path = "plugins/saveCord/GRUP";                    // percorso del file
        List<Gruppo> gruppi = new ArrayList<Gruppo>() ;
        List<String> linee = legge_file( path) ;                  // ottiene righe di un file


        for ( int i=0; i<linee.size(); i++){                      // scorre il file di righe
            gruppi.add( new Gruppo( linee.get(i) ) );             // crea e aggiunge alla lista dei gruppi
        }

        return gruppi;                                            // ristorna lista gruppi
    }

    public static boolean append_membro_gruppo( String gruppoNome, String membro, String permesso) {
        String path_u = "plugins/saveCord/GRUP";                // indirizzo file sorgente
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

                if ( gruppoNome.equals( val) ) {                      // controlla se ?? la gia da cancellare
                    liena +=  ";" + permesso + membro;          // aggiunge il nuovo membro
                }

                bw.append( liena+"\n");                         // copia la riga nel file di out
            }
            bw.close();                                         // chiude file in e out
            br.close();

            fr.delete();                                        // cancella il file di in
            fw.renameTo( fr);                                   // rinomina il file di out come quello di in


        } catch(IOException e){
            e.printStackTrace();
            Cwarning("agginta mebro fallita");             // segnala problema sulla console
            return false;
        }
        return true;
    }

    public static boolean remove_membro_gruppo( String gruppoNome, String membro) {
        String path_u = "plugins/saveCord/GRUP"   ;             // indirizzo file sorgente
        String path_t = "plugins/saveCord/temp" ;               // indirizzo file destinazione
        String liena;
        String val;
        String originale;
        String[] membri;

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

                if ( gruppoNome.equals( val) ) {                      // controlla se ?? la gia da cancellare

                    membri = originale.split(";");        // ottiene lista dei membri

                    liena = "" + membri[0] + ";" + membri[1];   // aggiunta nome gruppo e proprietario

                    for ( int i=2; i<membri.length; i++ ) {     // scorre la lista
                        if ( membri[i].substring(1).equals( membro) ) {      // se ?? il nome da togliere non lo aggiunge alla linea
                            continue;
                        }
                        liena += ";" + membri[i];
                    }

                    // rimozione gruppo membro
                }

                bw.append( liena+"\n");                         // copia la riga nel file di out
            }
            bw.close();                                         // chiude file in e out
            br.close();

            fr.delete();                                        // cancella il file di in
            fw.renameTo( fr);                                   // rinomina il file di out come quello di in


        } catch(IOException e){
            e.printStackTrace();
            Cwarning("cancellazione mebro fallita");       // segnala problema sulla console
            return false;
        }
        return true;
    }

    public static boolean cancella_gruppo( String gruppoNome) {
        String path_u = "plugins/saveCord/GRUP" ;           // indirizzo file sorgente
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

                if ( gruppoNome.equals( val) ) {                      // controlla se va cancellata la riga
                    continue;                                   // salta la copia della riga
                }

                bw.append( liena+"\n");                         // copia la riga nel file di out
            }
            bw.close();                                         // chiude file in e out
            br.close();

            fr.delete();                                        // cancella il file di in
            fw.renameTo( fr);                                   // rinomina il file di out come quello di in


        } catch(IOException e){
            e.printStackTrace();
            Cwarning("cancellazione gruppo fallita");     // segnala problema sulla console
            return false;
        }
        return true;
    }

    public static void Cwarning ( String msg ) {                       // manda un messaggio di pericolo in console
        Bukkit.getServer().getLogger().warning( "[Save-coordinates-Plugins] " + msg);
    }

    public static void Csevere ( String msg ) {                        // manda un messaggio di errore in console
        Bukkit.getServer().getLogger().severe( "[Save-coordinates-Plugins] " + msg);
    }

    public static void Cinfo ( String msg ) {                          // manda un messaggio di info in console
        Bukkit.getServer().getLogger().info( "[Save-coordinates-Plugins] " + msg);
    }

}

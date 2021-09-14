package me.enne139.ogg;

import me.enne139.PluginMain;

import java.util.ArrayList;
import java.util.List;

public class Gruppo {
    public String nome;
    public String proprietario;
    public List<String> membri;

    public Gruppo(String nome, String proprietario, List<String> membri) {
        this.nome = nome;
        this.proprietario = proprietario;
        this.membri = membri;
    }

    public Gruppo(String stringa) {
        String[] arg = stringa.split(";");
        this.nome = arg[0];
        this.proprietario = arg[1];
        this.membri = new ArrayList<String>();
        for ( int i=2; i<arg.length; i++) {
            this.membri.add( arg[i]);
        }

    }

    public boolean is_inside( String player) {

        if ( player.equals( this.proprietario.substring(1) ) ) {     // se il player è il proprietario
            return true;
        }

        for ( int i=0; i<membri.size(); i++) {

            if ( membri.get(i).substring(1).equals( player)) {      // cerca se il player e nel gruppo
                return true;
            }

        }

        return false;
    }

    public boolean puo_aggiungere( String player) {

        if ( player.equals( this.proprietario.substring(1) ) ) {     // se il player è il proprietario
            return true;
        }

        for ( int i=0; i<membri.size(); i++) {

            if ( membri.get(i).equals( "+" + player)) {      // cerca se il player puo aggiungere wp
                return true;
            }

        }

        return false;
    }

    static public List<Gruppo> get_grup_pro( String pl) {           // ottiene il
        List<Gruppo> gruppi;                                        // lista di gruppi
        List<Gruppo> out = new ArrayList<Gruppo>();
        gruppi = PluginMain.leggi_gruppi();                         // legge tutti i gruppi

        for ( int i=0; i<gruppi.size(); i++) {                      // scorre gruppi
            if ( gruppi.get(i).proprietario.substring(1).equals( pl) ) {  //  se il player è il proprietario del gruppo
                out.add( gruppi.get(i));
            }
        }

        return out;
    }

    static public List<Gruppo> get_grup_inside( String pl) {
        List<Gruppo> gruppi;                                        // lista di gruppi
        List<Gruppo> out = new ArrayList<Gruppo>();
        gruppi = PluginMain.leggi_gruppi();                         // legge tutti i gruppi

        for ( int i=0; i<gruppi.size(); i++) {                      // scorre gruppi
            if ( gruppi.get(i).is_inside( pl) ) {                   //  se il player è nel gruppo
                out.add( gruppi.get(i));
            }
        }

        return out;

    }

    static public List<Gruppo> get_grup_wp_add( String pl) {
        List<Gruppo> gruppi;                                        // lista di gruppi
        List<Gruppo> out = new ArrayList<Gruppo>();
        gruppi = PluginMain.leggi_gruppi();                         // legge tutti i gruppi

        for ( int i=0; i<gruppi.size(); i++) {                      // scorre gruppi
            if ( gruppi.get(i).is_inside( pl) ) {                   // se il player è nel gruppo
                if ( pl.charAt(0) == '+' ) {                        // se il player puo aggiungere al gruppo
                    out.add( gruppi.get(i));
                }
            }
        }

        return out;

    }

    static public boolean gruppo_esiste( String nomeGruppo) {

        List<Gruppo> gruppi;                                        // lista di gruppi
        gruppi = PluginMain.leggi_gruppi();                         // legge tutti i gruppi

        for ( int i=0; i<gruppi.size(); i++) {                      // scorre gruppi
            if ( gruppi.get(i).nome.equals( nomeGruppo) ) {         // se esiste gia
                return true;
            }
        }

        return false;
    }

    static public Gruppo get_gruppo( String nomeGruppo) {

        List<Gruppo> gruppi;                                        // lista di gruppi
        gruppi = PluginMain.leggi_gruppi();                         // legge tutti i gruppi

        for ( int i=0; i<gruppi.size(); i++) {                      // scorre gruppi
            if ( gruppi.get(i).nome.equals( nomeGruppo) ) {         // se esiste gia
                return gruppi.get(i);
            }
        }

        return null;
    }

}

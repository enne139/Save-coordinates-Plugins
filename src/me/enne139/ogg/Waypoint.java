package me.enne139.ogg;

import java.text.DecimalFormat;
import java.util.List;

public class Waypoint {

    public String nome;
    public double x;
    public double y;
    public double z;
    public String mondo;

    public Waypoint(String nome, double x, double y, double z, String mondo) {
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mondo = mondo;
    }

    public Waypoint(String liena) {
        String[] part = liena.split(";");

        this.nome = part[0];
        this.x = Double.parseDouble( part[1] );
        this.y = Double.parseDouble( part[2] );
        this.z = Double.parseDouble( part[3] );
        this.mondo = part[4];
    }

    public int getDisx( int x) {
        return Math.abs( (int) this.x - x );
    }

    public int getDisz( int z) {
        return Math.abs( (int) this.z - z );
    }

    @Override
    public String toString() {
        return ( nome + " : " + Math.round(x) + " ; " + Math.round(x) + " ; " + Math.round(z) + " ; " + mondo ) ;

    }
}

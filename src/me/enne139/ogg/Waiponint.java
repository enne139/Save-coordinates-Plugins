package me.enne139.ogg;

import java.text.DecimalFormat;
import java.util.List;

public class Waiponint {

    public String nome;
    public double x;
    public double y;
    public double z;
    public String mondo;

    public Waiponint( String nome, double x, double y, double z, String mondo) {
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mondo = mondo;
    }

    public Waiponint(String liena) {
        String[] part = liena.split(";");

        this.nome = part[0];
        this.x = Double.parseDouble( part[1] );
        this.y = Double.parseDouble( part[2] );
        this.z = Double.parseDouble( part[3] );
        this.mondo = part[4];
    }

    @Override
    public String toString() {
        return ( nome + " : " + Math.round(x) + " ; " + Math.round(x) + " ; " + Math.round(z) + " ; " + mondo ) ;

    }
}

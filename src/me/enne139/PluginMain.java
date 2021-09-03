package me.enne139;


import me.enne139.comandi.Com_savewp;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {

    @Override
    public void onEnable() {

        //set eventi
        //getServer().getPluginManager().registerEvents( new Playermovelistener(), this);

        //set comandi
        this.getCommand( "savewp").setExecutor(new Com_savewp());
        //this.getCommand( "wps").setTabCompleter(new Tab_wps());

    }
}

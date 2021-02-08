package fr.ishield.restau;

import fr.ishield.restau.api.MongoDBManager;
import fr.ishield.restau.command.CommandRestau;
import fr.ishield.restau.implementation.MongoManagerImpl;
import fr.ishield.restau.listener.RestauListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public static Main instance;

    MongoDBManager mongoDBManager;

    public MongoDBManager getMongoManager() {
        return mongoDBManager;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {


        instance = this;

        this.saveDefaultConfig();

        mongoDBManager = new MongoManagerImpl();

        getLogger().log(Level.INFO, "Plugin has been started....");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new RestauListener(), this);

        Objects.requireNonNull(getCommand("restau")).setExecutor(new CommandRestau());


    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin has been stopped....");
    }
}

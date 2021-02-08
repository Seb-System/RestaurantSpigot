package fr.ishield.restau.implementation;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import fr.ishield.restau.Main;
import fr.ishield.restau.api.MongoDBManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;

import static java.util.Objects.requireNonNull;

public class MongoManagerImpl implements MongoDBManager {

    private final MongoClient client;

    public MongoManagerImpl() {

        final FileConfiguration config = Main.getInstance().getConfig();

        final MongoCredential credential = MongoCredential.createCredential(
                requireNonNull(config.getString("database.user")),
                requireNonNull(config.getString("database.bdd")),
                requireNonNull(config.getString("database.pass")).toCharArray());

        final ConnectionString connectionString = new ConnectionString("mongodb://" + config.getString("database.host") + "/?ssl=false");
        final MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .build();

        this.client = MongoClients.create(settings);

        if(client != null)
            Bukkit.getLogger().log(Level.INFO, "The database has been linked...");


    }

    @Override
    public MongoClient getClient() {
        return client;
    }

    @Override
    public void close() {
        this.client.close();
    }

    @Override
    public MongoDatabase getRestauDatabase() {
        return getClient().getDatabase("restau");
    }

    @Override
    public MongoDatabase getReservationsDatabase() {
        return getClient().getDatabase("reservations");
    }
}

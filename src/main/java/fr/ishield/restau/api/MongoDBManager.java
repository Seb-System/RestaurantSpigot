package fr.ishield.restau.api;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;

public interface MongoDBManager {

    /**
     * Return the mongodb client instance
     * <p>
     * One client has multiple connection instances
     *
     * @return the client instance
     */
    MongoClient getClient();

    void close();

    MongoDatabase getRestauDatabase();

    MongoDatabase getReservationsDatabase();


}

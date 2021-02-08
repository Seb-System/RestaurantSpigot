package fr.ishield.restau.reservation;

import java.util.UUID;

public class Reservation {

    private final String name;
    private final UUID uuid;
    private final String jour;
    private final String ouverture;
    private final String fermeture;
    private final String adresse;

    public Reservation(String name, UUID uuid, String jour, String ouverture, String fermeture, String adresse) {
        this.name = name;
        this.uuid = uuid;
        this.jour = jour;
        this.ouverture = ouverture;
        this.fermeture = fermeture;
        this.adresse = adresse;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getJour() {
        return jour;
    }

    public String getOuverture() {
        return ouverture;
    }

    public String getFermeture() {
        return fermeture;
    }

    public String getAdresse() {
        return adresse;
    }
}

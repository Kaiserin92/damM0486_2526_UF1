package com.activitat2.Model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionManager {

    private static final String URI = "mongodb+srv://Gisela:1234@cluster0.vcvaeyy.mongodb.net/Tasques";
    private static final String DB_NAME = "Tasques";

    // Retorna la connexió a la base de dades
    public static MongoDatabase getConnection() {
        MongoClient mongoClient = MongoClients.create(URI);
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        System.out.println("Connectat a la base de dades: " + database.getName());
        return database;
    }
}

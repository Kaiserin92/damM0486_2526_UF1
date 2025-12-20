package com.activitat2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

public class QuickStart {
    public static void main(String[] args) {
        // URI de connexió al cluster
        String uri = "mongodb+srv://Gisela:1234@cluster0.vcvaeyy.mongodb.net/Tasques";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Obtenir la base de dades
            MongoDatabase database = mongoClient.getDatabase("Tasques");
            System.out.println("Connectat a la base de dades: " + database.getName());

            // Obtenir la col·lecció
            MongoCollection<Document> collection = database.getCollection("Entrades");

            // ----- INSERIR -----
            Document nouDoc = new Document("nom_alumne", "Marc")
                    .append("cognom1", "Gomez")
                    .append("cognom2", "Lopez")
                    .append("data_entrada", "2025-12-21")
                    .append("completa", false)
                    .append("observacions", "Primer exemple");
            collection.insertOne(nouDoc);
            System.out.println("Document inserit.");

            // ----- LLEGIR TOTS -----
            System.out.println("\nTots els documents:");
            for (Document d : collection.find()) {
                System.out.println(d.toJson());
            }

            // ----- ACTUALITZAR -----
            collection.updateOne(eq("nom_alumne", "Marc"), new Document("$set", new Document("completa", true)));
            System.out.println("\nDocument actualitzat (Marc, completa=true).");

            // ----- FILTRES -----
            System.out.println("\nTasques completades:");
            for (Document d : collection.find(eq("completa", true))) {
                System.out.println(d.toJson());
            }

            System.out.println("\nTasques d'un alumne específic (Marc):");
            for (Document d : collection.find(eq("nom_alumne", "Marc"))) {
                System.out.println(d.toJson());
            }

            System.out.println("\nTasques completades amb observacions:");
            for (Document d : collection.find(and(eq("completa", true), exists("observacions", true)) )) {
                System.out.println(d.toJson());
            }

            System.out.println("\nTasques amb observacions que contenen 'exemple':");
            for (Document d : collection.find(regex("observacions", "exemple"))) {
                System.out.println(d.toJson());
            }

            // ----- ELIMINAR -----
           collection.deleteOne(eq("nom_alumne", "Nerea"));
            System.out.println("\nDocumento eliminado (Nerea).");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

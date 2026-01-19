package com.activitat2.Model;

import org.bson.Document;

public class Tasca {

    private String nom;
    private String cognom1;
    private String cognom2;
    private String dataEntrada;
    private boolean completa;
    private String observacions;

    public Tasca(String nom, String cognom1, String cognom2, String dataEntrada, boolean completa, String observacions) {
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.dataEntrada = dataEntrada;
        this.completa = completa;
        this.observacions = observacions;
    }

    // Mètode que indica l'enunciat: convertir l'objecte a Document
    public Document toDocument() {
        return new Document("nom_alumne", nom)
                .append("cognom1", cognom1)
                .append("cognom2", cognom2)
                .append("data_entrada", dataEntrada)
                .append("completa", completa)
                .append("observacions", observacions);
    }

    @Override
    public String toString() {
        return nom + " " + cognom1 + " " + cognom2 +
               " | Data: " + dataEntrada +
               " | Completa: " + completa +
               " | Observacions: " + observacions;
    }
}

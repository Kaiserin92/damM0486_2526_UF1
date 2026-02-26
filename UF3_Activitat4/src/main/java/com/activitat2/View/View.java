package com.activitat2.View;

import java.util.List;
import org.bson.Document;

import com.activitat2.Model.Tasca;

import java.util.Scanner;

public class View {

    private static final Scanner read = new Scanner(System.in);

    public static void mostrarMenu() {
        System.out.println("-----------------------------------\n"
                + "Escolleix una opcio:\n"
                + "1. Afegir Tasca\n"
                + "2. Eliminar Tasca\n"
                + "3. Modificar Tasca\n"
                + "4. Llistar Totes\n"
                + "5. Llistar per Dates\n"
                + "6. Cercar per Nom\n"
                + "7. Sortir\n"
                + "-----------------------------------");
    }

    public static int llegirOpcio() {
        while (!read.hasNextInt()) {
            System.out.println("Introdueix un número vàlid:");
            read.next();
        }
        int opcio = read.nextInt();
        read.nextLine();
        return opcio;
    }

    public static void separador() {
        System.out.println("-----------------------------------");
    }

    public static void mostrarTotesTasques(List<Document> tasques) {
        System.out.println("-----------------------------------");
        System.out.println("\n--- Llista de Totes les Tasques ---");
        for (Document d : tasques) {
            System.out.println("Nom: " + d.getString("nom_alumne"));
            System.out.println("Cognoms: " + d.getString("cognom1") + " " + d.getString("cognom2"));
            System.out.println("Data d'entrada: " + d.getString("data_entrada"));
            System.out.println("Completada: " + (d.getBoolean("completa", false) ? "Sí" : "No"));
            String obs = d.getString("observacions");
            System.out.println("Observacions: " + (obs != null && !obs.isEmpty() ? obs : "-"));
            System.out.println("-----------------------------------");
        }
    }

    public static String llegirData(String missatge) {
        System.out.print(missatge);
        return read.nextLine();
    }

    public static Tasca llegirNovaTasca() {

        System.out.print("Nom alumne: ");
        String nom = read.nextLine();
        System.out.print("Cognom1: ");
        String cognom1 = read.nextLine();
        System.out.print("Cognom2: ");
        String cognom2 = read.nextLine();
        System.out.print("Data entrada (YYYY-MM-DD): ");
        String dataEntrada = read.nextLine();
        System.out.print("Completada? (true/false): ");
        boolean completa = Boolean.parseBoolean(read.nextLine());
        System.out.print("Observacions: ");
        String observacions = read.nextLine();

        return new Tasca(nom, cognom1, cognom2, dataEntrada, completa, observacions);
    }

    public static void mostrarTasquesAmbId(List<Document> tasques) {
        System.out.println("-----------------------------------");
        System.out.println("\n--- Llista de Tasques (amb ID) ---");
        for (Document d : tasques) {
            System.out.println("ID: " + d.getObjectId("_id").toHexString());
            System.out.println("Nom: " + d.getString("nom_alumne"));
            System.out.println("Cognoms: " + d.getString("cognom1") + " " + d.getString("cognom2"));
            System.out.println("Data d'entrada: " + d.getString("data_entrada"));
            System.out.println("Completada: " + (d.getBoolean("completa", false) ? "Sí" : "No"));
            String obs = d.getString("observacions");
            System.out.println("Observacions: " + (obs != null && !obs.isEmpty() ? obs : "-"));
            System.out.println("-----------------------------------");
        }
    }

    public static String llegirString(String missatge) {
        System.out.print(missatge);
        return read.nextLine();
    }

    public static Document llegirDadesPerModificar() {
        System.out.print("Nou nom: ");
        String nom = read.nextLine();
        System.out.print("Nou cognom1: ");
        String cognom1 = read.nextLine();
        System.out.print("Nou cognom2: ");
        String cognom2 = read.nextLine();
        System.out.print("Nova data d'entrada (YYYY-MM-DD): ");
        String dataEntrada = read.nextLine();
        System.out.print("Completada (true/false): ");
        boolean completa = Boolean.parseBoolean(read.nextLine());
        System.out.print("Observacions: ");
        String obs = read.nextLine();

        return new Document("nom_alumne", nom)
                .append("cognom1", cognom1)
                .append("cognom2", cognom2)
                .append("data_entrada", dataEntrada)
                .append("completa", completa)
                .append("observacions", obs);
    }

}

package dam.m6.uf2.View;

import java.util.List;
import java.util.Scanner;

import dam.m6.uf2.Model.Atleta;
import dam.m6.uf2.Model.Esport;

public class MainView {
    public int mainMenu() {
        Scanner read = new Scanner(System.in);
        int opcio = -1;

        while (opcio == -1) {
            System.out.println("--------------------------------");
            System.out.println("Introdueix una opció (1-5): ");
            System.out.println("1. Afegir esport");
            System.out.println("2. Afegir atleta");
            System.out.println("3. Cerca per nom d'atleta");
            System.out.println("4. Llistar atletas");
            System.out.println("5. Sortir");
            System.out.println("--------------------------------");

            if (read.hasNextInt()) {
                int input = read.nextInt();
                if (input >= 1 && input <= 5) {
                    opcio = input;
                } else {
                    System.out.println("Opció no vàlida.");
                }
            } else {
                System.out.println("⚠ Entrada no vàlida.");
                read.next();
            }
        }

        return opcio;
    }

    public String sportForm() {
        Scanner read = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("Nom de l'esport:");
        return read.nextLine();
    }

    public void printSeparador() {
        System.out.println("--------------------------------");
    }

    public void llistaEsports(List<Esport> esports) {
        printSeparador();
        for (Esport esport : esports) {
            System.out.println(esport);
        }
    }

    public int demanaEsport() {
        Scanner read = new Scanner(System.in);
        int codEsport = -1;
        System.out.println("Introdueix el codi de l'esport per llistar atletas: ");
        if (read.hasNextInt()) {
            codEsport = read.nextInt();
        } else {
            System.out.println("⚠ Entrada no válida.");
            read.next();
        }
        return codEsport;
    }

    public void llistaAtletes(List<Atleta> atletes) {
        printSeparador();
        for (Atleta atleta : atletes) {
            System.out.println(atleta);
        }
    }

    public Atleta atletaForm() {
        Scanner read = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("Nom de l'atleta:");
        String nom = read.nextLine();

        System.out.println("Codi de l'esport:");
        int codEsport = read.nextInt();
        read.nextLine(); // netejar buffer

        return new Atleta(0, nom, codEsport);
    }

    public String cercaAtleta(){
        Scanner read = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("Nom de l'atleta:");
        return read.nextLine();
    }

}

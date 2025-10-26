import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import View.PenjatView;

public class game {
    private static final Random aleatori = new Random();

    public static void playGame(String usuari) {
        // Obtenim llistat paraules
        List<Paraula> records = paraulesUtils.LlegirParaules("model/paraules.dat");

        // Escolim una paraula aleatoriament
        Paraula rec = records.get(aleatori.nextInt(records.size()));
        String paraulaJoc = rec.getParaula();
        int puntsParaula = rec.getPunts();

        Set<Character> correct = new HashSet<>();
        Set<Character> wrong = new HashSet<>();

        char[] mostrat = new char[paraulaJoc.length()];
        for (int i = 0; i < paraulaJoc.length(); i++) {
            mostrat[i] = '_';
        }

        int intents = Config.readMaxIntents();

        System.out.println("Comença el joc! Adivina la paraula:");
        while (true) {
            // mostrar estat actual
            System.out.print("Paraula: ");
            for (char c : mostrat)
                System.out.print(c + " ");
            System.out.println();
            System.out.println("Intents restants: " + intents);
            System.out.print("Lletres fallades: ");
            for (char c : wrong)
                System.out.print(c + " ");
            System.out.println();

            // demanar una lletra
            System.out.print("Introdueix una lletra: ");
            String line = PenjatView.SC.nextLine();
            if (line == null) {
                System.out.println("Entrada nul·la. Sortida del joc.");
                return;
            }
            line = line.trim().toUpperCase(Locale.getDefault());
            if (line.isEmpty()) {
                System.out.println("No has introduït res. Intenta-ho de nou.");
                continue;
            }

            // només acceptar la primera lletra de l'entrada
            char lletra = line.charAt(0);
            if (!Character.isLetter(lletra)) {
                System.out.println("Introdueix una lletra vàlida (A-Z).");
                continue;
            }

            // ja provada?
            if (correct.contains(lletra) || wrong.contains(lletra)) {
                System.out.println("Ja has provat la lletra '" + lletra + "'. Prova una altra.");
                continue;
            }

            // comprovar si la lletra està a la paraula
            boolean trobada = false;
            for (int i = 0; i < paraulaJoc.length(); i++) {
                if (paraulaJoc.charAt(i) == lletra) {
                    mostrat[i] = lletra;
                    trobada = true;
                }
            }

            if (trobada) {
                correct.add(lletra);
                System.out.println("Correcte!");
            } else {
                wrong.add(lletra);
                intents--; // cada lletra fallada resta 1 intent
                System.out.println("Incorrecte! Perds 1 intent.");
            }

            // verificar si ja està completada la paraula (sense guions)
            boolean completed = true;
            for (char c : mostrat) {
                if (c == '_') {
                    completed = false;
                    break;
                }
            }
            if (completed) {
                System.out.print("Enhorabona! Has endevinat la paraula: ");
                System.out.println(paraulaJoc);
                // sumar punts de la paraula a l'usuari guardat a model/users/<usuari>.usr
                UserUtils.actualitzarPuntuacio(usuari, puntsParaula);
                return;
            }

            // Si s'han acabat els intents, el jugador perd la partida
            if (intents <= 0) {
                System.out.println("S'han acabat els intents. Has perdut.");
                System.out.println("La paraula era: " + paraulaJoc);
                // restar 5 punts al fitxer de l'usuari
                UserUtils.actualitzarPuntuacio(usuari, -5);
                return;
            }

        }
    }

}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class paraulesUtils{
    private static final Random aleatori = new Random();
    public static boolean afegirParaula(String paraula) {
        
        File f = new File("model/paraules.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f, true);
            boolean newLine = f.exists() && f.length() > 0;

            if(newLine) {
                fos.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            }
            fos.write(paraula.toUpperCase(Locale.getDefault()).getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println("Error escrivint al fitxer: " + e.getMessage());
                    return false;
                }
            }
        }   
    }

    public static void mostrarParaules() {
        File f = new File("model/paraules.txt");
        if (!f.exists()) {
            System.out.println("No hi ha el fitxer de paraules.");
            return;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            byte[] all = fis.readAllBytes();
            String content = new String(all, StandardCharsets.UTF_8);
            if (content.isBlank()) {
                System.out.println("El fitxer està buit.");
                return;
            }
            String[] lines = content.split("\\R");
            System.out.println("Paraules:");
            for (String line : lines) {
                String p = line.trim();
                if (!p.isEmpty()) {
                    System.out.println(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error llegint el fitxer: " + e.getMessage());
        } finally {
            if (fis != null) {
                try { fis.close(); } catch (IOException ignored) {}
            }
        }
    }

    public static void playGame(){
        List<String> words = LlegirParaules("model/paraules.txt");
        if (words.isEmpty()) {
            System.out.println("No hi ha paraules per jugar.");
            return;
        }

        String paraulaJoc = words.get(aleatori.nextInt(words.size()));

        Set<Character> correct = new HashSet<>();
        Set<Character> wrong = new HashSet<>();

        char[] mostrat = new char[paraulaJoc.length()];
        for (int i = 0; i < paraulaJoc.length(); i++) {
            mostrat[i] = '_';
        }

        System.out.println("Comença el joc! Adivina la paraula:");
        while (true) {
            // mostrar estat actual
            System.out.print("Paraula: ");
            for (char c : mostrat) {
                System.out.print(c + " ");
            }
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
                System.out.println("Incorrecte!");
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
                return;
            }
        }
    }
    private static List<String> LlegirParaules(String path) {
        List<String> result = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()){
            return result;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            byte[] all = fis.readAllBytes();
            String content = new String(all, StandardCharsets.UTF_8);
            if (content.isBlank()){
                return result;
            }
            String[] lines = content.split("\\R");
            for (String line : lines) {
                String p = line.trim();
                if (!p.isEmpty()) result.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error llegint paraules: " + e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
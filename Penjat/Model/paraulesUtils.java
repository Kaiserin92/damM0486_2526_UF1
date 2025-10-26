import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class paraulesUtils {

    public static boolean editarParaula(String paraula, int punts, int pos) {
        final File file = new File("model", "paraules.dat");

        final int CHAR_COUNT = 25; // nombre de caràcters reservats per paraula (25)
        final int RECORD_SIZE = CHAR_COUNT * 2 + 4; // mida del registre: 25 chars * 2 bytes + 4 bytes int = 54 bytes

        // Validació de la posició
        if (pos < 1) {
            return false;
        }

        // Assegurar que la cadena no és null
        if (paraula == null) {
            paraula = "";
        }

        // Introdueix la paraula en majúscules
        paraula = paraula.toUpperCase();

        // Afegim espais fins a 25 caràcters
        StringBuilder sb = new StringBuilder(paraula);
        while (sb.length() < CHAR_COUNT)
            sb.append(' ');
        String novaParaula = sb.toString();

        // Calculem el desplaçament del registre demanat
        long desplaçament = (long) (pos - 1) * RECORD_SIZE;

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            // Calculem el nombre de registres existents actualment al fitxer.
            long recordsActuals = raf.length() / RECORD_SIZE;

            // Comprovem que l'usuari demana una posició existent o exactament l'opció
            // d'afegir al final
            // (si pos == recordsActuals + 1 vol dir que vol afegir una nova paraula)
            if (pos > recordsActuals + 1) {
                return false;
            }

            // Moure el punter del fitxer al desplaçament calculat per començar a escriure
            raf.seek(desplaçament);

            // Escriure els caràcters de la paraula
            for (int i = 0; i < CHAR_COUNT; i++)
                raf.writeChar(novaParaula.charAt(i));

            // Escriure l'int de punts (4 bytes) immediatament després dels caràcters.
            raf.writeInt(punts);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String mostrarParaules() {
        File file = new File("model", "paraules.dat");
        final int CHAR_COUNT = 25;
        final int RECORD_SIZE = CHAR_COUNT * 2 + 4; // 25 chars * 2 bytes + 4 bytes int

        StringBuilder out = new StringBuilder();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long records = raf.length() / RECORD_SIZE;
            int index = 1;

            for (int r = 0; r < records; r++) {
                // llegir 25 chars
                StringBuilder sb = new StringBuilder(CHAR_COUNT);
                for (int c = 0; c < CHAR_COUNT; c++) {
                    sb.append(raf.readChar());
                }

                // llegir els punts
                int punts = raf.readInt();

                // reemplaçar nul per espais i trim
                String paraula = sb.toString().replace('\0', ' ').trim();
                if (!paraula.isEmpty()) {
                    out.append(index).append(". ").append(paraula).append(" / ").append(punts).append('\n');
                    index++;
                }
            }

            out.append(index).append(". Afegir paraula");

        } catch (IOException e) {
            e.printStackTrace();
            return "Error llegint les paraules: " + e.getMessage();
        }
        return out.toString();
    }

    public static List<Paraula> LlegirParaules(String path) {
        List<Paraula> result = new ArrayList<>();

        File f = new File(path);


        final int CHAR_COUNT = 25;
        final int RECORD_SIZE = CHAR_COUNT * 2 + 4; // 25 chars * 2 bytes + 4 bytes int

        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            // Longitud total del fitxer en bytes
            long length = raf.length();

            // Nombre de registres
            long records = length / RECORD_SIZE;

            // Iterem cada registre existent
            for (long r = 0; r < records; r++) { 
                StringBuilder sb = new StringBuilder(); 

                for (int i = 0; i < CHAR_COUNT; i++) { 
                    sb.append(raf.readChar());
                }
                
                int punts = raf.readInt();

                String paraula = sb.toString().trim();

                // Afegim un nou objecte Paraula amb la paraula i els punts a la llista
                result.add(new Paraula(paraula, punts));
            }
        } catch (IOException e) {
            System.out.println("Error llegint paraules: " + e.getMessage());
        }

        return result;
    }

}

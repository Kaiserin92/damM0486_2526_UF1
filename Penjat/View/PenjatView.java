package View;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PenjatView {
    public static final Scanner SC = new Scanner(System.in);

    public static int menuPrincipal() {

        int opcio = -1;

        while (true) {
            System.out.println("---------------------------");
            System.out.println("Selecciona una opcio (1-3):\t");
            System.out.println("1. Login\n" +
                    "2. Register\n" +
                    "3. Sortir");
            System.out.println("---------------------------");
            String opcioStr = SC.nextLine();

            try {
                opcio = Integer.parseInt(opcioStr.trim());
                if (opcio >= 1 && opcio <= 3) {
                    return opcio;
                } else {
                    System.out.println("Error: Introdueix un numero entre 1 i 3");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada no vàlida. Introdueix un numero.");
            }

        }

    }

    public static int menuJoc(String username) {

        int opcio = -1;
        while (true) {
            if (username.equals("admin")) {
                System.out.println("---------------------------");
                System.out.println("Selecciona una opcio (0-3):\t");
                System.out.println("0. Sortir\n" +
                        "1. Jugar\n" +
                        "2. Editar paraules\n" +
                        "3. Modificar configuració");
                System.out.println("---------------------------");
                String opcioStr = SC.nextLine();
                try {
                    opcio = Integer.parseInt(opcioStr.trim());
                    if (opcio >= 0 && opcio <= 3) {
                        return opcio;
                    } else {
                        System.out.println("Error: Introdueix un numero entre 0 i 3");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no vàlida. Introdueix un numero.");
                }
            } else {
                System.out.println("---------------------------");
                System.out.println("Selecciona una opcio (0-1):\t");
                System.out.println("0. Sortir\n" +
                        "1. Jugar");
                System.out.println("---------------------------");
                String opcioStr = SC.nextLine();
                try {
                    opcio = Integer.parseInt(opcioStr.trim());
                    if (opcio >= 0 && opcio <= 1) {
                        return opcio;
                    } else {
                        System.out.println("Error: Introdueix un numero entre 0 i 1");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no vàlida. Introdueix un numero.");
                }
            }
        }

    }

    public static String[] menuRegistre() {
        System.out.println("---------------------------");
        System.out.println("Introdueix el teu nom: ");
        String name = SC.nextLine();
        System.out.println("Introdueix el teu usuari: ");
        String user = SC.nextLine();
        System.out.println("Introdueix la teva contraseya: ");
        String password = SC.nextLine();
        System.out.println("---------------------------");
        return new String[] { name, user, password };

    }

    public static String[] menuLogin() {
        System.out.println("---------------------------");
        System.out.println("Introdueix el teu usuari: ");
        String user = SC.nextLine();
        System.out.println("Introdueix la teva contraseya: ");
        String password = SC.nextLine();
        System.out.println("---------------------------");
        return new String[] { user, password };
    }

    public static String[] menuConfig() {
        System.out.println("---------------------------");
        System.out.println("Introdueix la versio: ");
        String version = SC.nextLine();
        System.out.println("Introdueix el numero de intents: ");
        String intents = SC.nextLine();
        System.out.println("---------------------------");
        return new String[] { version, intents };
    }

    public static String[] menuModificarFitxerParaules() {
        System.out.print("Introdueix el numero de la paraula a modificar / afegir: ");
        String num = SC.nextLine();
        System.out.print("Introdueix la paraula: ");
        String paraula = SC.nextLine();
        System.out.print("Introdueix la puntuacio: ");
        String puntuacio = SC.nextLine();
        return new String[] { paraula, puntuacio, num };
    }

    public static void mostrarMissatge(String missatge) {
        System.out.println("---------------------------");
        System.out.println(missatge);
        System.out.println("---------------------------");
    }

    public static void missatgeBenvinguda(String username, int punts) {
        System.out.println("Benvingut/da " + username + ". Tens " + punts + " punts.");
    }
}

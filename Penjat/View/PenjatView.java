import java.util.Scanner;

public class PenjatView {

    static final Scanner SC = new Scanner(System.in);

    public static int menuPrincipal() {

        int opcio = -1;

        while(true) {
            System.out.println("Selecciona una opcio (1-3):\t");
            System.out.println("1. Login\n"+
                                "2. Register\n"+
                                "3. Sortir");
            
            String opcioStr = SC.nextLine();

            try {
                opcio = Integer.parseInt(opcioStr.trim());
                if(opcio >=1 && opcio <= 3) {
                    return opcio;
                }else{
                    System.out.println("Error: Introdueix un numero entre 1 i 3");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada no vàlida. Introdueix un numero.");
            }
            
        }
       
    }

    public static int menuJoc(String username) {

        int opcio = -1;
        while(true) {
            if(username.equals("admin")){
                System.out.println("Selecciona una opcio (1-3):\t");
                System.out.println("1. Jugar\n"+
                                    "2. Sortir\n"+
                                    "3. Afegir paraules");
                String opcioStr = SC.nextLine();
                try {
                    opcio = Integer.parseInt(opcioStr.trim());
                    if(opcio >=1 && opcio <= 3) {
                        return opcio;
                    }else{
                        System.out.println("Error: Introdueix un numero entre 1 i 3");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no vàlida. Introdueix un numero.");
                }
            }else{
                System.out.println("Selecciona una opcio (1-2):\t");
                System.out.println("1. Jugar\n"+
                                    "2. Sortir");
                String opcioStr = SC.nextLine();
                try {
                    opcio = Integer.parseInt(opcioStr.trim());
                    if(opcio >=1 && opcio <= 2) {
                        return opcio;
                    }else{
                        System.out.println("Error: Introdueix un numero entre 1 i 2");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no vàlida. Introdueix un numero.");
                } 
            }
        }

    }

    public static String[] menuRegistre() {

        System.out.println("Introdueix el teu nom: ");
        String name = SC.nextLine();
        System.out.println("Introdueix el teu usuari: ");
        String user = SC.nextLine();
        System.out.println("Introdueix la teva contraseya: ");
        String password = SC.nextLine();

        return new String[] {name, user, password};

    }

    public static String[] menuLogin() {

        System.out.println("Introdueix el teu usuari: ");
        String user = SC.nextLine();
        System.out.println("Introdueix la teva contraseya: ");
        String password = SC.nextLine();

        return new String[] {user, password};
    }

    public static String introduirParaula() {
    
        System.out.println("Introdueix la paraula a afegir: ");
        return SC.nextLine();
    }

}

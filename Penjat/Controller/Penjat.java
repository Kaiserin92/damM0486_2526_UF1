import java.io.File;

public class Penjat {

    public final static int LOGIN = 1, REGISTER = 2, EXIT = 3;
    public final static int JUGAR = 1, EXIT_JOC = 2, AFEGIR_PARAULES = 3;
    // MCV
    public static void main(String[] args) {

        File adminFile = new File("model/users", "admin.usr");
        if (!adminFile.exists()) {
            User admin = new User("Administrator", "admin", "admin", true);
            UserUtils.registerUser(admin);
        }

        boolean exit = false;

        while(!exit){

            int opcio = PenjatView.menuPrincipal();

            switch(opcio){
                case REGISTER:
                    String [] dadesReg = PenjatView.menuRegistre();
                    String name = dadesReg[0];
                    String username = dadesReg[1];
                    String password = dadesReg[2];
                    User newUser = new User(name, username, password);
                    UserUtils.registerUser(newUser);
                    break;
                case LOGIN:
                    String [] dadesLog = PenjatView.menuLogin();
                    String usernameLog = dadesLog[0];
                    String passwordLog = dadesLog[1];

                    if(UserUtils.loginUser(usernameLog, passwordLog)){
                        System.out.println("Usuari correcte.");
                        boolean exitJoc = false;
                        while(!exitJoc){
                            opcio = PenjatView.menuJoc(usernameLog);
                            switch(opcio){
                                case JUGAR:
                                    paraulesUtils.playGame();
                                    break;
                                case AFEGIR_PARAULES:
                                    paraulesUtils.mostrarParaules();
                                    if (paraulesUtils.afegirParaula(PenjatView.introduirParaula())) {
                                        System.out.println("Paraula afegida.");
                                    } else {
                                        System.out.println("No s'ha pogut afegir la paraula.");
                                    }
                                    break;
                                case EXIT_JOC:
                                    exitJoc = true;
                                    break;
                            }
                        }

                        exit = true;
                    }else{
                        System.out.println("Usuari o contrasenya incorrectes.");
                    }

                    break;
                case EXIT:
                    exit = true;
                    break;
            }
        }
    }
}

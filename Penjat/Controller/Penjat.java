import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import View.PenjatView;


public class Penjat {
    public final static int LOGIN = 1, REGISTER = 2, EXIT = 3;
    public final static int EXIT_JOC = 0, JUGAR = 1, EDITAR_PARAULES = 2, MOD_CONFIG = 3;

    // MCV
    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException {

        // Creació del usuari admin la primera vegada que s'executa el programa
        File adminFile = new File("model/users", "admin.usr");
        if (!adminFile.exists()) {
            User admin = new User("Administrator", "admin", "admin", true);
            UserUtils.registerUser(admin);
        }

        // Creació del fitxer de configuració la primera vegada que s'executa el
        // programa
        File configFile = new File("model", "config.bin");
        if (!configFile.exists()) {
            Config config = new Config("1.0", 10);
            Config.writeConfig(config);
            PenjatView.mostrarMissatge("Arxiu de configuració creat.");
        }

        // Creació del fitxer de paraules la primera vegada que s'executa el programa

        Paraula[] paraules = new Paraula[10];
        paraules[0] = new Paraula("SOL", 3);
        paraules[1] = new Paraula("BLAU", 4);
        paraules[2] = new Paraula("ARBRE", 5);
        paraules[3] = new Paraula("ESCOLA", 6);
        paraules[4] = new Paraula("PLATJA", 6);
        paraules[5] = new Paraula("PENJAT", 6);
        paraules[6] = new Paraula("MUNTANYA", 8);
        paraules[7] = new Paraula("ORDINADOR", 9);
        paraules[8] = new Paraula("ARQUITECTURA", 12);
        paraules[9] = new Paraula("INTERNACIONAL", 13);

        File paraulesFile = new File("model", "paraules.dat");
        if (!paraulesFile.exists()) {
            StringBuilder b = null;
            try (RandomAccessFile raf = new RandomAccessFile(paraulesFile, "rw")) {
                for (int i = 0; i < paraules.length; i++) {
                    b = new StringBuilder(paraules[i].getParaula());
                    b.setLength(25);
                    raf.writeChars(b.toString());
                    raf.writeInt(paraules[i].getPunts());
                }
                PenjatView.mostrarMissatge("Arxiu de paraules creat.");
            }
        }

        // Creació del fitxer de ranking la primera vegada que s'executa el programa
        File rankingFile = new File("model", "ranking.dat");
        if (!rankingFile.exists()) {
            try {
                rankingFile.createNewFile();
                PenjatView.mostrarMissatge("Arxiu de ranking creat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean exit = false;

        while (!exit) {

            int opcio = PenjatView.menuPrincipal();

            switch (opcio) {
                case REGISTER:
                    String[] dadesReg = PenjatView.menuRegistre();
                    String name = dadesReg[0];
                    String username = dadesReg[1];
                    String password = dadesReg[2];
                    User newUser = new User(name, username, password);
                    UserUtils.registerUser(newUser);
                    break;
                case LOGIN:
                    String[] dadesLog = PenjatView.menuLogin();
                    String usernameLog = dadesLog[0];
                    String passwordLog = dadesLog[1];

                    if (UserUtils.loginUser(usernameLog, passwordLog)) {
                        PenjatView.missatgeBenvinguda(usernameLog, UserUtils.getPunts(usernameLog));
                        boolean exitJoc = false;
                        while (!exitJoc) {
                            opcio = PenjatView.menuJoc(usernameLog);
                            switch (opcio) {
                                case JUGAR:
                                    game.playGame(usernameLog);
                                    PenjatView.mostrarMissatge(UserUtils.mostrarRanking());
                                    break;
                                case EDITAR_PARAULES:
                                    PenjatView.mostrarMissatge(paraulesUtils.mostrarParaules());
                                    String[] editar = PenjatView.menuModificarFitxerParaules();
                                    String paraula = editar[0];
                                    int punts = Integer.parseInt(editar[1]);
                                    int posicio = Integer.parseInt(editar[2]);
                                    boolean paraulaOk = paraulesUtils.editarParaula(paraula, punts, posicio);
                                    if (paraulaOk) {
                                        PenjatView.mostrarMissatge("Paraula afegida/modificada correctament.");
                                    } else {
                                        PenjatView.mostrarMissatge("Error: No s'ha pogut afegir/modificar la paraula.");
                                    }
                                    break;
                                case MOD_CONFIG:
                                    String[] config = PenjatView.menuConfig();
                                    Config newConfig = new Config(config[0], Integer.parseInt(config[1]));
                                    Config.writeConfig(newConfig);
                                    break;
                                case EXIT_JOC:
                                    PenjatView.mostrarMissatge("Programa finalitzat.");
                                    exitJoc = true;
                                    break;
                            }
                        }

                        exit = true;
                    } else {
                        PenjatView.mostrarMissatge("Usuari o contrasenya incorrectes.");
                    }

                    break;
                case EXIT:
                    exit = true;
                    break;
            }
        }
    }
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    private static final File USERS_DIR = new File("model/users");

    public static void registerUser(User newUser) {

        String username = newUser.getUser();
        File userPath = new File(USERS_DIR, username + ".usr");

        if (userPath.exists()) {
            System.out.println("El usuari ja existeix. No es pot registrar.");
            return;
        } else {
            USERS_DIR.mkdirs(); // <-- asegura que exista la carpeta "users"
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userPath))) {
                oos.writeObject(newUser);
                System.out.println("Usuari creat: " + newUser.getUser());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean loginUser(String username, String passw) {

        File userPath = new File(USERS_DIR, username + ".usr");
        if (!userPath.exists()) {
            return false;
        } else {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userPath));
                User u = (User) ois.readObject();
                ois.close();
                if (u.getPassword().equals(passw)) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

    }

    public static int getPunts(String usernameLog) throws ClassNotFoundException {
        File userPath = new File(USERS_DIR, usernameLog + ".usr");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userPath))) {
            User u = (User) ois.readObject();
            return u.getPunts();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean actualitzarPuntuacio(String username, int newPunts) {
        File userFile = new File("model/users", username + ".usr");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {
            User user = (User) ois.readObject();
            user.addPunts(newPunts);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
                oos.writeObject(user);
                oos.close();
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public static String mostrarRanking() {
        File usersDir = new File("model/users");
        File[] files = usersDir.listFiles((d, name) -> name.endsWith(".usr"));

        if (files == null || files.length == 0) {
            return "No hi ha usuaris registrats.";
        }

        class Entry {
            final String username;
            final int punts;

            Entry(String u, int p) {
                username = u;
                punts = p;
            }
        }

        List<Entry> llista = new ArrayList<>(files.length);

        for (File f : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                User u = (User) ois.readObject();
                llista.add(new Entry(u.getUser(), u.getPunts()));
            } catch (IOException | ClassNotFoundException e) {
            }
        }

        if (llista.isEmpty()) {
            return "No hi ha usuaris registrats.";
        }

        // ordenar per punts descendent; en cas d'empat, per nom d'usuari ascendent
        llista.sort((a, b) -> {
            int puntsCmp = Integer.compare(b.punts, a.punts);
            if (puntsCmp != 0) {
                return puntsCmp; // l'ordre queda decidit pels punts (descendent)
            } else {
                // empat en punts: ordenar per nom d'usuari
                return a.username.compareTo(b.username);
            }

        });

        StringBuilder sb = new StringBuilder();
        sb.append("RANKING D'USUARIS\n");
        int pos = 1;
        for (Entry e : llista) {
            sb.append(String.format("%2d. %s : %d\n", pos++, e.username, e.punts));
        }
        return sb.toString();
    }

}

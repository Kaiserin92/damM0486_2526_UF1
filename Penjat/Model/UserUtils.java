import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
                if(u.getPassword().equals(passw)){
                    return true;
                }else{
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

    }
    
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class ex22 {
    public static void main(String[] args) {
        
        String file = "userText.txt";

        File f = new File(file);

        String userText = "";

        try {
            Scanner scan = new Scanner(System.in);
            FileOutputStream fos = new FileOutputStream(f);
            System.out.println("Escribe quit para salir");
            while (!userText.equals("quit")) {
               
                userText = scan.nextLine();

                fos.write(userText.getBytes());
                fos.write("\n".getBytes());
            }
            fos.close();
            

            FileInputStream fis = new FileInputStream(f);

        } catch (Exception e) {

        }

    }
}

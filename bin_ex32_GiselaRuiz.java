/*
Es demana a l’usuari un codi dels que s’han desat al fitxer secret.bin. 
El programa llegirà el fitxer i mostrarà el secret associat.
Si no es troba, Dirà “Codi no trobat” 
Heu de fer-ho utilitzant DataInputStream.readInt() i DataInputStream.readChar() 

*/

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class bin_ex32_GiselaRuiz {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String file = "secret.bin";
        DataInputStream dis = null;
        try {
            System.out.println("Introdueix el codi: ");
            int codi = scan.nextInt();
            dis = new DataInputStream(new FileInputStream(file));
            boolean exit = false;
            boolean trobat = false;
            String secret = "";
            while (!exit) {
                if (dis.readInt() == codi) {
                    exit = true;
                    trobat = true;
                    int i = 0;
                    while (i < 3) {
                        secret += dis.readChar();
                        i++;
                    }
                    System.out.println("Secret: " + secret);
                } else {
                    int i = 0;
                    while (i < 3) {
                        dis.readChar();
                        i++;
                    }
                }
            }
            if (!trobat) {
                System.out.println("Codi no trobat.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

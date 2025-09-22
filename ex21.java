
/*Fes un programa que demani un nom de fitxer a l'usuari,
i mostri quantes lletres 'a' hi ha al fitxer.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ex21 {
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        System.out.println("Introdueix el nom del fitxer: ");
        String nameFile = scan.nextLine();

        int cont = 0;

        try {
            FileInputStream fis = new FileInputStream(nameFile);
            int charLlegit = fis.read();

            while(charLlegit != -1) {
                char c = (char)charLlegit;
                if(c == 'a') {
                    cont++;
                }
                charLlegit = fis.read();
            }

            fis.close();
            System.out.println("Hi ha " + cont + " lletres 'a' al fitxer.");

        }catch(FileNotFoundException e) {
            System.out.println("El fitxer no existeix.");
        }catch(IOException e) {
            System.out.println("Error al llegir el fitxer." + e.getMessage());
        }
        

    }
}
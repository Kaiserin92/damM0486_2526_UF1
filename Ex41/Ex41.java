/*
Has de programar un sistema de login / register.
Per fer-ho utilitzarem una classe User:

A l’inici, el programa demana dos strings: user / pass. ex: alex / 1234
Cal cercar un fitxer anomenat alex.usr que conté un objecte User. 

Si existeix, el carreguem i comprovem la contrasenya. Acabem amb un missatge:
“Accés correcte al sistema” o “Accés no concedit: La contrasenya no és correcta”

Si no existeix el fitxer diem “No s’ha trobat l’usuari, vols registrar-te?”
Si l’usuari respon que sí, hem de guardar l’objecte al fitxer alex.usr

 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Ex41 {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Introdueix usuari:");
        String user = scan.nextLine();
        System.out.println("Introdueix contrasenya:");
        String pass = scan.nextLine();

        File f = new File(user + ".usr");
        if (f.exists()) {
            scan.close();
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(user + ".usr")));
                User u = (User) ois.readObject();
                ois.close();
                if (u.pass.equals(pass)) {
                    System.out.println("Accés correcte al sistema");
                } else {
                    System.out.println("Accés no concedit: La contrasenya no és correcta");
                }
            } catch (Exception e) {
                System.out.println("Error al llegir l'usuari: " + e.getMessage());
            }

        } else {
            System.out.println("No s’ha trobat l’usuari, vols registrar-te?\n" +
                    "1. Si\n2.No");
            int option = scan.nextInt();
            switch (option) {
                case 1:

                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(user + ".usr")));
                        User usr = new User(user, pass);
                        oos.writeObject(usr);
                        oos.close();
                        System.out.println("Usuari creat: " + usr.nom);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    System.out.println("Programa finalitzat.");
                    break;
                default:
                    System.out.println("Opció escollida no valida.\nPrograma finalitzat.");
            }
            scan.close();
        }

    }
}
/*
En aquest programa, es generarà un fitxer binari (secret.bin) que contindrà una sèrie de parelles codi-secret.
 El codi és un nombre enter i el secret una cadena de 3 caràcters. El fitxer estarà ordenat de menor a major pels codis.
1:ABA
4:FKX
6:BSP
…
Per generar el fitxer es procedirà de la següent manera: el primer codi serà un nombre aleatori entre 1 i 3. 
Cadascun dels següents codis serà com l'anterior sumant-hi una quantitat entre 1 i 3. Els secrets seran combinacions 
de tres lletres agafades aleatòriament d'entre el conjunt de lletres minúscules.
El programa generarà un total de 10 parelles codi-secret.
IMPORTANT: Imprimeix per pantalla les parelles codi secret en aquest format:
Nota: per guardar les cadenes utilitza writeChars i no writeUTF, ja que la longitud d'una cadena UTF és variable.
 */

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class bin_ex31_GiselaRuiz {
    public static void main(String[] args) {
        Random random = new Random();
        int num;
        num = random.nextInt(3) + 1;
        File f = new File("secret.bin");
        DataOutputStream dos = null;

        try {
            dos = new DataOutputStream(new FileOutputStream(f));
            dos.writeInt(num);
            System.out.print(num + ":");
            introduirLletres(dos, random, 3);
            System.out.println();
            for (int i = 0; i < 9; i++) {
                num += random.nextInt(3) + 1;
                dos.writeInt(num);
                System.out.print(num + ":");
                introduirLletres(dos, random, 3);
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void introduirLletres(DataOutputStream dos, Random random, int quantitat) throws IOException {
        for (int i = 0; i < quantitat; i++) {
            int lletraRandom = random.nextInt(26);
            char lletra = (char) ('A' + lletraRandom);
            dos.writeChar(lletra);
            System.out.print(lletra);
        }
    }
}

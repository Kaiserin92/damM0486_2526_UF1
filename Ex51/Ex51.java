import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Ex51 {

	public static void main(String[] args) {
		Pais[] paisos = new Pais[5];

		paisos[0] = new Pais("Albània", "ALB", "Tirana");
		paisos[1] = new Pais("Bòsnia i Hercegovina", "BIH", "Sarajevo");
		paisos[2] = new Pais("Croàcia", "HRV", "Zagreb");
		paisos[3] = new Pais("Montenegro", "MNE", "Podgorica");
		paisos[4] = new Pais("Sèrbia", "SRB", "Belgrad");
		paisos[0].setPoblacio(3582205);
		paisos[1].setPoblacio(4498976);
		paisos[2].setPoblacio(4800000);
		paisos[3].setPoblacio(630548);
		paisos[4].setPoblacio(8196411);

		StringBuilder b = null;
		try (RandomAccessFile fitxer = new RandomAccessFile("paisos.dat", "rw");) {
			for (int i = 0; i < paisos.length; i++) {
				b = new StringBuilder(paisos[i].getNom());
				b.setLength(40); // Asigna mida de 40 caracters al contingut de StringBuilder
				fitxer.writeInt(i + 1); // id ------> int (4 bytes)
				fitxer.writeChars(b.toString()); // nom ------> char (2 bytes) * 40 caràcters
				fitxer.writeChars(paisos[i].getCodiISO()); // Codi ISO -> char (2 bytes) * 3 caràcters
				b = new StringBuilder(paisos[i].getCapital());
				b.setLength(40);
				fitxer.writeChars(b.toString()); // Capital --> char (2 bytes) * 40 caràcters
				fitxer.writeInt(paisos[i].getPoblacio()); // població -> int (4 bytes)
				// total per país: 174 bytes
			} // Total: 174 bytes * 5 països = 870 bytes
		} catch (IOException e) {
			System.err.println(e);
		}

		String nom, iso = "", capital;
		int id, poblacio;
		long pos = 0;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introdueix número de registre: ");
		id = scanner.nextInt();
		scanner.nextLine();
		try (RandomAccessFile fitxer = new RandomAccessFile("paisos.dat", "rw");) {
			// per accedir a un registre multipliquem per la mida de
			// cada registre.
			pos = (id - 1) * 174;

			if (pos < 0 || pos >= fitxer.length())
				throw new IOException("Número de registre invàlid.");

			fitxer.seek(pos);
			fitxer.readInt(); // Saltem l'id

			System.out.println("Introdueix el nou nom: ");
			nom = scanner.nextLine();
			b = new StringBuilder(nom);
			b.setLength(40);
			fitxer.writeChars(b.toString());

			boolean ok = false;
			while (!ok) {
				System.out.println("Introdueix el nou codi ISO: ");
				iso = scanner.nextLine();
				if (iso.length() == 3) {
					for (int i = 0; i < iso.length(); i++) {
						fitxer.writeChar(iso.charAt(i));
					}
					ok = true;
				}
			}

			System.out.println("Introdueix la nova capital: ");
			capital = scanner.nextLine();
			b = new StringBuilder(capital);
			b.setLength(40);
			fitxer.writeChars(b.toString());

			System.out.println("Introdueix la nova població: ");
			poblacio = scanner.nextInt();
			fitxer.writeInt(poblacio);
			scanner.nextLine();
			if (poblacio >= 0) {
				pos = fitxer.getFilePointer() - 4; // tornem enrere per sobreescriure la població
				fitxer.seek(pos);
				fitxer.writeInt(poblacio);
			} else {
				System.err.println("La població ha de ser positiva.");
			}
			System.out.println("País: " + nom + ", Codi ISO: " + iso + 
								", Capital: " + capital + ", Poblacio: " + poblacio);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		scanner.close();
	}

	

	private static String readChars(RandomAccessFile fitxer, int nChars) throws IOException {
		StringBuilder b = new StringBuilder();
		char ch = ' ';
		for (int i = 0; i < nChars; i++) {
			ch = fitxer.readChar();
			if (ch != '\0')
				b.append(ch);
		}
		return b.toString();
	}

}

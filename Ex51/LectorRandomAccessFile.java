import java.io.IOException;
import java.io.RandomAccessFile;

public class LectorRandomAccessFile {

	private static String readChars(RandomAccessFile fitxer, int nChars) throws IOException {
		StringBuilder b = new StringBuilder();
		char ch = ' ';
		for (int i=0; i<nChars; i++) {
			ch=fitxer.readChar();
			if (ch != '\0')
				b.append(ch);
		}
		return b.toString();
	}

	public static void main(String[] args) {
		Pais p;
		String nom, capital, codiISO;
		int poblacio;
		try (RandomAccessFile fitxer = new RandomAccessFile("paisos.dat", "r");) {
			while (fitxer.getFilePointer() != fitxer.length()) {
				System.out.println("País: "+fitxer.readInt());
				nom = readChars(fitxer, 40);
				codiISO = readChars(fitxer, 3);
				capital = readChars(fitxer, 40);
				poblacio = fitxer.readInt();
				p = new Pais(nom, codiISO, capital);
				p.setPoblacio(poblacio);
				System.out.println(p);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}

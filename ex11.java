import java.io.File;

public class ex11 {
    public static void main(String[] args) {
//CANVI
        String ruta = args[0];
        File path = new File(ruta);

        File[] files = path.listFiles();

        for (File file : files) {
            StringBuilder permisos = new StringBuilder();

            if (file.isDirectory()) {
                permisos.append('d');
            } else if (file.isFile()) {
                permisos.append('-');
            } else {
                // Entradas no clasificables como archivo/directorio
                permisos.append('?');
            }

            if (file.canRead()) {
                permisos.append('r');
            } else {
                permisos.append('-');
            }

            if (file.canWrite()) {
                permisos.append('w');
            } else {
                permisos.append('-');
            }

            if (file.canExecute()) {
                permisos.append('x');
            } else {
                permisos.append('-');
            }

            System.out.println(permisos + " " + file.getName());
        }
    }
}

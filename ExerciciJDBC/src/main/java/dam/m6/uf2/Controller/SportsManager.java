package dam.m6.uf2.Controller;

import java.sql.Connection;
import java.util.List;

import dam.m6.uf2.Model.ConnectionManager;
import dam.m6.uf2.Model.Esport;
import dam.m6.uf2.Model.EsportDAO;
import dam.m6.uf2.View.MainView;

public class SportsManager  {
    private Connection conn;

    public static void main(String[] args) {
        System.out.println("Current directory (aquí debes poner el XML): " + System.getProperty("user.dir"));
        SportsManager SportsManager = new SportsManager();
        SportsManager.init();
    }

    private void init() {
        final int AF_ESPORT = 1, AF_ATLETA = 2, CERCAR_AT = 3, LLISTAR_AT = 4, SORTIR = 5;

        // Conecció a la base de dades
        this.conn = ConnectionManager.getConnection("database.xml"); // Ajusta el nombre si es distinto

        // Crear la vista
        MainView mainView = new MainView();

        // 3️⃣ Crear el DAO
        // UserPgDAO userDAO = new UserPgDAO(conn);

        EsportDAO esportDAO = new EsportDAO(conn);   
    
        boolean sortir = false;
        while (!sortir) {
            // Mostra el menú principal
            int opcio = mainView.mainMenu();
            switch (opcio) {
                case AF_ESPORT:
                    String nameEsport = mainView.sportForm();
                    mainView.printSeparador();
                    Esport newEsport = new Esport(0, nameEsport);
                    esportDAO.add(newEsport);
                    break;

                case AF_ATLETA:

                    break;

                case CERCAR_AT:

                    break;

                case LLISTAR_AT:
                    
                    List<Esport> esports = esportDAO.getAll();
                    mainView.llistaEsports(esports);

                    break;

                case SORTIR:
                    sortir = true;
                    break;
            }
        }

    }
}

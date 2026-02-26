package com.activitat2.Controller;

import java.util.List;

import org.bson.Document;

import com.activitat2.Model.Model;
import com.activitat2.Model.Tasca;
import com.activitat2.View.View;

public class Controller {
    public static void main(String[] args) {

        final int AFEGIR = 1, ELIMINAR = 2, MODIFICAR = 3, LLISTAR_TOTES = 4, LLISTAR_DATES = 5, CERCAR_NOM = 6,
                SORTIR = 7;

        
        Model tasquesDB = new Model();

        boolean sortir = false;
        int opcio;

       
        while (!sortir) {
            View.mostrarMenu();
            opcio = View.llegirOpcio();

            switch (opcio) {
                case AFEGIR:
                    View.separador();
                    Tasca novaTasca = View.llegirNovaTasca();
                    tasquesDB.inserirTasca(novaTasca);
                    View.separador();
                    System.out.println("Tasca afegida correctament.");
                    View.separador();
                    break;

                case ELIMINAR:
                    View.separador();
                    List<Document> tasquesPerEliminar = tasquesDB.getAllTasca();
                    View.mostrarTasquesAmbId(tasquesPerEliminar);

                    String idEliminar = View.llegirString("Introdueix l'ID de la tasca a eliminar: ");
                    tasquesDB.eliminarTascaPerId(idEliminar);
                    View.separador();
                    System.out.println("Tasca eliminada correctament.");
                    View.separador();
                    break;

                case MODIFICAR:
                    View.separador();
                    List<Document> totesTasques = tasquesDB.getAllTasca();
                    View.mostrarTasquesAmbId(totesTasques); 
                    String idModificar = View.llegirString("Introdueix l'ID de la tasca a modificar: ");
                    Document nousValors = View.llegirDadesPerModificar();
                    tasquesDB.modificarTasca(idModificar, nousValors);
                    View.separador();
                    System.out.println("Tasca modificada correctament.");
                    View.separador();
                    break;

                case LLISTAR_TOTES:
                    View.separador();
                    List<Document> tasques = tasquesDB.getAllTasca();
                    View.mostrarTotesTasques(tasques);
                    break;

                case LLISTAR_DATES:
                    View.separador();
                    String dataInici = View.llegirData("Introdueix data inici (YYYY-MM-DD): ");
                    String dataFi = View.llegirData("Introdueix data fi (YYYY-MM-DD): ");
                    List<Document> tasquesEntreDates = tasquesDB.getTascaByDates(dataInici, dataFi);
                    View.mostrarTotesTasques(tasquesEntreDates);
                    View.separador();
                    break;

                case CERCAR_NOM:
                    View.separador();
                    String nomCerca = View.llegirString("Introdueix el nom de l'alumne a cercar: ");
                    List<Document> tasquesFiltrades = tasquesDB.getFilteredTasca(nomCerca);
                    if (tasquesFiltrades.isEmpty()) {
                        System.out.println("No s'han trobat tasques amb aquest nom.");
                    } else {
                        View.mostrarTotesTasques(tasquesFiltrades);
                    }
                    View.separador();
                    break;

                case SORTIR:
                    sortir = true;
                    break;
            }
        }

        System.out.println("Aplicació finalitzada.");
    }
}
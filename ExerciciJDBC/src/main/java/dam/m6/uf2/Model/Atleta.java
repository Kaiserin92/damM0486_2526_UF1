package dam.m6.uf2.Model;

public class Atleta {
    int cod;
    String nom;
    int codEsport;
    String nomEsport;

    public Atleta(int cod, String nom, int codEsport) {
        this.cod = cod;
        this.nom = nom;
        this.codEsport = codEsport;
    }

    public Atleta(int cod, String nom, String nomEsport) {
        this.cod = cod;
        this.nom = nom;
        this.nomEsport = nomEsport;
    }

    public String toString() {
        if(nomEsport != null) {
            return "Cod: " + cod + " - Nom: " + nom + " - Esport: " + nomEsport;
        }

        return "Cod: " + cod + " - Nom: " + nom + " - Esport: " + codEsport;
    }

    public String getNom() {
        return nom;
    }

}

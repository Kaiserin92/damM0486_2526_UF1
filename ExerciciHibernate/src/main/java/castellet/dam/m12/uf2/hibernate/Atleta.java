package castellet.dam.m12.uf2.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "deportistas")
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    int cod;

    @Column(name = "nombre")
    String nom;

    @ManyToOne
    @JoinColumn(name = "cod_deporte")
    private Esport esport;

    // String nomEsport; // Sigue haciendo falta???

    public Atleta() { // este constructor ok? consultar

    }

    public Atleta(int cod, String nom, Esport esport) {
        this.cod = cod;
        this.nom = nom;
        this.esport = esport;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Esport getEsport() {
        return esport;
    }

    public void setEsport(Esport esport) {
        this.esport = esport;
    }

    public String toString() {
        String nomEsport = (esport != null ? esport.getNom() : "N/A");
        return "Cod: " + cod + " - Nom: " + nom + " - Esport: " + nomEsport;
    }
}

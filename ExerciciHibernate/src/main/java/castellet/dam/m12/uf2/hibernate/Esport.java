package castellet.dam.m12.uf2.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "deportes")
public class Esport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    int cod;

    @Column(name = "nombre")
    String nom = "";

    public Esport() {}

    public Esport(int cod, String nom) {
        this.cod = cod;
        this.nom = nom;
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

    public String toString() {
        return "Cod: " + cod + " - Nom: " + nom;
    }
}

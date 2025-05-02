package model;

public class Liste {
    private int idListe;
    private String nom;
    private int createurId;

    public Liste(int idListe, String nom, int createurId) {
        this.idListe = idListe;
        this.nom = nom;
    }

    public int getIdListe() { return idListe; }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getCreateurId() {
        return createurId;
    }

    public void setCreateurId(int createurId) {
        this.createurId = createurId;
    }
    @Override
    public String toString() {
        return this.nom;
    }

}
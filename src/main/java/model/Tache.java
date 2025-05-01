package model;

public class Tache {
    private int id;
    private String nom;
    private int etat; // 0=à faire, 1=en cours, 2=terminé
    private int listeId;
    private int typeId;

    // Constructeurs
    public Tache(int id, String nom, int etat, int listeId, int typeId) {
        this.id = id;
        this.nom = nom;
        this.etat = etat;
        this.listeId = listeId;
        this.typeId = typeId;
    }

    // Getters & Setters
    public String getEtatString() {
        return switch(etat) {
            case 0 -> "À faire";
            case 1 -> "En cours";
            case 2 -> "Terminé";
            default -> "Inconnu";
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getListeId() {
        return listeId;
    }

    public void setListeId(int listeId) {
        this.listeId = listeId;
    }
}
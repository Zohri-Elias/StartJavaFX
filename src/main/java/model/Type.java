package model;

public class Type {
    private int id;
    private String nom;
    private String codeCouleur;
    private boolean adminOnly;

    public Type(int id, String nom, String codeCouleur, boolean adminOnly) {
        this.id = id;
        this.nom = nom;
        this.codeCouleur = codeCouleur;
        this.adminOnly = adminOnly;
    }

    // Getters
    public String getCouleurStyle() {
        return String.format("-fx-background-color: %s; -fx-text-fill: white;", codeCouleur);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }

    public void setCodeCouleur(String codeCouleur) {
        this.codeCouleur = codeCouleur;
    }

    public boolean isAdminOnly() {
        return adminOnly;
    }

    public void setAdminOnly(boolean adminOnly) {
        this.adminOnly = adminOnly;
    }
}
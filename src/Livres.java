import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Livres {
    private String titre;
    private String auteur;
    private int anneePublication;
    private String isbn;
    private boolean disponible = true;
    private LocalDate dateEmprunt = null;

    public Livres(String titre, String auteur, int anneePublication, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    @Override
    public String toString() {
        return "Livres{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", anneePublication=" + anneePublication +
                ", isbn='" + isbn + '\'' +
                ", disponible=" + disponible +
                ", dateEmprunt=" + dateEmprunt +
                '}';
    }

    /**
     * Cette fonction permet à l'utilisateur d'ajouter un nouveau livre à une liste de livres.
     *
     * @param listLivre La liste de livres à laquelle ajouter un nouveau livre.
     * @return La liste de livres mise à jour après l'ajout du nouveau livre.
     */
    public static ArrayList ajout(ArrayList<Livres> listLivre){
        Scanner input = new Scanner(System.in);
        String titre, auteur, isbn;
        int anneePublication;
        System.out.println("veuillez entrer le titre du livre");
        titre = input.nextLine();
        System.out.println("Veuillez entrer l'auteur");
        auteur = input.nextLine();
        System.out.println("veuillez entrer l'année de publication");
        anneePublication = Integer.parseInt(input.nextLine());
        System.out.println("veuillez entrer l'ISBN");
        isbn = input.nextLine();
        listLivre.add(new Livres(titre,auteur,anneePublication,isbn));
        return listLivre;
    }

    /**
     * Cette fonction permet à l'utilisateur de choisir un livre à supprimer de la liste des livres.
     *
     * @param listLivre La liste des livres à partir de laquelle supprimer un livre.
     * @return La liste des livres mise à jour après la suppression.
     */
    public static ArrayList suppression(ArrayList<Livres> listLivre){
        int choix;
        System.out.println("Quelle livre souhaitez vous supprimer?");
        choix = Integer.parseInt(Affichable.affichageLivres(listLivre))-1;
        listLivre.remove(choix);
        return listLivre;
    }

    /**
     * Cette fonction permet à l'utilisateur de modifier les informations d'un livre existant dans la liste des livres.
     *
     * @param listLivre La liste des livres à partir de laquelle effectuer les modifications.
     * @return La liste des livres mise à jour après les modifications.
     */
    public static ArrayList modification(ArrayList<Livres> listLivre){
        int choixLivre;
        Scanner input = new Scanner(System.in);
        String choix, entree;
        int entreeAnnee;
        boolean encore = false;
        Livres livre;
        System.out.println("Quel livre souhaitez vous modifier");
        choixLivre = Integer.parseInt(Affichable.affichageLivres(listLivre))-1;
        do {
            System.out.println("Quelle information souhaitez vous modifier?");
            System.out.println("1) Titre\n2) Auteur\n3) Annee de publication\n4) ISBN");
            choix = input.nextLine();
            switch (choix){
                case "1" -> {
                    System.out.println("Veuillez entrer le nouveau titre");
                    entree = input.nextLine();
                    listLivre.get(choixLivre).setTitre(entree);
                }
                case "2" -> {
                    System.out.println("Veuillez entrer le nouvel auteur");
                    entree = input.nextLine();
                    listLivre.get(choixLivre).setAuteur(entree);
                }
                case "3" -> {
                    System.out.println("Veuillez entrer la nouvelle année de parution");
                    entreeAnnee = Integer.parseInt(input.nextLine());
                    listLivre.get(choixLivre).setAnneePublication(entreeAnnee);
                }
                case "4" -> {
                    System.out.println("Veuillez entrer le nouvel ISBN");
                    entree = input.nextLine();
                    listLivre.get(choixLivre).setIsbn(entree);
                }
                default -> throw new YannException();
            }
            System.out.println("Souhaitez vous faire une autre modification sur ce livre? (o)ui/(n)on)");
            entree = input.nextLine();
            if (entree.equals("o")) encore = true;
            else encore = false;
        } while (encore);
        return listLivre;
    }

    /**
     * Cette fonction permet à l'utilisateur d'emprunter un livre de la liste des livres.
     *
     * @param listLivre La liste des livres à partir de laquelle effectuer l'emprunt.
     * @return La liste des livres mise à jour après l'emprunt.
     */
    public static ArrayList emprunt(ArrayList<Livres> listLivre){
        int choix;
        System.out.println("Quel livre souhaitez vous emprunter?");
        choix = Integer.parseInt(Affichable.affichageLivres(listLivre))-1;
        listLivre.get(choix).setDisponible(false);
        listLivre.get(choix).setDateEmprunt(LocalDate.now());
        return listLivre;
    }
}

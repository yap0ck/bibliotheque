import lombok.Builder;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

@Data @Builder
public class Livres {
    private String titre;
    private String auteur;
    private int anneePublication;
    private String isbn;
    private int livreId;
    private Emprunt emprunt;

    public Livres(String titre, String auteur, int anneePublication, String isbn, int livreId, Emprunt emprunt) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.isbn = isbn;
        this.livreId = livreId;
        this.emprunt = emprunt;
    }

    public Livres(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur;
    }

    public Livres() {
    }

    public Livres(String titre, String auteur, int anneePublication, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.isbn = isbn;
    }

    /**
     * Cette fonction permet à l'utilisateur d'ajouter un nouveau livre à une liste de livres.
     *
     * @param listLivre La liste de livres à laquelle ajouter un nouveau livre.
     * @return La liste de livres mise à jour après l'ajout du nouveau livre.
     */
    public static void ajout(){
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
        Requete.insertBook(titre,auteur,anneePublication,isbn);
    }

    /**
     * Cette fonction permet à l'utilisateur de choisir un livre à supprimer de la liste des livres.
     *
     * @param listLivre La liste des livres à partir de laquelle supprimer un livre.
     * @return La liste des livres mise à jour après la suppression.
     */
    public static ArrayList suppression(){
        ArrayList<Livres> listLivre = Requete.listingLivres();
        int choix;
        System.out.println("Quelle livre souhaitez vous supprimer?");
        choix = Integer.parseInt(Affichable.affichageLivres())-1;
        Livres livres = listLivre.get(choix);
        Requete.suppressionLivresDB(livres);
        listLivre.remove(choix);
        return listLivre;
    }

    /**
     * Cette fonction permet à l'utilisateur de modifier les informations d'un livre existant dans la liste des livres.
     *
     * @param listLivre La liste des livres à partir de laquelle effectuer les modifications.
     * @return La liste des livres mise à jour après les modifications.
     */
    public static ArrayList modification(){
        ArrayList<Livres> listLivre = Requete.listingLivres();
        int choixLivre;
        Scanner input = new Scanner(System.in);
        String choix, entree = "";
        int entreeAnnee;
        boolean encore = false;
        Livres livre;
        System.out.println("Quel livre souhaitez vous modifier");
        choixLivre = Integer.parseInt(Affichable.affichageLivres())-1;
        livre = listLivre.get(choixLivre);
        do {
            System.out.println("Quelle information souhaitez vous modifier?");
            System.out.println("1) Titre\n2) Auteur\n3) Annee de publication\n4) ISBN");
            choix = input.nextLine();
            switch (choix){
                case "1" -> {
                    System.out.println("Veuillez entrer le nouveau titre");
                    entree = input.nextLine();
                    livre.setTitre(entree);
                }
                case "2" -> {
                    System.out.println("Veuillez entrer le nouvel auteur");
                    entree = input.nextLine();
                    livre.setAuteur(entree);
                }
                case "3" -> {
                    System.out.println("Veuillez entrer la nouvelle année de parution");
                    entreeAnnee = Integer.parseInt(input.nextLine());
                    livre.setAnneePublication(entreeAnnee);
                }
                case "4" -> {
                    System.out.println("Veuillez entrer le nouvel ISBN");
                    entree = input.nextLine();
                    livre.setIsbn(entree);
                }
                default -> throw new YannException();
            }
            Requete.modifLivre(choix, entree, livre);
            System.out.println("Souhaitez vous faire une autre modification sur ce livre? (o)ui/(n)on)");
            entree = input.nextLine();
            if (entree.equals("o")) encore = true;
            else encore = false;
        } while (encore);
        return listLivre;
    }

    static void recherche(){
        Livres livre = new Livres();
        Scanner input = new Scanner(System.in);
        String entree;
        System.out.println("Veuillez entrer l'ISBN' du livre que vous recherchez");
        entree = input.nextLine();
        livre = Requete.rechercheLivreDB(entree);
        System.out.println(livre);
    }
}

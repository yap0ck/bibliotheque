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
     * Cette fonction permet à l'utilisateur d'ajouter un nouveau livre en saisissant ses informations.
     * Les informations du livre (titre, auteur, année de publication, ISBN) sont saisies par l'utilisateur
     * et ensuite insérées dans la base de données en utilisant la fonction Requete.insertBook.
     */
    public static void ajout() {
        Scanner input = new Scanner(System.in);
        String titre, auteur, isbn;
        int anneePublication;

        // Demande à l'utilisateur de saisir le titre du livre
        System.out.println("Veuillez entrer le titre du livre");
        titre = input.nextLine();

        // Demande à l'utilisateur de saisir l'auteur du livre
        System.out.println("Veuillez entrer l'auteur");
        auteur = input.nextLine();

        // Demande à l'utilisateur de saisir l'année de publication du livre
        System.out.println("Veuillez entrer l'année de publication");
        anneePublication = Integer.parseInt(input.nextLine());

        // Demande à l'utilisateur de saisir l'ISBN du livre
        System.out.println("Veuillez entrer l'ISBN");
        isbn = input.nextLine();

        // Appelle la fonction Requete.insertBook pour insérer les informations du livre dans la base de données
        Requete.insertBook(titre, auteur, anneePublication, isbn);
    }



    /**
     * Cette fonction permet à l'utilisateur de supprimer un livre de la base de données.
     * L'utilisateur choisit le livre à supprimer en sélectionnant son index parmi la liste des livres disponibles.
     * Une fois le livre choisi, la fonction supprime ce livre de la base de données en utilisant la fonction Requete.suppressionLivresDB.
     *
     * @return Une liste mise à jour des livres après la suppression.
     */
    public static ArrayList<Livres> suppression() {
        ArrayList<Livres> listLivre = Requete.listingLivres(); // Récupère la liste des livres depuis la base de données
        int choix;

        // Demande à l'utilisateur de choisir le livre à supprimer en sélectionnant son index
        System.out.println("Quelle livre souhaitez-vous supprimer?");
        choix = Integer.parseInt(Affichable.affichageLivres()) - 1; // Affiche la liste des livres disponibles et lit le choix de l'utilisateur

        // Récupère le livre sélectionné
        Livres livres = listLivre.get(choix);

        // Appelle la fonction Requete.suppressionLivresDB pour supprimer le livre de la base de données
        Requete.suppressionLivresDB(livres);

        // Supprime également le livre de la liste locale (mise à jour de la liste)
        listLivre.remove(choix);

        // Renvoie la liste mise à jour des livres après la suppression
        return listLivre;
    }


    /**
     * Cette fonction permet à l'utilisateur de modifier les informations d'un livre existant dans la base de données.
     * L'utilisateur choisit le livre à modifier en sélectionnant son index parmi la liste des livres disponibles.
     * Ensuite, l'utilisateur peut choisir quelle information du livre il souhaite modifier (titre, auteur, année de publication, ISBN),
     * et saisir la nouvelle valeur pour cette information.
     * Les modifications sont enregistrées dans la base de données en utilisant la fonction Requete.modifLivre.
     *
     * @return Une liste mise à jour des livres après les modifications.
     */
    public static ArrayList<Livres> modification() {
        ArrayList<Livres> listLivre = Requete.listingLivres();
        int choixLivre;
        Scanner input = new Scanner(System.in);
        String choix, entree = "";
        int entreeAnnee;
        boolean encore = false;
        Livres livre;

        // Demande à l'utilisateur de choisir le livre à modifier en sélectionnant son index
        System.out.println("Quel livre souhaitez-vous modifier?");
        choixLivre = Integer.parseInt(Affichable.affichageLivres()) - 1; // Affiche la liste des livres disponibles et lit le choix de l'utilisateur

        // Récupère le livre sélectionné
        livre = listLivre.get(choixLivre);

        do {
            // Affiche les options de modification disponibles
            System.out.println("Quelle information souhaitez-vous modifier?");
            System.out.println("1) Titre\n2) Auteur\n3) Année de publication\n4) ISBN");
            choix = input.nextLine();
            switch (choix) {
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
                default -> throw new YannException(); // En cas de choix invalide
            }

            // Appelle la fonction Requete.modifLivre pour enregistrer les modifications dans la base de données
            Requete.modifLivre(choix, entree, livre);

            // Demande à l'utilisateur s'il souhaite effectuer d'autres modifications sur ce livre
            System.out.println("Souhaitez-vous faire une autre modification sur ce livre? (o)ui/(n)on)");
            entree = input.nextLine();
            if (entree.equals("o")) encore = true;
            else encore = false;
        } while (encore);

        // Renvoie la liste mise à jour des livres après les modifications
        return listLivre;
    }


    /**
     * Cette fonction permet à l'utilisateur de rechercher un livre dans la base de données en utilisant son ISBN.
     * L'utilisateur saisit l'ISBN du livre qu'il recherche, puis la fonction appelle Requete.rechercheLivreDB pour obtenir
     * les informations du livre correspondant à cet ISBN. Les informations du livre sont ensuite affichées à l'utilisateur.
     */
    public static void recherche() {
        Livres livre = new Livres(); // Crée un objet Livres pour stocker les informations du livre trouvé
        Scanner input = new Scanner(System.in);
        String entree;

        // Demande à l'utilisateur de saisir l'ISBN du livre qu'il recherche
        System.out.println("Veuillez entrer l'ISBN du livre que vous recherchez");
        entree = input.nextLine();

        // Appelle la fonction Requete.rechercheLivreDB pour rechercher le livre par ISBN
        livre = Requete.rechercheLivreDB(entree);

        // Affiche les informations du livre trouvé
        System.out.println(livre);
    }

}

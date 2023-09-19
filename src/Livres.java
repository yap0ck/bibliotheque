import java.util.ArrayList;
import java.util.Scanner;

public class Livres {
    private String titre;
    private String auteur;
    private int anneePublication;
    private String isbn;

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

    @Override
    public String toString() {
        return "Livres{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", anneePublication=" + anneePublication +
                ", isbn='" + isbn + '\'' +
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

    public static ArrayList suppression(ArrayList<Livres> listLivre){
        int choix;
        System.out.println("Quelle livre souhaitez vous supprimer?");
        choix = Integer.parseInt(Affichable.affichageLivres(listLivre))-1;
        listLivre.remove(choix);
        return listLivre;
    }
}

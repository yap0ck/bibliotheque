import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Data @Builder
public class Emprunt {
    private int empruntId;
    private Livres livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private User user;

    public Emprunt(int empruntId, Livres livre, LocalDate dateEmprunt, LocalDate dateRetour, User user) {
        this.empruntId = empruntId;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.user = user;
    }

    public Emprunt() {
    }

    /**
     * Cette fonction permet à l'utilisateur d'emprunter un livre à partir de la liste des livres disponibles.
     *
     * @param user L'utilisateur qui souhaite emprunter un livre.
     */
    public static void emprunt(User user){
        //Obtient la liste des livres empruntés depuis une Requete SQL
        ArrayList<Livres> list = Requete.listingLivresEmprunte(user);
        Livres livre;
        int choixLivre;
        // Demande a l'utilisateur de choisir le livre à emprunter en utilisant la fonction Affichable.affichageLivres()
        System.out.println("Quel livre souhaitez vous emprunter?");
        choixLivre = Integer.parseInt(Affichable.affichageLivres())-1;
        //Récupere le livre séléctionné depuis la liste des livres empruntés par l'utilisateur
        livre = list.get(choixLivre);
        //crée un objet en utilisant le patter builder
        Emprunt emprunt = Emprunt.builder()
                                .dateEmprunt(LocalDate.now())
                                .livre(livre)
                                .user(user)
                                .build();
        // Insere l'emprunt dans la base de donnée en utilisant la fonction Requete.insertEmprunt()
        Requete.insertemprunt(emprunt);
    }

    /**
     * Cette fonction permet à l'utilisateur de retourner un livre emprunté.
     *
     * @param user L'utilisateur qui souhaite retourner un livre.
     */
    public static void retour(User user){
        // Obtient la liste des livres empruntés par l'utilisateur depuis Requete
        ArrayList<Livres> list = Requete.listingLivresEmprunte(user);
        Livres livre;
        int choixLivre;
        // Demande à l'utilisateur de choisir le livre à retourner en utilisant la fonction Affichable.affichageLivreEmprunte(user)
        System.out.println("quel livre souhaitez vous retourner?");
        choixLivre = Integer.parseInt(Affichable.affichageLivreEmprunte(user))-1;
        // Récupère le livre sélectionné depuis la liste des livres empruntés par l'utilisateur
        livre = list.get(choixLivre);
        // Obtient la date actuelle pour enregistrer la date de retour
        LocalDate dateRetour = LocalDate.now();
        // Utilise Requete.modifEmprunt pour mettre à jour la date de retour dans la base de données
        Requete.modifEmprunt(dateRetour, livre);
    }
}

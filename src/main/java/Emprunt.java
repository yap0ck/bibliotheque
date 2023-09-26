import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Data @Builder
public class Emprunt {
    private int empruntId;
    private Livres livre;
    private Date dateEmprunt;
    private Date dateRetour;
    private User user;

    public Emprunt(int empruntId, Livres livre, Date dateEmprunt, Date dateRetour, User user) {
        this.empruntId = empruntId;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.user = user;
    }

    public Emprunt() {
    }

    public static void emprunt(User user){
        ArrayList<Livres> list = Requete.listingLivresEmprunte(user);
        Livres livre;
        int choixLivre;
        System.out.println("Quel livre souhaitez vous emprunter?");
        choixLivre = Integer.parseInt(Affichable.affichageLivres())-1;
        livre = list.get(choixLivre);
        Emprunt emprunt = Emprunt.builder()
                                .dateEmprunt(Date.from(Instant.now()))
                                .livre(livre)
                                .user(user)
                                .build();
        Requete.insertemprunt(emprunt);
    }
    public static void retour(User user){
        ArrayList<Livres> list = Requete.listingLivresEmprunte(user);
        Livres livre;
        int choixLivre;
        System.out.println("quel livre souhaitez vous retourner?");
        choixLivre = Integer.parseInt(Affichable.affichageLivreEmprunte(user))-1;
        livre = list.get(choixLivre);
        Date dateRetour = Date.from(Instant.now());
        Requete.modifEmprunt(dateRetour, livre);
    }
}

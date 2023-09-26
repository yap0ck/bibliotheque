import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        // variable
        Boolean sortir= false, correct;
        //core
        if (Affichable.login()) {
            do {
                correct = true;
                switch (Affichable.menuAdmin()){
                    case "1" -> Livres.ajout();
                    case "2" -> Livres.recherche();
                    case "3" -> listLivres = Livres.suppression(listLivres);
                    case "4" -> listLivres = Livres.modification(listLivres);
                    case "5" -> listLivres = Livres.emprunt(listLivres);
                    case "6" -> listLivres = Livres.retour(listLivres);
                    case "7" -> Affichable.affichageLivre(listLivres);
                    case "8" -> sortir = true;
                    default -> correct = false;
                }

            } while (!correct || !sortir);
        } else {
            do {
                correct = true;
                switch (Affichable.menuClient()){
                    case "1" -> Livres.recherche();
                    case "2" -> listLivres = Livres.emprunt(listLivres);
                    case "3" -> listLivres = Livres.retour(listLivres);
                    case "4" -> Affichable.affichageLivre(listLivres);
                    case "5" -> sortir = true;
                    default -> correct = false;
                }
            } while (!correct || !sortir);
        }

    }
}

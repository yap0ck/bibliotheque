import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        // variable
        Boolean sortir= false, correct;
        User user = Affichable.login();
        //core
        if (user.getRole().equals("Administrator")) {
            do {
                correct = true;
                switch (Affichable.menuAdmin()){
                    case "1" -> Livres.ajout();
                    case "2" -> Livres.recherche();
                    case "3" -> Livres.suppression();
                    case "4" -> Livres.modification();
                    case "5" -> Emprunt.emprunt(user);
                    case "6" -> Emprunt.retour(user);
                    case "7" -> Affichable.affichageLivreDispo();
                    case "8" -> sortir = true;
                    default -> correct = false;
                }

            } while (!correct || !sortir);
        } else {
            do {
                correct = true;
                switch (Affichable.menuClient()){
                    case "1" -> Livres.recherche();
                    case "2" -> Emprunt.emprunt(user);
                    case "3" -> Emprunt.retour(user);
                    case "4" -> Affichable.affichageLivreDispo();
                    case "5" -> sortir = true;
                    default -> correct = false;
                }
            } while (!correct || !sortir);
        }

    }
}

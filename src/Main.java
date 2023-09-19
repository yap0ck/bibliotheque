import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        // génération objet testing
        ArrayList<User> listUser = new ArrayList<>();
        User testing = new Admin("john","johnny","admin","admin");
        listUser.add(testing);
        testing = new User("Valerie","Damidot","client","client");
        listUser.add(testing);
        System.out.println(testing.toString());
        // variable
        Boolean sortir= false, correct;

        //core
        if (Affichable.login(listUser)) {
            do {
                correct = true;
                switch (Affichable.menuAdmin()){
                    //case "1" -> ajout();
                    //case "2" -> information();
                    //case "3" -> suppression();
                    //case "4" -> modification();
                    //case "5" -> emprunt();
                    //case "6" -> retour();
                    //case "7" -> affichageLivre();
                    case "8" -> sortir = true;
                    default -> correct = false;
                }
            } while (!correct || !sortir);
        } else {
            do {
                correct = true;
                switch (Affichable.menuClient()){
                    //case "1" -> information();
                    //case "2" -> emprunt();
                    //case "3" -> retour();
                    //case "4" -> affichageLivre();
                    case "5" -> sortir = true;
                    default -> correct = false;
                }
            } while (!correct || !sortir);
        }

    }
}

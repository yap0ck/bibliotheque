import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        // génération objet testing
        ArrayList<User> listUser = new ArrayList<>();
        User testing = new Admin("john","johnny","admin","admin");
        listUser.add(testing);
        testing = new User("Valerie","Damidot","client","client");
        listUser.add(testing);

        ArrayList<Livres> listLivres = new ArrayList<>();
        listLivres.add(new Livres("Harry Potter et la pierre philosophale", "J.K. Rowling", 1997, "978-1-4028-9462-6"));
        listLivres.add(new Livres("Le Seigneur des Anneaux : La Communauté de l'Anneau", "J.R.R. Tolkien", 1954, "978-0-7382-1553-1"));
        listLivres.add(new Livres("1984", "George Orwell", 1949, "978-1-84653-024-2"));
        listLivres.add(new Livres("Le Petit Prince", "Antoine de Saint-Exupéry", 1943, "978-0-7645-9653-1"));
        // variable
        Boolean sortir= false, correct;
        //core
        if (Affichable.login(listUser)) {
            do {
                correct = true;
                switch (Affichable.menuAdmin()){
                    case "1" -> listLivres = Livres.ajout(listLivres);
                    case "2" -> Affichable.recherche(listLivres);
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
                    case "1" -> Affichable.recherche(listLivres);
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

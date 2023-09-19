import java.util.ArrayList;
import java.util.Scanner;

public interface Affichable {

    /**
     * Cette fonction permet à l'utilisateur de se connecter en demandant un nom d'utilisateur et un mot de passe.
     * Elle parcourt une liste d'utilisateurs pour vérifier l'authentification.
     * Si l'authentification réussit, la fonction renvoie true si l'utilisateur est un administrateur, sinon false.
     *
     * @param listUser La liste des utilisateurs enregistrés.
     * @return true si l'utilisateur est un administrateur, sinon false.
     */
    static Boolean login(ArrayList<User> listUser){
        Scanner input = new Scanner(System.in);
        String loginEntree, mdpEntree;
        System.out.println("Bonjour");
        boolean authCorrect = false;
        boolean isAdmin = false;
        do {
            System.out.println("Veuillez entrer votre nom d'utilisateur");
            loginEntree = input.nextLine();
            System.out.println("Veuillez entrer votre mot de passe");
            mdpEntree = input.nextLine();
            for (User user : listUser) {
                if (user.getLogin().equals(loginEntree) && user.getMotdepasse().equals(mdpEntree)) {
                    authCorrect = true;
                    System.out.println("identification réussie");
                    if (user.getRole().equals("admin")) {
                        return isAdmin = true;
                    } else {
                        return isAdmin = false;
                    }
                }
            }
            System.out.println("identifiants incorrect");
        } while (!authCorrect);
        return isAdmin;
    }

    /**
     * Cette fonction affiche un menu de choix pour l'utilisateur client et récupère son choix.
     *
     * @return Le choix de l'utilisateur sous forme de chaîne de caractères.
     */
    static String menuClient(){
        String choix;
        Scanner input = new Scanner(System.in);
        System.out.println("Quelle opération souhaitez vous effectuer?");
        System.out.println("1) Afficher les informations d'un livre");
        System.out.println("2) Emprunter un livre");
        System.out.println("3) Retourner un livre");
        System.out.println("4) Afficher les livres disponibles");
        System.out.println("5) Sortir");
        choix = input.nextLine();
        return choix;
    }

    /**
     * Cette fonction affiche un menu de choix pour l'administrateur et récupère son choix.
     *
     * @return Le choix de l'administrateur sous forme de chaîne de caractères.
     */
    static String menuAdmin(){
        String choix;
        Scanner input = new Scanner(System.in);
        System.out.println("Quelle opération souhaitez vous effectuer?");
        System.out.println("1) Ajouter un livre");
        System.out.println("2) Afficher les informations d'un livre");
        System.out.println("3) Supprimer un livre");
        System.out.println("4) Modifier les information d'un livre");
        System.out.println("5) Emprunter un livre");
        System.out.println("6) Retourner un livre");
        System.out.println("7) Afficher les livres disponibles");
        System.out.println("8) Sortir");
        choix = input.nextLine();
        return choix;
    }

    /**
     * Cette fonction affiche la liste des livres et permet à l'utilisateur de sélectionner un livre ou de retourner en arrière.
     * Elle renvoie le choix de l'utilisateur sous forme de chaîne de caractères.
     *
     * @param list La liste des livres à afficher.
     * @return Le choix de l'utilisateur sous forme de chaîne de caractères.
     */
    static String affichageLivres(ArrayList<Livres> list){
        int index = 0;
        String choix;
        Scanner input = new Scanner(System.in);
        for (Livres livres : list) {
            index ++;
            System.out.print(index + ") ");
            System.out.println(livres.toString());
        }
        System.out.println(index++ + ") Retour");
        choix = input.nextLine();
        return choix;
    }

    /**
     * Cette fonction affiche la liste des livres disponibles.
     *
     * @param list La liste des livres à afficher.
     */
    static void affichageLivre(ArrayList<Livres> list){
        int index = 0;
        for (Livres livres : list) {
            if (livres.isDisponible()) {
                index++;
                System.out.print(index + ") ");
                System.out.println(livres.toString());
            }
        }
    }

    /**
     * Cette fonction permet à l'utilisateur de rechercher un livre par son titre dans la liste des livres
     * et affiche les informations du livre s'il est trouvé.
     *
     * @param list La liste des livres dans laquelle effectuer la recherche.
     */
    static void recherche(ArrayList<Livres> list){
        Scanner input = new Scanner(System.in);
        String entree;
        System.out.println("Veuillez entrer le titre du livre que vous recherchez");
        entree = input.nextLine();
        for (Livres livres : list) {
            if (livres.getTitre().equals(entree)) System.out.println(livres.toString());
        }
    }
}

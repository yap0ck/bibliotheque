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
    static User login(){
        Scanner input = new Scanner(System.in);
        String loginEntree, mdpEntree;
        System.out.println("Bonjour");
        boolean authCorrect = false;
        boolean isAdmin = false;
        User user;
        do {
            System.out.println("Veuillez entrer votre nom d'utilisateur");
            loginEntree = input.nextLine();
            System.out.println("Veuillez entrer votre mot de passe");
            mdpEntree = input.nextLine();
            user = Requete.loginUser(loginEntree,mdpEntree);
            if (user == null){
                System.out.println("identifiants incorrect");
                authCorrect = false;
            } else if (user.getRole().equals("Administrator")) {
                System.out.println("chargement menu admin");
                authCorrect = true;
            } else {
                System.out.println("chargement menu utilisateur");
                authCorrect=true;
            }
        } while (!authCorrect);
        return user;
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
    static String affichageLivres(){
        ArrayList<Livres> list = Requete.listingLivres();
        int index = 0;
        String choix;
        Scanner input = new Scanner(System.in);
        for (Livres livres : list) {
            index ++;
            System.out.print(index + ") ");
            System.out.println(livres.toString());
        }
        index++;
        System.out.println( index + ") Retour");
        choix = input.nextLine();
        return choix;
    }

    /**
     * Cette fonction affiche la liste des livres disponibles.
     *
     * @param list La liste des livres à afficher.
     */
    static void affichageLivreDispo(){
        ArrayList<Livres> list = Requete.listingLivresDispo();
        int index = 0;
        for (Livres livres : list) {
            index++;
            System.out.print(index + ") ");
            System.out.println(livres);
        }
    }

    static String affichageLivreEmprunte(User user){
        ArrayList<Livres> list = Requete.listingLivresEmprunte(user);
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

}

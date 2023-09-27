import java.util.ArrayList;
import java.util.Scanner;

public interface Affichable {

    /**
     * Cette fonction gère le processus de connexion de l'utilisateur.
     *
     * @return L'utilisateur connecté.
     */
    static User login(){
        Scanner input = new Scanner(System.in);
        String loginEntree, mdpEntree;
        System.out.println("Bonjour");
        boolean authCorrect = false; // Indicateur ppour verifier l'authentification
        boolean isAdmin = false; // Indicateur pour vérifier si l'utilisateur est un administrateur
        User user; // stockage de l'utilisateur connecté
        do {
            //Demande à l'utilisateur de saisir son nom d'utilisateur
            System.out.println("Veuillez entrer votre nom d'utilisateur");
            loginEntree = input.nextLine();
            // Demande à l'utilisateur de saisir son mot de passe
            System.out.println("Veuillez entrer votre mot de passe");
            mdpEntree = input.nextLine();
            //Appelle la fonction Requete.loginUser pour vérifier les identifiants
            user = Requete.loginUser(loginEntree,mdpEntree);
            if (user == null){
                // Si les identifiants sont incorrects, affiche un message d'erreur
                System.out.println("identifiants incorrect");
                authCorrect = false;
            } else if (user.getRole().equals("Administrator")) {
                // Si l'utilisateur est un admin, charge le menu Admin
                System.out.println("chargement menu admin");
                authCorrect = true;
            } else {
                // Sinon charge le menu client
                System.out.println("chargement menu utilisateur");
                authCorrect=true;
            }
        } while (!authCorrect); // répete tant que l'authentification n'est pas reussie
        // Renvoie l'utilisateur connecté
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
     * Cette fonction affiche la liste des livres disponibles et permet à l'utilisateur de sélectionner un livre ou de retourner en arrière.
     *
     * @return Le choix de l'utilisateur sous forme de chaîne de caractères.
     */
    static String affichageLivres(){
        ArrayList<Livres> list = Requete.listingLivres();
        int index = 0; // variable pour numeroter les livres
        String choix; // variable pour stocker le choix de l'utilisateur
        Scanner input = new Scanner(System.in);
        // Parcours de la liste des livres pour les afficher avec des numéros
        for (Livres livres : list) {
            index ++;
            System.out.print(index + ") ");
            System.out.println(livres.toString());
        }
        //Ajout d'une option de retour en arriére dans le menu
        index++;
        System.out.println( index + ") Retour");
        // Demande à l'utilisateur de choisir une option
        choix = input.nextLine();
        // Renvoie le choix de l'utilisateur
        return choix;
    }

    /**
     * Cette fonction affiche la liste des livres disponibles.
     */
    static void affichageLivreDispo(){
        ArrayList<Livres> list = Requete.listingLivresDispo(); //Obtient la liste des livres disponible via une requete SQL
        int index = 0; //Variable pour numeroter les livres
        for (Livres livres : list) {
            index++;
            System.out.print(index + ") ");
            System.out.println(livres);
        }
    }

    /**
     * Cette fonction affiche la liste des livres empruntés par l'utilisateur et permet à l'utilisateur de sélectionner un livre ou de retourner en arrière.
     *
     * @param user L'utilisateur dont les livres empruntés doivent être affichés.
     * @return Le choix de l'utilisateur sous forme de chaîne de caractères.
     */
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

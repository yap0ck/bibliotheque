import java.util.ArrayList;
import java.util.Scanner;

public interface Affichable {
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
                    if (user.getRole().equals("admin")) isAdmin = true;
                    break;
                }
            }
            System.out.println("identifiants incorrect");
        } while (!authCorrect);
        System.out.println("identification réussie");
        return isAdmin;
    }

}

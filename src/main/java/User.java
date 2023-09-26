import lombok.Data;

import java.util.Scanner;
@Data
public class User {
    private String nom, prenom, login, motdepasse, role = "client";

    public User(String nom, String prenom, String login, String motdepasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.motdepasse = motdepasse;
    }

    public User() {

    }
}

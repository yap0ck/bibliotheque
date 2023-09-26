import lombok.Data;
@Data
public class Admin extends User{

    public Admin(String nom, String prenom, String login, String motdepasse) {
        super(nom, prenom, login, motdepasse);
        this.setRole("admin");
    }
}



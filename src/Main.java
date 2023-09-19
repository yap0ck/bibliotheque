import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<User> listUser = new ArrayList<>();
        User testing = new Admin("john","johnny","admin","admin");
        listUser.add(testing);
        testing = new User("Valerie","Damidot","vadamidot","d&co");
        listUser.add(testing);
        Affichable.login(listUser);
    }
}

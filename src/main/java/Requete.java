import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public interface Requete {
    static boolean insertBook(String name, String author, int publishingYear, String isbn){
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO ? (name,author,publishingyear,isbn) VALUES (?, ?, ?, ?)");
                ){
            statement.setString(1,name);
            statement.setString(2,author);
            statement.setInt(3,publishingYear);
            statement.setString(4,isbn);
            return statement.execute();
        } catch (SQLException e){
            throw new RuntimeException("CHEH", e);
        }
    }

    static User loginUser(String login, String password){
        User user = null;
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT login, password, userrole FROM user_ WHERE login = ?");
                ResultSet rs = statement.executeQuery()
                ){
            statement.setString(1,login);
            while (rs.next()){
                user.setLogin(rs.getString("login"));
                user.setMotdepasse(rs.getString("password"));
                user.setRole(rs.getString("userrole"));
            }
            if (user.getMotdepasse().equals(password)) return user;
            else return null;
        }catch (SQLException e){
            throw new RuntimeException("Cheh", e);
        }
    }
}

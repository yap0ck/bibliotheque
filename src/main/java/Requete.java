import java.sql.*;
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
                PreparedStatement statement = connection.prepareStatement("SELECT login, password, userrole FROM user_ WHERE login LIKE ?");
                ){
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            try {

                while (rs.next()){
                    user.setLogin(rs.getString("login"));
                    user.setMotdepasse(rs.getString("password"));
                    user.setRole(rs.getString("userrole"));
                    user.setUserId(rs.getInt("userid"));
                }
                rs.close();
                if (user.getMotdepasse().equals(password)) return user;
                else return null;
            } finally {
                rs.close();
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Cheh", e);
        }
    }

    static Livres rechercheLivreDB(String entree){
        Livres livre = new Livres();
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE isbn LIKE ? ");
                ResultSet rs = statement.executeQuery()
        ){
            statement.setString(1,entree);
            if (rs == null){
                System.out.println("Livre non trouvé");
                livre = null;
            } else {
                while (rs.next()){
                    livre.setTitre(rs.getString("name"));
                    livre.setAuteur(rs.getString("author"));
                    livre.setAnneePublication(rs.getInt("publishingYear"));
                    livre.setIsbn(rs.getString("isbn"));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("CHEH", e);
        }
        return livre;
    }

    static ArrayList<Livres> listingLivres(){
        ArrayList<Livres> list = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Book");
                ResultSet rs = statement.executeQuery()
                ){
                while (rs.next()){
                    Livres livre = new Livres();
                    livre.setTitre(rs.getString("name"));
                    livre.setAuteur(rs.getString("author"));
                    livre.setAnneePublication(rs.getInt("publishingYear"));
                    livre.setIsbn(rs.getString("isbn"));
                    livre.setLivreId(rs.getInt("bookid"));
                    list.add(livre);
                }
        } catch (SQLException e){
            throw new RuntimeException("CHEH", e);
        }
        return list;
    }

    static boolean suppressionLivresDB(Livres livre){
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM book WHERE isbn = ?");
                ){
            statement.setString(1, livre.getIsbn());
            return statement.execute();
        } catch (SQLException e){
            throw new RuntimeException("CHEH", e);
        }
    }

    static boolean insertemprunt(Emprunt emprunt){
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO borrow (bookid,userid,borrowdate) VALUES (?,?,?)");
                ){
            statement.setInt(1,emprunt.getLivre().getLivreId());
            statement.setInt(2,emprunt.getUser().getUserId());
            statement.setDate(3, (Date) emprunt.getDateEmprunt());
            return statement.execute();
        } catch (SQLException e){
            throw new RuntimeException("Cheh", e);
        }
    }

    static ArrayList<Livres> listingLivresEmprunte(User user){
        ArrayList<Livres> list = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT b.name, b.author, b.bookid, bo.borrowdate FROM user_ u JOIN public.borrow bo on u.userid = bo.userid JOIN public.book b on b.bookid = bo.bookid WHERE u.userid LIKE ?");
                ){
            statement.setInt(1,user.getUserId());
            ResultSet rs = statement.executeQuery();
            try {
                while (rs.next()){
                    Livres livres = new Livres();
                    Emprunt emprunt = new Emprunt();
                    emprunt.setDateEmprunt(rs.getDate("borrowdate"));
                    livres.setEmprunt(emprunt);
                    livres.setTitre(rs.getString("name"));
                    livres.setAuteur(rs.getString("author"));
                    livres.setLivreId(rs.getInt("bookid"));
                    list.add(livres);
                    rs.close();
                }
            } finally {
                rs.close();
            }
            return list;
        } catch (SQLException e){
            throw new RuntimeException("CHEH", e);
        }
    }

    static boolean modifEmprunt(java.util.Date date, Livres livres){
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE borrow SET returndate = ? WHERE bookid LIKE ?")
                ){
        statement.setDate(1, (Date) date);
        statement.setInt(2,livres.getLivreId());
        return statement.execute();
        } catch (SQLException e){
            throw new RuntimeException("CHEH", e);
        }
    }

    static ArrayList<Livres> listingLivresDispo(){
        ArrayList<Livres> list = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT b.author, b.name FROM book b WHERE bookid LIKE (SELECT bookid FROM borrow WHERE borrowdate IS NULL OR returndate IS NOT NULL )");
                ResultSet rs = statement.executeQuery()
                ){
            while (rs.next()){
                Livres livres = new Livres();
                livres.setAuteur(rs.getString("author"));
                livres.setTitre(rs.getString("name"));
                list.add(livres);
            }
        } catch (SQLException e){
            throw new RuntimeException("CHEH", e);
        }
        return list;
    }
}

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public interface Requete {
    /**
     * Cette fonction insère un nouveau livre dans la base de données avec les informations spécifiées.
     *
     * @param name          Le nom du livre.
     * @param author        L'auteur du livre.
     * @param publishingYear L'année de publication du livre.
     * @param isbn          Le numéro ISBN du livre.
     * @return true si l'insertion a réussi, sinon false.
     */
    static boolean insertBook(String name, String author, int publishingYear, String isbn){
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO book (name,author,publishingyear,isbn) VALUES (?, ?, ?, ?)");
                ){
            // Définit les valeurs des paramètres dans la requête SQL préparée
            statement.setString(1,name);
            statement.setString(2,author);
            statement.setInt(3,publishingYear);
            statement.setString(4,isbn);
            // Exécute la requête SQL d'insertion
            return statement.execute();
        } catch (SQLException e){
            // En cas d'erreur SQL, lance une exception RuntimeException
            throw new RuntimeException("CHEH", e);
        }
    }

    /**
     * Cette fonction vérifie les informations de connexion de l'utilisateur et renvoie l'utilisateur s'il est authentifié avec succès.
     *
     * @param login    Le nom d'utilisateur saisi par l'utilisateur.
     * @param password Le mot de passe saisi par l'utilisateur.
     * @return L'utilisateur authentifié s'il existe, sinon null.
     */
    static User loginUser(String login, String password){
        User user = new User(); // Crée un objet User pour stocker les informations de l'utilisateur
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT login, password, userrole, userid FROM user_ WHERE login LIKE ?");
                ){
            statement.setString(1, login); // Définit le paramètre de la requête SQL préparée avec le nom d'utilisateur saisi
            ResultSet rs = statement.executeQuery(); // Exécute la requête SQL et récupère le résultat
            try {
                while (rs.next()){
                    // Récupère les informations de l'utilisateur depuis le résultat de la requête
                    user.setLogin(rs.getString("login"));
                    user.setMotdepasse(rs.getString("password"));
                    user.setRole(rs.getString("userrole"));
                    user.setUserId(rs.getInt("userid"));
                }
                rs.close();// Ferme le résultat de la requête
                // Vérifie si le mot de passe saisi correspond au mot de passe de l'utilisateur
                if (user.getMotdepasse().equals(password)) return user; // Renvoie l'utilisateur authentifié
                else return null; // Renvoie null si les mots de passe ne correspondent pas
            } finally {
                rs.close(); // Assure la fermeture du résultat de la requête en cas d'exception
            }

        }catch (SQLException e){
            e.printStackTrace(); // Affiche les détails de l'erreur SQL (utile pour le débogage)
            throw new RuntimeException("Cheh", e); // Lance une exception en cas d'erreur SQL
        }
    }

    /**
     * Cette fonction recherche un livre dans la base de données en utilisant le numéro ISBN fourni.
     *
     * @param entree Le numéro ISBN du livre à rechercher.
     * @return L'objet Livres représentant le livre trouvé, ou null si le livre n'est pas trouvé.
     */
    static Livres rechercheLivreDB(String entree) {
        Livres livre = new Livres(); // Crée un objet Livres pour stocker les informations du livre recherché

        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE isbn LIKE ?");
        ) {
            statement.setString(1, entree); // Définit le paramètre de la requête SQL préparée avec le numéro ISBN

            ResultSet rs = statement.executeQuery(); // Exécute la requête SQL et récupère le résultat

            try {
                if (rs == null) {
                    System.out.println("Livre non trouvé");
                    livre = null;
                } else {
                    while (rs.next()) {
                        // Récupère les informations du livre depuis le résultat de la requête
                        livre.setTitre(rs.getString("name"));
                        livre.setAuteur(rs.getString("author"));
                        livre.setAnneePublication(rs.getInt("publishingYear"));
                        livre.setIsbn(rs.getString("isbn"));
                    }
                }

                rs.close(); // Ferme le résultat de la requête

            } finally {
                rs.close(); // Assure la fermeture du résultat de la requête en cas d'exception
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du livre dans la base de données.", e); // Lance une exception en cas d'erreur SQL
        }

        return livre; // Renvoie l'objet Livres représentant le livre trouvé, ou null si le livre n'est pas trouvé
    }


    /**
     * Cette fonction récupère la liste complète des livres depuis la base de données.
     *
     * @return Une liste d'objets Livres représentant tous les livres de la base de données.
     */
    static ArrayList<Livres> listingLivres() {
        ArrayList<Livres> list = new ArrayList<>(); // Crée une liste vide pour stocker les livres

        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM book");
                ResultSet rs = statement.executeQuery();
        ) {
            // Exécute la requête SQL pour obtenir la liste complète des livres
            while (rs.next()) {
                Livres livre = new Livres(); // Crée un nouvel objet Livres pour chaque livre trouvé
                livre.setTitre(rs.getString("name")); // Récupère le titre depuis la colonne "name"
                livre.setAuteur(rs.getString("author")); // Récupère l'auteur depuis la colonne "author"
                livre.setAnneePublication(rs.getInt("publishingYear")); // Récupère l'année de publication depuis la colonne "publishingYear"
                livre.setIsbn(rs.getString("isbn")); // Récupère le numéro ISBN depuis la colonne "isbn"
                livre.setLivreId(rs.getInt("bookid")); // Récupère l'ID du livre depuis la colonne "bookid"
                list.add(livre); // Ajoute l'objet Livres à la liste
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la liste des livres depuis la base de données.", e); // Lance une exception en cas d'erreur SQL
        }

        return list; // Renvoie la liste complète des livres
    }


    /**
     * Cette fonction supprime un livre de la base de données en utilisant le numéro ISBN du livre spécifié.
     *
     * @param livre L'objet Livres représentant le livre à supprimer.
     * @return true si la suppression a réussi, sinon false.
     */
    static boolean suppressionLivresDB(Livres livre) {
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM book WHERE isbn = ?");
        ) {
            statement.setString(1, livre.getIsbn()); // Définit le paramètre de la requête SQL préparée avec le numéro ISBN du livre

            // Exécute la requête SQL de suppression
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du livre depuis la base de données.", e); // Lance une exception en cas d'erreur SQL
        }
    }


    /**
     * Cette fonction insère un nouvel enregistrement d'emprunt dans la base de données.
     *
     * @param emprunt L'objet Emprunt représentant les détails de l'emprunt.
     * @return true si l'insertion a réussi, sinon false.
     */
    static boolean insertemprunt(Emprunt emprunt) {
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO borrow (bookid, userid, borrowdate) VALUES (?, ?, ?)");
        ) {
            statement.setInt(1, emprunt.getLivre().getLivreId()); // Définit l'ID du livre emprunté
            statement.setInt(2, emprunt.getUser().getUserId()); // Définit l'ID de l'utilisateur emprunteur
            statement.setDate(3, Date.valueOf(emprunt.getDateEmprunt())); // Définit la date d'emprunt

            // Exécute la requête SQL d'insertion de l'emprunt
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'emprunt dans la base de données.", e); // Lance une exception en cas d'erreur SQL
        }
    }


    /**
     * Cette fonction récupère la liste des livres empruntés par un utilisateur depuis la base de données.
     *
     * @param user L'objet User représentant l'utilisateur dont les emprunts sont recherchés.
     * @return Une liste d'objets Livres représentant les livres empruntés par l'utilisateur.
     */
    static ArrayList<Livres> listingLivresEmprunte(User user) {
        ArrayList<Livres> list = new ArrayList<>(); // Crée une liste vide pour stocker les livres empruntés par l'utilisateur

        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT b.name, b.author, b.bookid, bo.borrowdate FROM user_ u JOIN public.borrow bo on u.userid = bo.userid JOIN public.book b on b.bookid = bo.bookid WHERE u.userid = ?");
        ) {
            statement.setInt(1, user.getUserId()); // Définit le paramètre de la requête SQL préparée avec l'ID de l'utilisateur

            ResultSet rs = statement.executeQuery(); // Exécute la requête SQL et récupère le résultat

            try {
                while (rs.next()) {
                    Livres livres = new Livres(); // Crée un nouvel objet Livres pour chaque livre emprunté
                    Emprunt emprunt = new Emprunt(); // Crée un nouvel objet Emprunt pour chaque emprunt

                    // Récupère la date d'emprunt depuis la colonne "borrowdate" et la convertit en LocalDate
                    emprunt.setDateEmprunt(rs.getDate("borrowdate").toLocalDate());

                    // Attribution de l'objet Emprunt à l'objet Livres
                    livres.setEmprunt(emprunt);

                    livres.setTitre(rs.getString("name")); // Récupère le titre depuis la colonne "name"
                    livres.setAuteur(rs.getString("author")); // Récupère l'auteur depuis la colonne "author"
                    livres.setLivreId(rs.getInt("bookid")); // Récupère l'ID du livre depuis la colonne "bookid"
                    list.add(livres); // Ajoute l'objet Livres à la liste
                }
            } finally {
                if (rs != null) rs.close(); // Assure la fermeture du résultat de la requête en cas d'exception
            }

            return list; // Renvoie la liste des livres empruntés par l'utilisateur
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des livres empruntés depuis la base de données.", e); // Lance une exception en cas d'erreur SQL
        }
    }


    /**
     * Cette fonction met à jour la date de retour d'un emprunt dans la base de données.
     *
     * @param date   La nouvelle date de retour à enregistrer.
     * @param livres L'objet Livres représentant le livre dont l'emprunt doit être mis à jour.
     * @return true si la mise à jour a réussi, sinon false.
     */
    static boolean modifEmprunt(LocalDate date, Livres livres) {
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE borrow SET returndate = ? WHERE bookid = ?");
        ) {
            statement.setDate(1, Date.valueOf(date)); // Définit le paramètre de la requête SQL préparée avec la nouvelle date de retour
            statement.setInt(2, livres.getLivreId()); // Définit le paramètre de la requête SQL préparée avec l'ID du livre

            // Exécute la requête SQL de mise à jour de la date de retour de l'emprunt
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la date de retour de l'emprunt dans la base de données.", e); // Lance une exception en cas d'erreur SQL
        }
    }


    /**
     * Cette fonction récupère la liste des livres disponibles (non empruntés) depuis la base de données.
     *
     * @return Une liste d'objets Livres représentant les livres disponibles.
     */
    static ArrayList<Livres> listingLivresDispo() {
        ArrayList<Livres> list = new ArrayList<>(); // Crée une liste vide pour stocker les livres disponibles

        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT b.author, b.name FROM book b WHERE bookid IN (SELECT bookid FROM borrow WHERE borrowdate IS NULL OR returndate IS NOT NULL)");
                ResultSet rs = statement.executeQuery();
        ) {
            while (rs.next()) {
                Livres livres = new Livres(); // Crée un nouvel objet Livres pour chaque livre disponible

                livres.setAuteur(rs.getString("author")); // Récupère l'auteur depuis la colonne "author"
                livres.setTitre(rs.getString("name")); // Récupère le titre depuis la colonne "name"

                list.add(livres); // Ajoute l'objet Livres à la liste
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des livres disponibles depuis la base de données.", e); // Lance une exception en cas d'erreur SQL
        }

        return list; // Renvoie la liste des livres disponibles
    }


    /**
     * Cette fonction modifie les informations d'un livre dans la base de données en fonction de l'index spécifié.
     *
     * @param index  L'index spécifiant quelle information du livre doit être modifiée (1 pour le titre, 2 pour l'auteur, 3 pour l'année de publication, 4 pour l'ISBN).
     * @param entree La nouvelle valeur à attribuer à l'information du livre.
     * @param livres L'objet Livres représentant le livre à modifier.
     * @return true si la modification a réussi, sinon false.
     */
    static boolean modifLivre(String index, String entree, Livres livres) {
        String query;

        // Sélectionne la requête SQL appropriée en fonction de l'index spécifié
        switch (index) {
            case "1" -> query = "UPDATE book SET name = ? WHERE bookid = ?";
            case "2" -> query = "UPDATE book SET author = ? WHERE bookid = ?";
            case "3" -> query = "UPDATE book SET publishingYear = ? WHERE bookid = ?";
            case "4" -> query = "UPDATE book SET isbn = ? WHERE bookid = ?";
            default -> throw new RuntimeException("Index invalide : " + index); // Lance une exception en cas d'index non valide
        }

        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, entree); // Définit le paramètre de la requête SQL préparée avec la nouvelle valeur
            statement.setInt(2, livres.getLivreId()); // Définit le paramètre de la requête SQL préparée avec l'ID du livre

            // Exécute la requête SQL de modification des informations du livre
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification des informations du livre dans la base de données.", e); // Lance une exception en cas d'erreur SQL
        }
    }

}

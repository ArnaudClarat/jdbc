import java.sql.*;

public class IntroDB {
    public static void main(String[] args) {
        //chargement du driver
        System.out.println("Chargement du driver");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver chargé");
        } catch (ClassNotFoundException e) {
            // gestion des erreurs
            e.printStackTrace();
            System.out.println("----");
            System.out.println(e.getMessage());
        }

        // Connexion à la base de donnée
        String url = "jdbc:mysql://localhost:3306/5ipoo";
        String user = "root";
        String pass = "";
        Connection connection = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            System.out.println("Tentative de connection");
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connexion DB réussie");

            // Nos requêtes
            stmt = connection.createStatement();
            pstmt = connection.prepareStatement("INSERT INTO membres (nom, prenom, email) VALUES (?,?,?)");
            pstmt.setString(1, "Gorlier");
            pstmt.setString(2, "Laurent");
            pstmt.setString(3,"coucoulaurent");
            int pstatut = pstmt.executeUpdate();

            int statut = stmt.executeUpdate("INSERT INTO Membres (Nom, Prenom, Email) VALUES ('Renard', 'Goupil' , 'test')",
                    Statement.RETURN_GENERATED_KEYS);
            System.out.println("Statut : " + statut);

            if (statut == 1) {
                // Récuperation de l'id
                ResultSet pk = stmt.getGeneratedKeys();
                pk.next();
                System.out.println("La nouvelle pk est " + pk.getInt(1));
                System.out.println("Ajout réussi");
            } else {
                System.out.println("Pas bon");
            }

            // Requête en lecture
            resultSet = stmt.executeQuery("SELECT * FROM Membres");

            // Affichage du resultat
            System.out.println("\nAffichage");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString("Nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR DB : " + e.getMessage());
        } finally {
            // Fermeture du resultat
            if (resultSet != null) {
                System.out.println("Fermeture du résultat");
                try {
                    resultSet.close();
                    System.out.println("Résultat fermé");
                } catch (SQLException e) {
                    System.out.println("Erreur à la fermeture du résultat");
                    e.printStackTrace();
                }
            }

            // Fermeture du statement
            if (stmt != null) {
                System.out.println("Fermeture du statement");
                try {
                    stmt.close();
                    System.out.println("Statement fermé");
                } catch (SQLException e) {
                    System.out.println("Erreur à la fermeture du statement");
                    e.printStackTrace();
                }
            }

            // Fermeture de la connexion
            if (connection != null) {
                System.out.println("Fermeture de la connexion");
                try {
                    connection.close();
                    System.out.println("Connexion fermée");
                } catch (SQLException e) {
                    System.out.println("Erreur à la déconnexion");
                    e.printStackTrace();
                }
            }
        }
    }
}

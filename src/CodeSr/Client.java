package CodeSr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
	String mail;
	String motdepasse;
	String nom;
	String prenom;
	String adresse;
	int idUser;
	
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "echarifs";
    static final String PASSWD = "echarifs";
	
	public Client(String mailConnectedUser) {
		try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection       
            PreparedStatement query=connexion.prepareStatement("SELECT * FROM CLIENT WHERE MAIL= ?");
            query.setString(1,mailConnectedUser);
            ResultSet result = query.executeQuery();
            result.next();
            this.mail=mailConnectedUser;
            this.motdepasse=result.getString(2);
            this.nom=result.getString(3);
            this.prenom=result.getString(4);
            this.adresse=result.getString(5);
            this.idUser = result.getInt(6);
            connexion.close(); 
    } 
    catch (SQLException e) {
        System.err.println("failed");
        e.printStackTrace(System.err);
    }
		
	}
//	public static void main(String[] args) {
//		Client client=new Client("antoin@gmail.com");
//		System.out.println(client.nom);
//		System.out.println(client.motdepasse);
//		System.out.println(client.prenom);
//		System.out.println(client.adresse);
//		System.out.println(client.mail);
//		
//	}
}
package CodeSr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Produit {
	String name;
	String description;
	String URLImage;
	String NOMCATEGORIE;
	int idProduit;
	int prixCourant;
	String[] values;
	String[] caracteristics;
	boolean sold;
	int nbOffers;
	
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "echarifs";
    static final String PASSWD = "echarifs";
	
	public Produit(String ID) {
		
		try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection       
            PreparedStatement query=connexion.prepareStatement("SELECT * FROM PRODUIT WHERE IDPRODUIT = ?");
            query.setInt(1,Integer.valueOf(ID));
            ResultSet result = query.executeQuery();
            result.next();
            this.name=result.getString(2);
            this.description=result.getString(4);
            this.URLImage=result.getString(5);
            this.NOMCATEGORIE=result.getString(6);
            this.prixCourant=result.getInt(3);
            this.idProduit=result.getInt(1);
            PreparedStatement query1=connexion.prepareStatement("SELECT COUNT(IDPRODUIT) FROM OFFRE WHERE IDPRODUIT = ?");
            query1.setInt(1,Integer.valueOf(ID));
            ResultSet res = query1.executeQuery();
            res.next();
            this.nbOffers=res.getInt(1);
            

            PreparedStatement query2=connexion.prepareStatement("SELECT IDPRODUIT FROM ACHAT WHERE IDPRODUIT = ?");
            query2.setInt(1,Integer.valueOf(ID));
            ResultSet reslt = query2.executeQuery();
            this.sold=reslt.next(); 
            
            PreparedStatement query3=connexion.prepareStatement("select  *  from PROPRIETE WHERE IDPRODUIT = ?");
            query3.setInt(1,Integer.valueOf(ID));
            ResultSet res3=query3.executeQuery();
            PreparedStatement query4=connexion.prepareStatement("select count(*)  from PROPRIETE WHERE IDPRODUIT = ?");
            query4.setInt(1,Integer.valueOf(ID));
            ResultSet res4=query4.executeQuery();
            res4.next();
            int nbrcara=res4.getInt(1);
            this.values=new String[nbrcara];
            this.caracteristics=new String[nbrcara];
            if(res3.next()) {
            	for(int i=0;i<nbrcara;i++) {
            		values[i]=res3.getString(2);
            		caracteristics[i]=res3.getString(1);
            		res3.next();
            	}
            }
            
            
            
            connexion.close(); 
    } 
    catch (SQLException e) {
        System.err.println("failed");
        e.printStackTrace(System.err);
    }
		
	}
	
//	public static void main(String[] args) {
//		Produit produit=new Produit("60");
//		System.out.println(produit.values[4]);
//		System.out.println(produit.caracteristics[4]);
//		
//	}
}
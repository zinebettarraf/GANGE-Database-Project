package CodeSr;

import static javax.swing.JOptionPane.showMessageDialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Categories {
	
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "echarifs";
    static final String PASSWD = "echarifs";

    public static String[] categorieSRoot() {
    	String[] categories=null;
    	/*** récuperer de la base des données les catégories ROOT ***/
    	try {
    	   	
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection       
            PreparedStatement query=connexion.prepareStatement("SELECT DISTINCT NOMMERE FROM HIERARCHIE WHERE NOMMERE NOT IN (SELECT NOMFILLE FROM HIERARCHIE)");
            ResultSet result = query.executeQuery();
            // nombre des categories root est 
            PreparedStatement query1=connexion.prepareStatement("SELECT  COUNT(NOMMERE) FROM ( SELECT DISTINCT NOMMERE FROM HIERARCHIE WHERE NOMMERE NOT IN (SELECT NOMFILLE FROM HIERARCHIE))");
            ResultSet res = query1.executeQuery();
            res.next();
            int categoriesRootNbr=res.getInt(1);
            categories=new String[categoriesRootNbr];
            for(int i=0;i<categoriesRootNbr;i++){
                result.next();
            	categories[i]=result.getString(1);
            }
            connexion.close();
        } 
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	return categories;
    }

    public static String[] categoriesFilles(String categorie) {
    	String[] categories=null;
    	/*** recuperer de la base de données les catégories filles de la premières géneration d'une catégorie donnée ***/
    	try {
    		
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection       
            PreparedStatement query=connexion.prepareStatement("SELECT NOMFILLE FROM HIERARCHIE WHERE NOMMERE = ?");
            query.setString(1, categorie);
            ResultSet result = query.executeQuery();
      
            // nombre des categories filles  est 
            PreparedStatement query1=connexion.prepareStatement("SELECT  COUNT( NOMFILLE) FROM ( SELECT NOMFILLE FROM HIERARCHIE WHERE NOMMERE = ?)");
            query1.setString(1, categorie);
            ResultSet res = query1.executeQuery(); 
            res.next();
            int categoriesFillesNbr=res.getInt(1);
            categories=new String[categoriesFillesNbr];
            if(categoriesFillesNbr!=0) {
                for(int i=0;i<categoriesFillesNbr;i++){
                    result.next();
                	categories[i]=result.getString(1);
                }
            }


            connexion.close();
        } 
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	return categories ;
    }
    public static boolean inCategorie(String categorie) {
    	boolean has=false;
    	try {	
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection       
            PreparedStatement query=connexion.prepareStatement("SELECT  * FROM CATEGORIE WHERE NOM=?");
            query.setString(1, categorie);
            ResultSet result = query.executeQuery();        
            has= result.next();
            
    	}
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	return has;
            
    }
    public static String[] hasProducts(String categorie) {
    	/*** for category  that have not subcategories return  String[] of product's ID if category in Categorie ; String[] is empty if category has any products ***/
    	String[] products=new String[] {};
    	try {
    		
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection       
            if (Categories.inCategorie(categorie)) {
            	/* categorie qui a  de produits*/ 
                PreparedStatement query1=connexion.prepareStatement("SELECT a.* FROM\n"
                		+ "(SELECT PRODUIT.IDPRODUIT, INTITULE FROM PRODUIT JOIN OFFRE on PRODUIT.IDPRODUIT = OFFRE.IDPRODUIT\n"
                		+ "WHERE NOMCATEGORIE = ? and PRODUIT.IDPRODUIT NOT IN (SELECT ACHAT.IDPRODUIT FROM ACHAT)\n"
                		+ "GROUP BY PRODUIT.IDPRODUIT,OFFRE.IDPRODUIT, INTITULE ORDER BY  COUNT(OFFRE.IDPRODUIT)  DESC, INTITULE )  a\n"
                		+ "UNION ALL\n"
                		+ "SELECT b.* FROM\n"
                		+ "(SELECT PRODUIT.IDPRODUIT,INTITULE FROM PRODUIT  WHERE NOMCATEGORIE = ? AND PRODUIT.IDPRODUIT\n"
                		+ "NOT IN (SELECT DISTINCT OFFRE.IDPRODUIT FROM OFFRE) and PRODUIT.IDPRODUIT NOT IN (SELECT ACHAT.IDPRODUIT FROM ACHAT) ORDER BY INTITULE) b");
                query1.setString(1, categorie);
                query1.setString(2, categorie);
                ResultSet res = query1.executeQuery(); 
                /* nombre de produits dans cette categorie */
                PreparedStatement query2=connexion.prepareStatement("SELECT COUNT(IDPRODUIT) FROM (SELECT a.* FROM\n"
                		+ "(SELECT PRODUIT.IDPRODUIT, INTITULE FROM PRODUIT JOIN OFFRE on PRODUIT.IDPRODUIT = OFFRE.IDPRODUIT \n"
                		+ "WHERE NOMCATEGORIE = ?  and PRODUIT.IDPRODUIT NOT IN (SELECT ACHAT.IDPRODUIT FROM ACHAT) \n"
                		+ "GROUP BY PRODUIT.IDPRODUIT,OFFRE.IDPRODUIT, INTITULE ORDER BY  COUNT(OFFRE.IDPRODUIT)  DESC, INTITULE )  a\n"
                		+ "UNION ALL\n"
                		+ "SELECT b.* FROM\n"
                		+ "(SELECT PRODUIT.IDPRODUIT,INTITULE FROM PRODUIT  WHERE NOMCATEGORIE =  ?  AND PRODUIT.IDPRODUIT\n"
                		+ "NOT IN (SELECT DISTINCT OFFRE.IDPRODUIT FROM OFFRE)and PRODUIT.IDPRODUIT NOT IN (SELECT ACHAT.IDPRODUIT FROM ACHAT) ORDER BY INTITULE) b)");
                query2.setString(1, categorie);
                query2.setString(2, categorie);
                ResultSet reslt = query2.executeQuery(); 
                reslt.next();
                int nbrProducts=reslt.getInt(1);
                products=new String[nbrProducts];
                    for(int i=0;i<nbrProducts;i++){
                        res.next();
                    	products[i]=res.getString(1);
                }
            }
            connexion.close();
        } 
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	
    	return products;
    }
    public static String allProductsCategory(String categorie) {
    	
    	/** for category in any generation and that have not subcategoies  check if it has products or not and return a String of its products if exist **/
    	String idProductsString="";
    	String[] filles=Categories.categoriesFilles(categorie);
    	if(filles.length==0) {
    		String[] idProducts=Categories.hasProducts(categorie);
    		if(idProducts.length==0) {
    			return "";
    		}
    		else {
        		idProductsString +=String.join(",", idProducts);
        		return ","+idProductsString ;
    		}

    		}
    	else {
    		for(String fille :filles) {
    			idProductsString+=allProductsCategory(fille);
                  }
    		return idProductsString;
      	}
  
    }
    public static String[] sortedAllProductsCategory(String categorie) {
    	String chaine=allProductsCategory(categorie);
    	if(chaine.length()==0) {
    		return new String[] {};
    	}
    	String stringProducts ="("+allProductsCategory(categorie).substring(1)+")";
    	String[] products=new String[] {};
    	try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection
            Statement stmt = connexion.createStatement ();
            ResultSet res =stmt.executeQuery("SELECT a.* FROM\n"
            		+ "(SELECT PRODUIT.IDPRODUIT, INTITULE FROM PRODUIT JOIN OFFRE on PRODUIT.IDPRODUIT = OFFRE.IDPRODUIT\n"
            		+ "WHERE PRODUIT.IDPRODUIT IN "+ stringProducts+"\n"
            		+ "GROUP BY PRODUIT.IDPRODUIT,OFFRE.IDPRODUIT, INTITULE ORDER BY  COUNT(OFFRE.IDPRODUIT)  DESC, INTITULE )  a\n"
            		+ "UNION ALL\n"
            		+ "SELECT b.* FROM\n"
            		+ "(SELECT PRODUIT.IDPRODUIT,INTITULE FROM PRODUIT  WHERE PRODUIT.IDPRODUIT IN "+ stringProducts+"  AND PRODUIT.IDPRODUIT\n"
            		+ "NOT IN (SELECT DISTINCT OFFRE.IDPRODUIT FROM OFFRE) ORDER BY INTITULE) b");
            /* nombre de produits */
            Statement stmt2 =connexion.createStatement ();
            ResultSet reslt = stmt2.executeQuery("SELECT COUNT(IDPRODUIT) FROM (SELECT a.* FROM\n"
            		+ "(SELECT PRODUIT.IDPRODUIT, INTITULE FROM PRODUIT JOIN OFFRE on PRODUIT.IDPRODUIT = OFFRE.IDPRODUIT \n"
            		+ "WHERE PRODUIT.IDPRODUIT IN "+ stringProducts+"\n"
            		+ "GROUP BY PRODUIT.IDPRODUIT,OFFRE.IDPRODUIT, INTITULE ORDER BY  COUNT(OFFRE.IDPRODUIT)  DESC, INTITULE )  a \n"
            		+ "UNION ALL \n"
            		+ "SELECT b.* FROM \n"
            		+ "(SELECT PRODUIT.IDPRODUIT,INTITULE FROM PRODUIT  WHERE PRODUIT.IDPRODUIT IN "+ stringProducts+"  AND PRODUIT.IDPRODUIT \n"
            		+ "NOT IN (SELECT DISTINCT OFFRE.IDPRODUIT FROM OFFRE) ORDER BY INTITULE) b)");
                reslt.next();
                int nbrProducts=reslt.getInt(1);
                products=new String[nbrProducts];
                    for(int i=0;i<nbrProducts;i++){
                        res.next();
                    	products[i]=res.getString(1);
                }
            
            
            connexion.close();
        } 
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	return products;
    }
 
    public static String[] personalRecomendedCategories(String mailConnectedUser) {
    	String[] recomendedcategories=new String[] {};
    	try {
    		
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection
            /* recuperer id client */
            PreparedStatement query1=connexion.prepareStatement("SELECT IDUTILISATEUR FROM CLIENT WHERE MAIL = ?");
            query1.setString(1, mailConnectedUser);
            ResultSet result1 =query1.executeQuery();
            result1.next();
            int ID =result1.getInt(1);
    
            /* recuperer de la base personal Recomended Categories for the connected user */
            PreparedStatement query2=connexion.prepareStatement("select PRODUIT.NOMCATEGORIE\n"
            		+ "from Produit join offre on produit.idproduit = offre.idproduit where offre.idutilisateur = ?\n"
            		+ "and offre.idproduit not in (select  Idproduit from achat )\n"
            		+ "group by PRODUIT.NOMCATEGORIE\n"
            		+ "Order by count(offre.idproduit) DESC");
            query2.setInt(1, ID);
            ResultSet result =query2.executeQuery();
            PreparedStatement query3=connexion.prepareStatement("select count(NOMCATEGORIE) from(select PRODUIT.NOMCATEGORIE\n"
            		+ "from Produit join offre on produit.idproduit = offre.idproduit where offre.idutilisateur = ?\n"
            		+ "and offre.idproduit not in (select  Idproduit from achat )\n"
            		+ "group by PRODUIT.NOMCATEGORIE\n"
            		+ "Order by count(offre.idproduit) DESC)");
            query3.setInt(1, ID);
            ResultSet resultCount =query3.executeQuery();
            resultCount.next();
            int nbrCategories=resultCount.getInt(1);
            recomendedcategories=new String[nbrCategories]; 
            for(int i=0;i<nbrCategories;i++) {
            		result.next();
            		recomendedcategories[i]=result.getString(1);         		
            	}
            
            connexion.close();
        
        } 
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	return recomendedcategories;
    }
    
    
    
    
    
    
    
    
    public static String[] trendingCategories() {
    	String[] trendingCategories=new String[] {};
    	try {
    		
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection
;
   
            Statement smt1 =connexion.createStatement();
            smt1.executeQuery("Create view newtable as \n"
            		+ "Select offre.idproduit as prod, count(Offre.DATEHEUREDEPOTOFFRE) as nboffres\n"
            		+ "from offre \n"
            		+ "group by offre.idproduit");
            Statement smt2 =connexion.createStatement();
            ResultSet result= smt2.executeQuery("select produit.nomcategorie\n"
            		+ "from produit join newtable on produit.idproduit = newtable.prod\n"
            		+ "group by produit.nomcategorie\n"
            		+ "order by avg(newtable.nboffres) desc");
            Statement smt3 =connexion.createStatement();
            ResultSet resultcount= smt3.executeQuery("select count(nomcategorie) from (select produit.nomcategorie\n"
            		+ "from produit join newtable on produit.idproduit = newtable.prod\n"
            		+ "group by produit.nomcategorie\n"
            		+ "order by avg(newtable.nboffres) desc)");            
            Statement smt4 =connexion.createStatement();
            smt4.executeQuery("drop view newtable");
            resultcount.next();
            int nbrCategories=resultcount.getInt(1);
            trendingCategories=new String[nbrCategories]; 
            for(int i=0;i<nbrCategories;i++) {
            		result.next();
            		trendingCategories[i]=result.getString(1);         		
            	}
            
            connexion.close();
        
        } 
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	return trendingCategories;
    }
    public static void deleteClientAccount(String mailConnectedUser) {
    	/* this function delete a client account and modify it ID in Utilisateur table  */
    	
    	try {
    		// Enregistrement du driver Oracle
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  
            connexion.setAutoCommit(false);
            connexion.setTransactionIsolation(connexion.TRANSACTION_SERIALIZABLE);
            // Etablissement de la connection
            /* recuperer id client */
            PreparedStatement query5=connexion.prepareStatement("SELECT MAX(IDUTILISATEUR) FROM UTILISATEUR");
            ResultSet rs = query5.executeQuery();
            rs.next();
            int alterId =rs.getInt(1);
            alterId+= 1;
            PreparedStatement query1=connexion.prepareStatement("SELECT IDUTILISATEUR FROM CLIENT WHERE MAIL = ?");
            query1.setString(1, mailConnectedUser);
            ResultSet result1 =query1.executeQuery();
            result1.next();    
            int ID =result1.getInt(1);
            /** UPDATE IN TABLE OFFRE UTILISATEUR AND DELETE FROM CLIENT */
            PreparedStatement smt1=connexion.prepareStatement("INSERT INTO UTILISATEUR VALUES(?)");
            smt1.setInt(1, alterId);
            smt1.execute();
            
            PreparedStatement smt2=connexion.prepareStatement("UPDATE OFFRE SET IDUTILISATEUR=? WHERE IDUTILISATEUR=?");
            smt2.setInt(1, alterId);
            smt2.setInt(2, ID);
            smt2.execute();
            PreparedStatement query=connexion.prepareStatement("Delete  from client where mail = ?");
            query.setString(1, mailConnectedUser);
            query.execute();
            PreparedStatement smt3=connexion.prepareStatement("Delete  from UTILISATEUR where IDUTILISATEUR = ?");
            smt3.setInt(1, ID);
            smt3.execute();
            connexion.commit();
            connexion.close();

    	}   
            
    	
        catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    	
    }
    public static void deleteProduct(int idProduct) {
    	
    	
    }
    public static void main(String[] args) {

    	System.out.println(sortedAllProductsCategory("CHAUSSURES").toString());
    	System.out.println(sortedAllProductsCategory("CHAUSSURES")[1]);
    	System.out.println(sortedAllProductsCategory("CHAUSSURES")[2]);

    }
}

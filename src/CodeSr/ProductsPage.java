package CodeSr;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductsPage extends JFrame {
	
	private int length = 720;
	private int width = 700;
	private JScrollPane scrollpane;
	Produit[] productsList;
	//String produits[];
	//String links[];
	
	
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "echarifs";
    static final String PASSWD = "echarifs";
	
	
	
	public ProductsPage(Produit[] productsList) {
		
		
		// Calling the super class 
		super("Welcome to the App");
		
		this.productsList = productsList;
		//this.links = links;
		
		
		// Set the size of the frame
        setSize(width, length);
        
        //set Default close operation of JFrame.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
         
        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenuItem m1 = new JMenu("Browse Categories");
        JMenuItem m2 = new JMenuItem("Bought Items");
        JMenuItem m3 = new JMenuItem ("My Info");

        JMenuItem m11 = new JMenuItem("All Categories");
        JMenuItem m12 = new JMenuItem("Personal Recommendations");
        JMenuItem m13 = new JMenuItem ("Now Trending");
        
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);
        
        //Produit[] boughtItems = findBoughtItems();
        //AbstractAction actionBought = new ActionGoToBoughtItems (this, boughtItems);
        //m2.setAction(actionBought);
        //String Client[] = {"Johnny", "Depp", "johnny.Depp@gmail.com", "DeppSparrow", "Grenoble"};
        AbstractAction actionInfo = new ActionGoToPersonalInfo (this);
        m3.setAction(actionInfo);
        
        m11.setAction(new ActionGoToRecs(this, 0));
        m11.setText ("All Categories");
        m12.setAction(new ActionGoToRecs(this, 1));
        m12.setText("Personal Recommendations");
        m13.setAction(new ActionGoToRecs(this, 2));
        m13.setText("Now Trending");
        
       
        mb.add(m1);
        //mb.add(m2);
        mb.add(m3);

        getContentPane().add(BorderLayout.NORTH, mb);
        
        FillThePage();
        
        // Make the frame Visible
        setVisible(true);
	  
	}
	
	//ADDD CHECK INFO AS A BUTTOONN!!!
	//ALSO PRIX COURANT ET PETITE FENETRE QUI S'AFFIICCHHEE!!!
	
	 /**
	   * @param args
	   * @throws IOException 
	  */
	
	public void FillThePage() {
 
      //Creating the Pane in the middle, scrolling through products
    	// image - Name - intitulé - Like - Buy
        
        String titles[] = {"image", "intitulé", "Check Info", "Buy"};
 
        //récup la liste des elts
       
 
     
        // Declaration of the JPanel class..
        JPanel p = new JPanel();
 
        // Set size of JFrame.
        p.setSize(600, 900);
        p.setBackground(new Color(128,0,0,70));
        // Function to set Layout of JFrame.
        int nbLines = this.productsList.length;
        p.setLayout(new GridLayout(nbLines+1, 4, 10, 0));
 
        // for loop
        for (int row = 0; row < nbLines+1; row++) {
 
            for (int col = 0; col < 4; col++)
            {
                 
                // If condition
                if (row == 0) {
 
                    // add new Jlabel
                    p.add(new JLabel(titles[col]));
                }
                else {

                	// If condition
                    if (col == 1)
                    {
                        // add new Jlabel
                        p.add(new JLabel(this.productsList[row - 1].name));
                    }
                    else if (col == 2) {
                    	JButton info = new JButton ("Check Info");
                    	int currentPrice = this.productsList[row - 1].prixCourant;
                    	int nbPeople = this.productsList[row - 1].nbOffers;
                    	String[] caracteristics=this.productsList[row - 1].caracteristics;
                    	String[] values=this.productsList[row - 1].values;
                    	String description = this.productsList[row - 1].description;
                    	info.setAction(new ActionGetInfo(description, currentPrice ,nbPeople,caracteristics,values));
                    	info.setText("Check Info");
                    	p.add(info);
                    }
                    
                    
                    else if (col == 3) {
                    	JButton buy = new JButton ("Buy");
                    	ActionBought action = new ActionBought(this, this.productsList[row - 1]);
                    	buy.setAction(action);
                    	buy.setText("Buy");
                    	p.add(buy);
                    }
                    else if (col == 0) {
                    	BufferedImage bufImage = null;
                    	try {
                    		URL url = new URL (this.productsList[row - 1].URLImage);
//                    		URL url = new URL ("https://fr.louisvuitton.com/images/is/image/lv/1/PP_VP_L/louis-vuitton-casquette-cap-ou-pas-cap-chapeaux-et-gants--M76528_PM2_Front%20view.png?wid=2048&hei=2048");
                    		bufImage = ImageIO.read(url);
                    	}
                    	catch(IOException e){
                    		e.printStackTrace();
                    	}
                    	
                    	ImageIcon oldIcon = new ImageIcon(bufImage);
                    	Image scaled = oldIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                    	ImageIcon newIcon = new ImageIcon(scaled);
                    	JLabel label = new JLabel (newIcon);
                    	p.add(label);
                    }
                    /**
                    else
                    {
                    	p.add(new JLabel(this.productsList[row - 1].description));

                    } */
                }
            }
        }
 
        // Declaration of objects
        // of scrollpane class.
        scrollpane = new JScrollPane(p);
 
        // to get content pane
        getContentPane().add(scrollpane, BorderLayout.CENTER);
        
        
	}
	
	
	public class ActionGetInfo extends AbstractAction{
		int currentPrice;
		int nbOfPeople;
		String info;
		String[] values;
		String[] caracteristics;
		public ActionGetInfo(String description, int price, int nbPeople,String[] caracteristics,String[] values) {
			this.currentPrice = price;
			this.nbOfPeople = nbPeople;
			this.caracteristics=caracteristics;
			this.values=values;		
			//System.out.println("helloo");
			
			this.info = description + "\n The Current Price is : " + this.currentPrice + "\n Characteristics/Values : \n" ;
			int nbrpv=this.caracteristics.length;
			for(int i=0;i<nbrpv;i++) {
				this.info+=this.caracteristics[i] +"                    "+ this.values[i] +"\n";
			}
			this.info+="\n The numbers of people who bid on it is :" + this.nbOfPeople;
		
		}
		
		public void actionPerformed(ActionEvent e){ 
			JOptionPane.showMessageDialog(null, this.info);
		}
	}  
	
	public class ActionLiked extends AbstractAction{
		String info = "Your Product has been added to your 'Liked Items' List" ;
		public void actionPerformed(ActionEvent e){ 
			JOptionPane.showMessageDialog(null,info);
			//Add the fact that The Item SHOULD ACTUALLY BE ADDED TO THE LIST
		}
	}
	
	public class ActionBought extends AbstractAction{
		JFrame newFrame = new JFrame("Make An Offer");
		int priceOffered;
		String info = "Want To make An offer? Please offer a price!" ;
		int currentPrice;
		int idProduit;
		int nbrOffres;
		Produit product;
		ProductsPage menu;
		public ActionBought(ProductsPage menu, Produit product) {
			this.currentPrice=product.prixCourant;
			this.idProduit=product.idProduit;
			this.nbrOffres=product.nbOffers;
			this.menu = menu;
		}
		public void actionPerformed(ActionEvent e){ 
			String result = (String) JOptionPane.showInputDialog(
					newFrame,
					info, 
					"Making an offer",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"The Price");
			
			try {
				priceOffered = Integer.parseInt(result);
				if(priceOffered<this.currentPrice) {
					JOptionPane.showMessageDialog(null,"The price must exceed the current price ");
				}
				else {
					//System.out.println("whyyy : "+ Login.getBidParameter() + "\n" );
					if(this.nbrOffres+1 >= Login.getBidParameter()) {
						JOptionPane.showMessageDialog(null,"Congrats !! You have won the bid ");
				
						WinningOffer(priceOffered, this.idProduit);
						String cats[]= Categories.categorieSRoot();
						this.menu.setVisible(false);
						//new ProductsPage(this.menu.productsList);
						new BrowseCategories(cats);
						
					}
					else {
						JOptionPane.showMessageDialog(null,"Your offer has been saved \n You can try to make new offer");
					
						OfferMade(priceOffered, this.idProduit);
						String cats[]= Categories.categorieSRoot();
						this.menu.setVisible(false);
						new BrowseCategories(cats);
					}
				}
			}
			catch (NumberFormatException ex){
				priceOffered = 0;
			}
			
			//return (priceOffered);
			
			
			//Add the fact that we should compare the price Offered to the prices offered before (au prix Courant surtt)
			//Add the fact that The Offer should be added to the Table related to it
		}
		
		public void setPriceOffered(int number) {
			this.priceOffered = number;
			
		}
		
		public int getPriceOffered() {
			return this.priceOffered;
		}
	}
	
	public void OfferMade(int priceOffered, int idProduit) {
		try {
			
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);
            connexion.setAutoCommit(false);
            connexion.setTransactionIsolation(connexion.TRANSACTION_SERIALIZABLE);
			Client client = new Client(Login.mailConnectedUser);
			int idUser = client.idUser;
			PreparedStatement query2 = connexion.prepareStatement("Update Produit set prixcourant=? where idproduit = ?");
			query2.setInt(1,priceOffered);
			query2.setInt(2, idProduit);
			query2.executeQuery();
			//System.out.println("whoaah");
			//PreparedStatement query1=connexion.prepareStatement("INSERT INTO OFFRE VALUES(to_timestamp( '03/18/2012 02:35 AM', 'MM/DD/YYYY HH:MI AM'),60,135,6)");
			PreparedStatement query1=connexion.prepareStatement("INSERT INTO OFFRE VALUES(CURRENT_TIMESTAMP,?,?,?)");
			query1.setInt(1,idProduit);
			query1.setInt(2, priceOffered);
			query1.setInt(3, idUser);
			query1.executeQuery();
			
            connexion.commit();
            connexion.close();
		}
		catch(SQLException e){
			 System.err.println("failed");
	         e.printStackTrace(System.err);
		}
	}
	
	public void WinningOffer (int priceOffered, int idProduit) {
		OfferMade(priceOffered, idProduit);
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);
            connexion.setTransactionIsolation(connexion.TRANSACTION_SERIALIZABLE);
			Client client = new Client(Login.mailConnectedUser);
			int idUser = client.idUser;
			PreparedStatement query1=connexion.prepareStatement("INSERT INTO ACHAT VALUES(?, (select DATEHEUREDEPOTOFFRE from offre where idProduit = ? and PRIXPROPSE = (select max(PRIXPROPSE) from offre where  idProduit = ? )))");
			query1.setInt(1, idProduit);
			query1.setInt(2, idProduit);
			query1.setInt(3, idProduit);
			query1.executeQuery();
            connexion.commit();
            connexion.close();
			
		}
		catch(SQLException e){
			 System.err.println("failed");
	         e.printStackTrace(System.err);
		}
	}
	
	
//	public static void main(String args[]) {
//		Produit pro1=new Produit("67");
//		Produit pro2=new Produit("66");
//		Produit pro3=new Produit("61");
//		Produit pro4=new Produit("51");
//		Produit[] productsList=new Produit[] {pro1,pro2,pro3,pro4};
//	   	new ProductsPage(productsList);
//
//    } 
	
	

}
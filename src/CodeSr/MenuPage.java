package CodeSr;



import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;

public class MenuPage extends JFrame {
	
	private int length = 720;
	private int width = 650;
	JScrollPane scrollpane;
	
	public MenuPage() {
		
		// Calling the super class 
		super("Welcome to the App");
		// Set the size of the frame
        setSize(width, length);
        
        //set Default close operation of JFrame.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
      //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Browse Categories");
        JMenu m2 = new JMenu("Bought Items");
        JMenu m3 = new JMenu ("My Info");
        JMenu m4 = new JMenu ("Liked Products");
        mb.add(m1);
        mb.add(m2);
        mb.add(m4);
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
    	// image - Name - intitul� - Like - Buy
        
        String titles[] = {"image", "Name", "intitul�", "Check Info", "like", "Buy"};
 
        //r�cup la liste des elts
        String produits[] = {"Produit1", "Produit2", "Produit3", "Produit4", "Produit5", "Produit6",
        		"Produit7", "Produit8", "Produit9", "produit10", "produit11", "produit12", "produit13", "produit14", "produit15", "produit16", "produit17"};
 
     
        // Declaration of the JPanel class..
        JPanel p = new JPanel();
 
        // Set size of JFrame.
        p.setSize(600, 900);
 
        // Function to set Layout of JFrame.
        int nbLines = produits.length;
        p.setLayout(new GridLayout(nbLines+1, 6, 10, 0));
 
        // for loop
        for (int row = 0; row < nbLines+1; row++) {
 
            for (int col = 0; col < 6; col++)
            {
                 
                // If condition
                if (row == 0) {
 
                    // add new Jlabel
                    p.add(new JLabel(titles[col]));
                }
                else {
                    // If condition
                	// If condition
                    if (col == 1)
                    {
                        // add new Jlabel
                        p.add(new JLabel(produits[row - 1]));
                    }
                    else if (col == 3) {
                    	JButton info = new JButton ("Check Info");
                    	info.setAction(new ActionGetInfo());
                    	info.setText("Check Info");
                    	p.add(info);
                    }
                    else if (col == 4) {
                    	JButton like = new JButton ("Like");
                    	like.setAction(new ActionLiked());
                    	like.setText("Like");
                    	p.add(like);
                    }
                    
                    else if (col == 5) {
                    	JButton buy = new JButton ("Buy");
                    	ActionBought action = new ActionBought();
                    	buy.setAction(action);
                    	buy.setText("Buy");
                    	p.add(buy);
                    }
                    else if (col == 0) {
                    	BufferedImage bufImage = null;
                    	try {
                    		URL url = new URL ("https://th.bing.com/th/id/R.d34214adb0000b81354a4a1e55c4e816?rik=gdawekb25y3Gaw&pid=ImgRaw&r=0");
                    		bufImage = ImageIO.read(url);
                    	}
                    	catch(IOException e){
                    		e.printStackTrace();
                    	}
                    	
                    	ImageIcon oldIcon = new ImageIcon(bufImage);
                    	Image scaled = oldIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                    	ImageIcon newIcon = new ImageIcon(scaled);
                    	JLabel label = new JLabel (newIcon);
                    	p.add(label);
                    }
                    else
                    {
                    	p.add(new JLabel("to be added"));

                    }
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
		String info = "The Current Price is : " + 120 + "\n The numbers of people who bid on it is :" + 3;
		public void actionPerformed(ActionEvent e){ 
			JOptionPane.showMessageDialog(null,info);
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
		public void actionPerformed(ActionEvent e){ 
			String result = (String) JOptionPane.showInputDialog(
					newFrame,
					info, 
					"Making an offer",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"Well");
			
			try {
				priceOffered = Integer.parseInt(result);
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
	
	public static void main(String args[]) {
	   	new MenuPage();

    }
	
	

}

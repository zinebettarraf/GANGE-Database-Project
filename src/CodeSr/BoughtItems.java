package CodeSr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

//import CodeSr.MenuPage.ActionGetInfo;


public class BoughtItems extends JFrame{
	
	private int length = 500;
	private int width = 900;
	Produit[] produits;
	JScrollPane scrollpane;
	
	
	public BoughtItems(Produit[] produits) {
		
		// Calling the super class 
		super("Bought Items");
		
		this.produits = produits;
		// Set the size of the frame
        setSize(width, length);
        
        //set Default close operation of JFrame.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Browse");
        //JMenu m2 = new JMenu("Bought Items");
        JMenu m3 = new JMenu ("My Info");
        JMenu m4 = new JMenu ("Liked Products");
        mb.add(m1);
        //mb.add(m2);
        mb.add(m4);
        mb.add(m3);
        JMenuItem m11 = new JMenuItem("Categories");
        JMenuItem m22 = new JMenuItem("Recommendations");
        m1.add(m11);
        m1.add(m22);
        
        getContentPane().add(BorderLayout.NORTH, mb);
        
        
        FillThePage();
        
        // Make the frame Visible
        setVisible(true);
	  
	}
	
	 /**
	   * @param args
	   * @throws IOException 
	   */
	
	public void FillThePage() {
		
		/**
		JPanel page = new JPanel();
		page.setSize(width, length);
		
		
		//JLabel text = new JLabel (header[col]);
		JLabel text = new JLabel ("All the Items That You have bought");
		text.setFont(new Font("Serif", Font.BOLD, 45));
		text.setForeground(Color.RED);
		page.add(text, BorderLayout.NORTH ); */
		
		
 
      //Creating the Pane in the middle, scrolling through products
    	// image - Name - intitulé - Like - Buy

        String header[] = {"Products", "You", "have", "Bought"};
        String titles[] = {"image", "Name", "intitulé", "Check Info"};
 
        //récup la liste des elts
        //String produits[] = {"Produit1", "Produit2", "Produit3", "Produit4", "Produit5", "Produit6", "Produit9", "produit10", "produit11", "produit12", "produit13", "produit14", "produit15", "produit16"};
        
     
        // Declaration of the JPanel class..
        JPanel p = new JPanel();
 
        // Set size of JFrame.
        p.setSize(600, 900);
        
        
 
        // Function to set Layout of JFrame.
        int nbLines = produits.length;
        p.setLayout(new GridLayout(nbLines+2, 4, 10, 0));
        
 
        // for loop
        for (int row = 0; row < nbLines+2; row++) {
 
            for (int col = 0; col < 4; col++)
            {
            	
                // If condition
            	
            	if (row == 0) {
            		JLabel text = new JLabel(header[col]);
            		text.setFont ( new Font("Serif", Font.BOLD, 42));
					text.setForeground(Color.RED);
           		 	p.add(text);
            	}
            	
            	else if (row == 1) {
 
                    // add new Jlabel
                    p.add(new JLabel(titles[col]));
                }
                else {
                    // If condition
                	// If condition
                    if (col == 1)
                    {
                        // add new Jlabel
                    	JLabel text = new JLabel(this.produits[row - 2].name);
                        p.add(text);
                    }
                    else if (col == 3) {
                    	JButton info = new JButton ("Check Info");
                    	Produit product= this.produits[row - 2];
                    	info.setAction(new ActionGetInfo(product.description, product.prixCourant ,product.nbOffers));
                    	info.setText("Check Info");
                    	p.add(info);
                    }
                    
                    else if (col == 0) {
                    	BufferedImage bufImage = null;
                    	try {
                    		URL url = new URL (this.produits[row-2].URLImage);
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
        
        
        scrollpane = new JScrollPane(p);
        
        //page.add(scrollpane, BorderLayout.CENTER);
 
        // to get content pane
        getContentPane().add(scrollpane, BorderLayout.CENTER);
        
        
        
        
	}

	
	public class ActionGetInfo extends AbstractAction{
		int currentPrice;
		int nbOfPeople;
		String info;
		public ActionGetInfo(String description, int price, int nbPeople) {
			this.currentPrice = price;
			this.nbOfPeople = nbPeople;
			//System.out.println("helloo");
			this.info = description + "\n The Current Price is : " + this.currentPrice + "\n The numbers of people who bid on it is :" + this.nbOfPeople;
		}
		
		public void actionPerformed(ActionEvent e){ 
			JOptionPane.showMessageDialog(null, this.info);
		}
	} 
	
	

	/**
	public static void main(String args[]) {
		String prods[] = {"Produit1", "Produit2", "Produit3", "Produit4", "Produit5", "Produit6"};
		 
		 String links[] = {"https://th.bing.com/th/id/R.d34214adb0000b81354a4a1e55c4e816?rik=gdawekb25y3Gaw&pid=ImgRaw&r=0", "https://th.bing.com/th/id/R.d34214adb0000b81354a4a1e55c4e816?rik=gdawekb25y3Gaw&pid=ImgRaw&r=0", 
				 "https://th.bing.com/th/id/R.d34214adb0000b81354a4a1e55c4e816?rik=gdawekb25y3Gaw&pid=ImgRaw&r=0", "https://th.bing.com/th/id/R.d34214adb0000b81354a4a1e55c4e816?rik=gdawekb25y3Gaw&pid=ImgRaw&r=0", 
				 "https://th.bing.com/th/id/R.d34214adb0000b81354a4a1e55c4e816?rik=gdawekb25y3Gaw&pid=ImgRaw&r=0", "https://th.bing.com/th/id/R.d34214adb0000b81354a4a1e55c4e816?rik=gdawekb25y3Gaw&pid=ImgRaw&r=0"};
	   	new BoughtItems(prods, links);
    } */
	
}

/**General Commentary:
 * Okay So here, the Check Info Will be different from the ones in Liked And the Menu
 * */


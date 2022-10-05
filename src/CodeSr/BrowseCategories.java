package CodeSr;


import javax.swing.*;
import java.util.*;
import java.awt.*;



public class BrowseCategories extends JFrame {
	private int length = 600;
	private int width = 500;
	JScrollPane scrollpane;
	String categories[];
	LinkedList<JButton> browsButtons;
	LinkedList<JButton> browsButtonsProducts;
	
	public BrowseCategories(String[] categories) {
		
		
		// Calling the super class 
		super ("Browse Categories");
		this.browsButtons = new LinkedList<JButton>();
		this.browsButtonsProducts = new LinkedList<JButton>();
		this.categories = categories;
		setSize(width, length);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Creating the MenuBar and adding components
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
	
	public void FillThePage() {
		
		String titles[] = {"Category Name", "Browse Category", "Check Products"};
		// Declaration of the JPanel class..
        JPanel p = new JPanel();
        p.setBackground(new Color(128,0,0,70));
        // Set size of JFrame.
        p.setSize(600, 900);
 
        // Function to set Layout of JFrame.
        int nbLines = this.categories.length;
        p.setLayout(new GridLayout(nbLines+1, 3, 10, 0));
 
        // for loop
        for (int row = 0; row < nbLines+1; row++) {
        	 for (int col = 0; col < 3; col++){
                  
                 // If condition
                 if (row == 0) {
  
                     // add new Jlabel
                     p.add(new JLabel(titles[col]));
                 }
                 else {
                	 if (col == 0)
                     {
                         // add new Jlabel
                         p.add(new JLabel(this.categories[row - 1]));
                     } 
                	 
                	 else if (col == 1) {
                     	JButton browse = new JButton ("Browse Categoty");
                     	browse.setBackground(new Color(128,0,0,150));
                     	browse.setForeground(Color.black);
                     	browse.setAction(new ActionBrowseSubCat(categories,this.browsButtons,this));
                     	browse.setText("Browse Category");
                     	browse.setActionCommand(this.categories[row - 1]);
                     	this.browsButtons.add(browse);
                     	p.add(browse);
                     } 
                	 
                	 else if (col == 2) {
                		 JButton browse = new JButton ("Browse Products");
                		 browse.setBackground(new Color(128,0,0,150));
                		 browse.setForeground(Color.black);
                      	browse.setAction(new ActionBrowseProducts(categories,this.browsButtonsProducts,this));
                      	browse.setActionCommand(this.categories[row - 1]);
                      	browse.setText("Browse Products");
                      	this.browsButtonsProducts.add(browse);
                      	p.add(browse);
                	 }
                	 
                 }
             }
        }
        
        scrollpane = new JScrollPane(p);
        getContentPane().add(scrollpane, BorderLayout.CENTER);
        
	}
	
		
	
	

//	
//	public static void main(String args[]) {
//		String cats[]= {"cat1", "cat2", "cat3", "cat4", "cat5"};
//	   	new BrowseCategories(cats);
//
//    }
	
}
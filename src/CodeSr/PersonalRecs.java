package CodeSr;

import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class PersonalRecs extends JFrame {
	private int length = 720;
	private int width = 900;
	JScrollPane scrollpane;
	String categories[];
	LinkedList<JButton> browsButtons;
	LinkedList<JButton> browsButtonsProducts;
	
	public PersonalRecs(String categories[]) {
		
		// Calling the super class 
		super ("Browse Personal Recommendations");
		this.categories = categories;
		setSize(width, length);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar(); 
		JMenu m1 = new JMenu("Browse Recommendations");
        //JMenu m2 = new JMenu("Bought Items");
        JMenu m3 = new JMenu ("My Info");
        //JMenu m4 = new JMenu ("Liked Products");
        mb.add(m1);
        //mb.add(m2);
        //mb.add(m4);
        mb.add(m3);
        
        
        getContentPane().add(BorderLayout.NORTH, mb);
        
        FillThePage();
        
        // Make the frame Visible
        setVisible(true); 
		
	}
	
	public void FillThePage() {
		
		String header[] = {"Your", "Personal", "Recommendations"};
		String titles[] = {"Category Name", "Browse Category", "Check Products"};
		// Declaration of the JPanel class..
        JPanel p = new JPanel();
 
        // Set size of JFrame.
        p.setSize(600, 900);
 
        // Function to set Layout of JFrame.
        int nbLines = this.categories.length;
        p.setLayout(new GridLayout(nbLines+2, 3, 10, 0));
 
        // for loop
        for (int row = 0; row < nbLines+2; row++) {
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
	
	
	public static void main(String args[]) {
		String cats[]= {"cat1", "cat2", "cat3", "cat4", "cat5"};
	   	new PersonalRecs(cats);

    }
	
}
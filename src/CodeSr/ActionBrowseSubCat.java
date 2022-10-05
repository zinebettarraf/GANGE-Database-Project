package CodeSr;
import javax.swing.*;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionBrowseSubCat extends AbstractAction {
	JFrame frame;
	String[] categories;
	LinkedList<JButton> browseButtons;
	public ActionBrowseSubCat(String[] categories,LinkedList<JButton> browseButtons,JFrame frame) {
		this.browseButtons=browseButtons;
		this.categories=categories;
		this.frame=frame;
	}
	

	public void actionPerformed(ActionEvent e) {
		 Iterator<JButton>  it= this.browseButtons.iterator();
		 while(it.hasNext()) {
			 JButton button=it.next();
		 if(e.getSource()==button) {
			 String categorie= button.getActionCommand();
			 String[] categories=Categories.categoriesFilles(categorie);
			 if(categories.length==0) {
				 /* check if categorie has product */
				 String[] products=Categories.hasProducts(categorie);
				 if(products.length==0) {
					 /* categorie qui n,a pas de produits*/
					 showMessageDialog(null, "This category has no products  ");
				 }
				 else {
					 showMessageDialog(null, "This category has no more subcategories Please check products  ");
				 }
				 
			 }
			 else {
	             this.frame.setVisible(false);
				 JFrame frame =new BrowseCategories(categories);
			 }

		 }
		 }
	 }
     
	
public static void main(String args[]) {
	String[] categories=Categories.categorieSRoot();
	new BrowseCategories(categories);

}
}
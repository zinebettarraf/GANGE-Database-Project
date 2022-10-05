package CodeSr;
import javax.swing.*;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionBrowseProducts extends AbstractAction {
	JFrame frame;
	String[] categories;
	LinkedList<JButton> browsButtonsProducts;
	public ActionBrowseProducts(String[] categories,LinkedList<JButton> browsButtonsProducts,JFrame frame) {
		this.browsButtonsProducts=browsButtonsProducts;
		this.categories=categories;
		this.frame=frame;
	}
	

	public void actionPerformed(ActionEvent e) {
//		 System.out.println(SignIn.mailConnectedUser);
		 Iterator<JButton>  it= this.browsButtonsProducts.iterator();
		 while(it.hasNext()) {
			 JButton button=it.next();
		 if(e.getSource()==button) {
			 String categorie= button.getActionCommand();
			 String[] sortedProducts=Categories.sortedAllProductsCategory(categorie);
			 
			 int nbrProducts=sortedProducts.length;
			 Produit[] products=new Produit[nbrProducts];
			 if (nbrProducts==0) {
				 showMessageDialog(null,"this category has no products yet");
			 }
			 else {
				 for (int i=0 ;i<nbrProducts;i++) {
					 products[i]=new Produit(sortedProducts[i]);
					 System.out.println(products[i].name);
				 }
				 this.frame.setVisible(false);
				 new ProductsPage(products);
//				 new ProductsPage(products);
			 }
		 }
		 }
	 }
     
	
public static void main(String args[]) {
	String[] categories=Categories.categorieSRoot();
	new BrowseCategories(categories);

}
}
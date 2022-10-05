package CodeSr;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionGoToBoughtItems extends AbstractAction {
	JFrame frame;
	Produit[] products;
	
	public ActionGoToBoughtItems (ProductsPage menu, Produit[] products) {
		super("Bought Items");
		this.frame= menu;
		this.products = products;
	}
	
	public void actionPerformed (ActionEvent e) {
		BoughtItems newFrame = new BoughtItems(this.products);
		frame.setVisible(false);
	}	
}
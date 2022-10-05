package CodeSr;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ActionGoToRecs extends AbstractAction {
	int indice;
	String categories[];
	JFrame menu;
	
	public ActionGoToRecs(JFrame menu, int i) {
		this.indice = i;
		this.menu = menu;
		if (i == 0) {
			//this.categories = allCategories();
			String cats[]= Categories.categorieSRoot();
			this.categories = cats;
		}
		if (i == 1) {
			String cats[]=Categories.personalRecomendedCategories(Login.mailConnectedUser);
			this.categories = cats;
		}
		if (i == 2) {
			//this.categories = TrendingRecommended();
			String cats[]= Categories.trendingCategories();
			this.categories = cats;
		}
	}
	
	public void actionPerformed (ActionEvent e) {
		if (this.indice == 0) {
			new BrowseCategories(this.categories);
		}
		if (this.indice == 1) {
			new BrowseCategories(this.categories);
		}
		if (this.indice ==2) {
			new BrowseCategories(this.categories);
		}
		
		this.menu.setVisible(false);
	}
	
}
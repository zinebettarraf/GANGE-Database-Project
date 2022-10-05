package CodeSr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionGoToPersonalInfo extends AbstractAction {
	
	//String Client[];
	JFrame menu;
	
	public ActionGoToPersonalInfo (JFrame menu){
		super ("My Info");
		this.menu = menu;
	}
	
	public void actionPerformed (ActionEvent e) {
		
		Client client=new Client(Login.mailConnectedUser);
		//Client client=new Client("antoin@gmail.com");
		PersonalInfo newPage = new PersonalInfo(client);

		this.menu.setVisible(false);
	}

}
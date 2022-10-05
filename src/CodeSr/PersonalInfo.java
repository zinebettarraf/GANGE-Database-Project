package CodeSr;

import javax.swing.*;

import CodeSr.BoughtItems.ActionGetInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalInfo extends JFrame implements ActionListener{
	private int length = 520;
	private int width = 600;
	JScrollPane scrollpane;
	Client client;
	JButton delete;
	
	public PersonalInfo(Client client) {
		// Calling the super class 
		super("Your Personal Info");
		this.client=client;
		
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
	public void FillThePage() {
		
		//A GRID showing the Client's : Name / Last Name / Mail / Mot de Passe / Adresse Postale
		String Client[] = {this.client.prenom, this.client.nom, this.client.mail, this.client.motdepasse, this.client.adresse};
		
		JPanel p = new JPanel();
		JScrollPane scrollpane;
		p.setSize(400, 300);
		
		int nbLines = 6;
		p.setLayout(new GridLayout(nbLines, 2, 10, 0));
		
		//for loop
		for (int row = 0; row < nbLines; row++) {
			for (int col = 0; col < 2; col++) {
				if (row == 0) {
					if (col == 0) {
						JLabel text = new JLabel("Name");
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						text.setForeground(Color.RED);
						p.add(text);
					}
					
					if (col == 1) {
						String name = Client[row];
						JLabel text = new JLabel(name);
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						p.add(text);
					}
				}
				
				if (row == 1) {
					if (col == 0) {
						JLabel text = new JLabel("Last Name");
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						text.setForeground(Color.RED);
						p.add(text);
					}
					
					if (col == 1) {
						String name =  Client[row];
						JLabel text = new JLabel(name);
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						p.add(text);
					}
				}
				if (row == 4) {
					if (col == 0) {
						JLabel text = new JLabel("Adresse Postale");
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						text.setForeground(Color.RED);
						p.add(text);
					}
					
					if (col == 1) {
						String adresse =  Client[row];
						JLabel text = new JLabel(adresse);
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						p.add(text);
					}
				}
				if (row == 2) {
					if (col == 0) {
						JLabel text = new JLabel("Adresse Mail");
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						text.setForeground(Color.RED);
						p.add(text);
					}
					
					if (col == 1) {
						String adresse =  Client[row];
						JLabel text = new JLabel(adresse);
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						p.add(text);
					}
				}
				if (row == 3) {
					if (col == 0) {
						JLabel text = new JLabel("Mot de Passe");
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						text.setForeground(Color.RED);
						p.add(text);
					}
					
					if (col == 1) {
						String adresse =  Client[row];
						JLabel text = new JLabel(adresse);
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						p.add(text);
					}
				}
				if (row == 5) {
					if (col == 0) {
						JLabel text = new JLabel("Delete Acount");
						text.setFont ( new Font("Serif", Font.BOLD, 30));
						text.setForeground(Color.RED);
						p.add(text);
					}
					
					if (col == 1) {
						this.delete = new JButton ("Delete Account");
                    	delete.addActionListener(this);
                    	delete.setText("Delete Account");
                    	p.add(delete);
						p.add(delete);
					}
				}
			}
		}
		
		scrollpane = new JScrollPane (p);
		//p.add(scrollpane, BorderLayout.CENTER);
		getContentPane().add(scrollpane, BorderLayout.CENTER);
	}
	
	
//	public class ActionDeleteAccount extends AbstractAction{
//		String mail;
//		int 
//		public ActionDeleteAccount(String email,int alerId) {
//			this.mail = email;
//			
//		}
	   @Override
	   public void actionPerformed(ActionEvent e) {
			if(e.getSource()==delete) {
				JOptionPane.showMessageDialog(null, "Your Account has been successfully deleted!");
			    Categories.deleteClientAccount(Login.mailConnectedUser);
			    this.setVisible(false);
				new Login();
			}
	   }

	} 
	
	
//	public static void main(String args[]) {
//	   	new PersonalInfo(new Client("antoin@gmail.com"));
//
//    }
//	
	


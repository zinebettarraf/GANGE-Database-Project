package CodeSr;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.*;
import java.sql.*;
import java.awt.image.BufferedImage;
import java.net.URL; 
import static javax.swing.JOptionPane.showMessageDialog;


public class SignUp extends JFrame implements ActionListener{
	
	private JButton createAccount;
	private JPasswordField PasswordTextFeild;
	private JTextField mailTextField;
	private JTextField nomTextField;
	private JTextField prenomTextField;
	private JTextField adresseTextField;

    
	

	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "echarifs";
    static final String PASSWD = "echarifs";
	
	public SignUp() {
		

        this.setTitle("Create Account");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 600));
        this.setResizable(false);
        this.setLayout(null);     
      BufferedImage image=null;
      try {
      	URL url=new URL("https://content.skyscnr.com/m/36feac5c9f3053bf/original/shutterstock_1403326268.jpg");
      	image=ImageIO.read(url);
          this.setContentPane(new JLabel(new ImageIcon(image)));
      } catch (IOException e) {
          e.printStackTrace();
      }
        
      /*  Nom d'utilisateur */
      JLabel nomLabel = new JLabel("Nom");
      this.nomTextField = new JTextField();
      nomLabel.setBounds(50, 50, 100, 30);
      nomTextField.setBounds(200, 50, 250, 30);
      nomTextField.setBackground(new Color(250,250,250,100));
      nomLabel.setForeground(new Color(150,0,0,190));
      this.add(nomLabel);
      this.add(nomTextField);
      
      /* Prenom d'utilisateur*/
      JLabel prenomLabel = new JLabel("Prenom");
      this.prenomTextField = new JTextField();
      prenomLabel.setBounds(50, 120, 100, 30);
      prenomTextField.setBounds(200, 120, 250, 30);
      prenomTextField.setBackground(new Color(250,250,250,100));
      prenomLabel.setForeground(new Color(150,0,0,190));
      this.add(prenomLabel);
      this.add(prenomTextField);
      
      /*  Mail d'utilisateur*/
      JLabel mailLabel = new JLabel("Mail");
      this.mailTextField = new JTextField();
      mailLabel.setBounds(50, 190, 100, 30);
      mailTextField.setBounds(200, 190, 250, 30);
      mailTextField.setBackground(new Color(250,250,250,100));
      mailLabel.setForeground(new Color(150,0,0,190));
      this.add(mailLabel);
      this.add(mailTextField);
        
        /*  Adresse d'utilisateur*/
        JLabel adresseLabel = new JLabel("Adresse Postale");
        this.adresseTextField = new JTextField();
        adresseLabel.setBounds(50, 260, 150, 30);
        adresseTextField.setBounds(200, 260, 250, 30);
        adresseTextField.setBackground(new Color(250,250,250,100));
        adresseLabel.setForeground(new Color(150,0,0,190));
        this.add(adresseLabel);
        this.add(adresseTextField);
  
     
        
        /* passewoord */
        
        JLabel passwordLabel = new JLabel("Password");
        this.PasswordTextFeild = new JPasswordField();
        passwordLabel.setBounds(50, 330, 100, 30);
        PasswordTextFeild.setBounds(200, 330, 250, 30);
        PasswordTextFeild.setBackground(new Color(250,250,250,100));
        passwordLabel.setForeground(new Color(150,0,0,190));
        this.add(passwordLabel);
        this.add(PasswordTextFeild);
        
        /* LOGGIN BUTTON */
        this.createAccount = new JButton("Create Account");
        createAccount.setBounds(150, 450, 200, 30);
        createAccount.setBackground(new Color(128,0,0,150));
        createAccount.setForeground(Color.black);
        createAccount.addActionListener(this); 
        this.add(createAccount);
        
        /**/
        this.setVisible(true);
        
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==createAccount) {
			String nom=nomTextField.getText();
			String prenom=prenomTextField.getText();
			String mail=mailTextField.getText();
			String adresse=adresseTextField.getText();
			String password=new String(PasswordTextFeild.getPassword());
			if(!(nom.isEmpty() && prenom.isEmpty() && mail.isEmpty() && password.isEmpty())) {		
				try {
		        	// connexion à la base de données
		        	
		            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
		            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection
		            connexion.setAutoCommit(false);
		            connexion.setTransactionIsolation(connexion.TRANSACTION_SERIALIZABLE);
		            
		            // Creation de la requete
		            
		            //nbr des comptes client
		         
		            PreparedStatement query1=connexion.prepareStatement("SELECT MAX(IDUTILISATEUR) FROM CLIENT");
		            ResultSet res = query1.executeQuery();
		            res.next();
		            int max=res.getInt(1);
//		            System.out.println(accountNbr);
                    String ID=String.valueOf(max+1);
//                    System.out.println(ID);
               

		            //insert dans client 
		            PreparedStatement query4=connexion.prepareStatement("SELECT MAIL FROM CLIENT WHERE MAIL=? ");
		            query4.setString(1, mail);
		            ResultSet res4=query4.executeQuery();
		            
		            if (res4.next()) {
		            	showMessageDialog(null,"Already existing account \n Please sign up with an other  mail");
		            }
		            else {
			            
			            PreparedStatement query2=connexion.prepareStatement("INSERT INTO UTILISATEUR VALUES (?)");
			            query2.setString(1,ID );
			            query2.executeQuery();
			            
			            PreparedStatement query3=connexion.prepareStatement("INSERT INTO CLIENT VALUES (?,?,?,?,?,?)");
			            query3.setString(1, mail);
			            query3.setString(2, password);
			            query3.setString(3, nom);
			            query3.setString(4, prenom);		            
			            query3.setString(5, adresse);
			            query3.setString(6,ID);
			            query3.executeQuery();
			           

		            }

//
//		             // Fermeture 
		        
		            connexion.commit();
		            connexion.close();
		            this.setVisible(false);
		            new Login();

		        } catch (SQLException e) {
		            System.err.println("failed");
		            e.printStackTrace(System.err);
		        }
			}
			
			else {
				showMessageDialog(null, "Il faut remplir toutes les cases s'il vous plaît !!! ");
			}
			}

		
	}





}


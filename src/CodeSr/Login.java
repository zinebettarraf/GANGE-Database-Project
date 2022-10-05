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
 

public class Login extends JFrame implements ActionListener{
	
	private JButton loginButton;
	private JButton SignInButton;
	private JPasswordField PasswordTextFeild;
	private JTextField MailTextField;
	


	

    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "echarifs";
    static final String PASSWD = "echarifs";
    static String mailConnectedUser;
	static int bidParameter = 5;
	public Login() {
	    //this.bidParameter=5;
        this.setTitle("Bid Shopping");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 600));
        this.setResizable(false);
        this.setLayout(null);
        this.mailConnectedUser="";
      BufferedImage image=null;
      try {
      	URL url=new URL("https://apicms.thestar.com.my/uploads/images/2021/02/23/1053702.jpg");
      	image=ImageIO.read(url);
          this.setContentPane(new JLabel(new ImageIcon(image)));
      } catch (IOException e) {
          e.printStackTrace();
      }
        

        
        /*  user mail*/
        JLabel mailLabel = new JLabel("Mail");
        this.MailTextField = new JTextField();
        mailLabel.setBounds(50, 150, 100, 30);
        MailTextField.setBounds(150, 150, 250, 30);
        MailTextField.setBackground(new Color(250,250,250,100));
        mailLabel.setForeground(Color.black);
        this.add(mailLabel);
        this.add(MailTextField);
  
       
        
        
        /* passewoord */
        
        JLabel passwordLabel = new JLabel("Password");
        this.PasswordTextFeild = new JPasswordField();
        passwordLabel.setBounds(50, 220, 100, 30);
        PasswordTextFeild.setBounds(150, 220, 250, 30);
        PasswordTextFeild.setBackground(new Color(250,250,250,100));
        passwordLabel.setForeground(Color.black);
        this.add(passwordLabel);
        this.add(PasswordTextFeild);
        
        /* LOGGIN BUTTON */
        this.loginButton = new JButton("Login");
        loginButton.setBounds(200, 300, 100, 30);
        loginButton.setBackground(new Color(128,0,0,150));
        loginButton.setForeground(Color.black);
        loginButton.addActionListener(this);
        this.add(loginButton);
        
        
        /* Sign in BUTTON */
        this.SignInButton = new JButton("Sign Up");
        SignInButton.setBounds(200, 350, 100, 30);
        SignInButton.setBackground(new Color(128,0,0,150));
        SignInButton.setForeground(Color.black);
        SignInButton.addActionListener(this);
        this.add(SignInButton);
        
        /**/
        this.setVisible(true);
        
	}
	
	
	
	public static int getBidParameter() {
		return bidParameter;
	}



	public static void setBidParameter(int bidParameter) {
		Login.bidParameter = bidParameter;
	}



	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==loginButton) {
			String mail=MailTextField.getText();
			String password=new String(PasswordTextFeild.getPassword());
			this.mailConnectedUser=mail;
			try {
	        	// connexion à la base de données
	        	
	            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());   // Enregistrement du driver Oracle
	            Connection connexion = DriverManager.getConnection(CONN_URL, USER, PASSWD);  // Etablissement de la connection

	            
	            // Creation de la requete
	            PreparedStatement query=connexion.prepareStatement("SELECT NOM FROM CLIENT WHERE MAIL=? AND MOTDEPASSE=?");
	            query.setString(1, mail);
	            query.setString(2, password);
	            ResultSet res=query.executeQuery();
	            if(res.next()) {
	            	/* utilisateur exist*/
	            	this.setVisible(false);
	        		Produit pro1=new Produit("53");
	        		Produit pro2=new Produit("51");
	        		Produit pro3=new Produit("59");
	        		Produit pro4=new Produit("80");
	        		Produit[] productsList=new Produit[] {pro1,pro2,pro3,pro4};
	        	   	new ProductsPage(productsList);	            	
	            }
	            else {
	            	showMessageDialog(null, "Oups !! identifiants incorrects ");
	            }


	             // Fermeture 
	            res.close();
	            query.close();
	            connexion.close();

	        } catch (SQLException e) {
	            System.err.println("failed");
	            e.printStackTrace(System.err);
	        }
			
			}
		if(event.getSource()==SignInButton) {
			this.setVisible(false);
			new SignUp();
		}

		
	}



}

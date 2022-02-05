package UI;

import javax.swing.*;
import databasesquared.services.LoginRegister;

import CRUD.FullCRUD;

import java.awt.*;  
import java.awt.event.*;  
import java.lang.Exception;
import java.sql.Date;
import java.util.Calendar;  

class UILogin extends JFrame
{  
	//DB Interaction
	FullCRUD fc;
    LoginRegister lr;
	
	//UI Fields
    JButton loginButton, registerButton;   
    JTextField  UsernameField, PasswordField;  
      
    UILogin(FullCRUD fc, LoginRegister lr)  
    {     
    	//Make the UI look okay
    	try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }    
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }   
    	
    	//Save DB interaction & set screen size
    	this.fc = fc;
    	this.lr = lr;
    	this.setSize(300,200);

        //Create the panel for user input
        JPanel inputPanel = new JPanel(new GridLayout(2,2));
        
        //Create Username & Password Boxes
        JLabel userLabel = new JLabel();  
        userLabel.setText("Username");
        UsernameField = new JTextField(UIMain.UNAME_MAX_LENGTH);
        JLabel passLabel = new JLabel();  
        passLabel.setText("Password");
        PasswordField = new JPasswordField(UIMain.PASS_MAX_LENGTH);
        
        //Add username & password boxes to panel
        inputPanel.add(userLabel);
        inputPanel.add(UsernameField);
        inputPanel.add(passLabel);
        inputPanel.add(PasswordField);
        
        //Create the panel for the title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Login as Reviewer");
        titlePanel.add(titleLabel);
        
        //Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));  
        loginButton = new JButton("LOGIN");
        registerButton = new JButton("REGISTER");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        //Add all our panels to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
             
        //Add actionlisteners to our puttons
        loginButton.addActionListener(new LoginListener());
        registerButton.addActionListener(new RegisterListener());
        
        //set title of the window
        setTitle("LOGIN FORM");         
    }
 
      
    //Button Actions
    
    //Login button Action
    class LoginListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		//Attempt login
    		int login = lr.login(UsernameField.getText(), PasswordField.getText());
    		
    		//If login successful, open reviewer panel
    		if(login == 0)
    		{
    			UIReviewer ui = new UIReviewer(fc, UsernameField.getText());
    			ui.setVisible(true);
    			
    			//Get rid of this window
    			setVisible(false);
    		}
    	}
    }
    
    //Register button Action
    class RegisterListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		
    		//Get Years of Experience
    		String input = JOptionPane.showInputDialog("How Many Years of Experience do you have");
    		int yoe = -1;
    		
    		try {
    			yoe = Integer.parseInt(input);
    		}
    		catch(Exception E) {
    			//They entered a invalid # of years
    		}
    		
    		if(yoe > -1)
    		{
    			//Find the year they started working
	    		int thisYear = new Date(Calendar.getInstance().getTimeInMillis()).getYear();
	    		int yearStarted = thisYear-yoe;
	    		
	    		//Add the reviewer
	    		fc.addReviewer(UsernameField.getText(), new Date(thisYear, 1, 1));
	    		
	    		//Register the reviewer
	    		int success = lr.register(UsernameField.getText(), PasswordField.getText());
	    		
	    		//If registration was sucessful, login the reviewer
	    		if(success == 0)
	    		{
	    			UIReviewer ui = new UIReviewer(fc, UsernameField.getText());
	        		ui.setVisible(true);
	        		setVisible(false);
	    		}
    		}
    	}
    }

}  
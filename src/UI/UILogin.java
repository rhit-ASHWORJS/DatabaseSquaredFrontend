package UI;

import javax.swing.*;
import databasesquared.services.LoginRegister;

import CRUD.FullCRUD;

import java.awt.*;  
import java.awt.event.*;  
import java.lang.Exception;
import java.sql.Date;  

class UILogin extends JFrame
{  
    JButton loginButton, registerButton;   
    JTextField  UsernameField, PasswordField;  
    FullCRUD fc;
    LoginRegister lr;
      
    UILogin(FullCRUD fc, LoginRegister lr)  
    {     
    	
    	this.fc = fc;
    	this.lr = lr;
    	this.setSize(300,200);

        //Create the panel for user input
        JPanel inputPanel = new JPanel(new GridLayout(2,2));
        JLabel userLabel = new JLabel();  
        userLabel.setText("Username");
        UsernameField = new JTextField(UIMain.UNAME_MAX_LENGTH);
        JLabel passLabel = new JLabel();  
        passLabel.setText("Password");
        PasswordField = new JPasswordField(UIMain.PASS_MAX_LENGTH);
        
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
        
        //Add our panels to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
             
        loginButton.addActionListener(new LoginListener());
        registerButton.addActionListener(new RegisterListener());
        setTitle("LOGIN FORM");         //set title to the login form  
    }
    
    public void setVisibility(boolean visible)
    {
    	this.setVisible(visible);
    }
      
    class LoginListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		
    		UIReviewer ui = new UIReviewer(fc, UsernameField.getText());
    		ui.setVisibility(true);
    		setVisibility(false);
    	}
    }
    
    class RegisterListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		//Register the user
    		fc.addReviewer(UsernameField.getText(), new Date(22));
    		int success = lr.register(UsernameField.getText(), PasswordField.getText());
    		System.out.println(UsernameField.getText());
    		System.out.println(PasswordField.getText());
    		
    		if(success == -1)
    		{
    			JOptionPane.showMessageDialog(null, "Registration Failed.");
    		}
    		else
    		{
    			UIReviewer ui = new UIReviewer(fc, UsernameField.getText());
        		ui.setVisibility(true);
        		setVisibility(false);
    		}
    	}
    }

}  
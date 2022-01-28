package UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CRUD.FullCRUD;

public class UIMain {
	public static final int UNAME_MAX_LENGTH = 20;
	public static final int PASS_MAX_LENGTH = 20;
	
	
	FullCRUD fc;
	
	public UIMain(FullCRUD fc)
	{
		this.fc = fc;
	}
	
	public void beginUI()
	{
		//There's two different paths a user can take here: Guest (no login) or Reviewer (login)
		//We start by asking if they want to login as a reviewer
		
		int result = JOptionPane.showConfirmDialog(null, "Would you like to login as a Reviewer?");
		
		switch (result) {
	        case JOptionPane.YES_OPTION:
	        	System.out.println("Continue as Reviewer");
	        	UILogin login = new UILogin(fc);
	        	login.setVisibility(true);
	        break;
	        case JOptionPane.NO_OPTION:
	        	System.out.println("Continue as Guest");
	        	UIGuest guest = new UIGuest(fc);
	        	guest.setVisibility(true);
	        break;
	        case JOptionPane.CANCEL_OPTION:
	        	System.out.println("Cancelled the option pain");
	        break;
	        case JOptionPane.CLOSED_OPTION:
	        	System.out.println("Closed the option pane");
	        break;
		}
	}
}

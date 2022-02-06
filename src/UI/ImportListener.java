package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ImportListener  implements ActionListener {
	JFrame frame;
	File f;
	public ImportListener(JFrame frame, File f)
	{
		this.frame = frame;
		this.f = f;
	}
	
	public void setFile(File f)
	{
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("this worked");
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this.frame);



        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            //This is where a real application would open the file.

            System.out.println("Opening: " + file.getName() + "." );
            try {
				Scanner reader = new Scanner(file);
				while(reader.hasNext()) {
					System.out.println(reader.nextLine());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            

        } else {

          System.out.println("error");

        }
	}
}

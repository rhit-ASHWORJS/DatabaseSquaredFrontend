package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


import CRUD.FullCRUD;

public class ImportListener  implements ActionListener {
	JFrame frame;
	private FullCRUD fc = null;
	public ImportListener(JFrame frame, FullCRUD fc)
	{
		this.frame = frame;
		this.fc = fc;
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
					String nl = reader.nextLine();
					System.out.println(nl);
					
					String[] vals  = nl.split(",", -1);
					this.fc.addImportRow(vals[0],vals[1],vals[2],Date.valueOf(vals[3]),Integer.parseInt(vals[4]),vals[5],Integer.parseInt(vals[6]),Date.valueOf(vals[7]),vals[8],vals[9],Date.valueOf(vals[10]),vals[11],Integer.parseInt(vals[12]),Date.valueOf(vals[13]));
					
					
				}
				reader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            

        } else {

          System.out.println("error");

        }
	}
}

package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

class CSVListener implements ActionListener {
		JFrame frame;
		JTable dataTable;
		public CSVListener(JFrame frame, JTable dataTable)
		{
			this.frame = frame;
			this.dataTable = dataTable;
		}
		
		public void setTable(JTable dataTable)
		{
			this.dataTable = dataTable;
		}
	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			PrintWriter csvWriter;
			JFileChooser filec = new JFileChooser();

			int success = filec.showOpenDialog(frame);

			if (success == JFileChooser.APPROVE_OPTION) {
				File file = filec.getSelectedFile();
				try {
					csvWriter = new PrintWriter(file);

					String[] headers = new String[dataTable.getColumnCount()];
					String[][] data = new String[dataTable.getRowCount()][dataTable.getColumnCount()];

					for (int i = 0; i < dataTable.getColumnCount(); i++) {
						headers[i] = dataTable.getColumnName(i);
					}

					for (int i = 0; i < dataTable.getRowCount(); i++) {
						for (int j = 0; j < dataTable.getColumnCount(); j++) {
							try {
								data[i][j] = dataTable.getValueAt(i, j).toString();
							} catch (Exception e) {
								data[i][j] = "N/A";
							}
						}
					}

					for (String header : headers) {
						csvWriter.print(header + ",");
					}
					csvWriter.print("\n");

					for (String[] row : data) {
						for (String cell : row) {
							csvWriter.print(cell + ",");
						}
						csvWriter.print("\n");
					}

					csvWriter.flush();
					csvWriter.close();

					JOptionPane.showMessageDialog(null, "Finished Writing file " + file.getAbsolutePath());

				} catch (FileNotFoundException e) {
					System.out.println("Could not open file");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Operation Cancelled");
			}
		}
	}
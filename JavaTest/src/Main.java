import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;


public class Main {

	private JFrame frmMaze;
	private JTextField txtn;
	private JTextField txtm;
	private JTextArea display;
	private JCheckBox debug;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmMaze.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMaze = new JFrame();
		frmMaze.setResizable(false);
		frmMaze.setTitle("Maze");
		frmMaze.setBounds(100, 100, 871, 704);
		frmMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMaze.getContentPane().setLayout(null);

		JLabel lblPleaseEnterThe = new JLabel("Please enter the sizes to create a maze (nXm)");
		lblPleaseEnterThe.setBounds(24, 13, 291, 16);
		frmMaze.getContentPane().add(lblPleaseEnterThe);

		txtn = new JTextField();
		txtn.setBounds(398, 10, 56, 22);
		frmMaze.getContentPane().add(txtn);
		txtn.setColumns(10);

		txtm = new JTextField();
		txtm.setBounds(398, 39, 56, 22);
		frmMaze.getContentPane().add(txtm);
		txtm.setColumns(10);

		JLabel lblEnterN = new JLabel("Enter n:");
		lblEnterN.setBounds(322, 13, 71, 16);
		frmMaze.getContentPane().add(lblEnterN);

		JLabel lblEnterM = new JLabel("Enter m:");
		lblEnterM.setBounds(322, 42, 56, 16);
		frmMaze.getContentPane().add(lblEnterM);

		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n, m;
				boolean deb = debug.isSelected();
				String output = "";
				try{
					n = Integer.parseInt(txtn.getText());
					m = Integer.parseInt(txtm.getText());
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Please enter integers for input");
				}
				//Maze myMaze = new Maze(5,5,true);
				display.setText(output);
			}
		});
		btnCreate.setBounds(499, 9, 97, 52);
		frmMaze.getContentPane().add(btnCreate);

		display = new JTextArea();
		display.setFont(new Font("Consolas", Font.PLAIN, 15));
		display.setEditable(false);
		display.setBounds(24, 99, 813, 525);
		frmMaze.getContentPane().add(display);
		
		JLabel lblDebugOn = new JLabel("Debug on:");
		lblDebugOn.setBounds(141, 42, 82, 16);
		frmMaze.getContentPane().add(lblDebugOn);
		
		debug = new JCheckBox("Yes");
		debug.setBounds(233, 38, 82, 25);
		frmMaze.getContentPane().add(debug);
		
	}
}

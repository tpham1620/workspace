/**
 * Main class for maze generator project
 * @author Tan Pham
 * Created: 03/12/2015
 * **************************************************************************************************************************
 * This class create a graphic window (GUI) which display the solution of the profect.
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
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
		frmMaze.setTitle("Maze");
		frmMaze.setBounds(100, 100, 871, 704);
		frmMaze.setResizable(false);
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

		/*
		 * when create button is click, read in the input and create the maze and output.
		 * If the input is invalid, ask user to re-enter the inputs.
		 */
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = 0, m = 0;
				boolean deb = debug.isSelected();
				try{
					n = Integer.parseInt(txtn.getText());
					m = Integer.parseInt(txtm.getText());
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Please enter integers for input");
				}
				Maze myMaze = new Maze(n,m,deb);
				display.setText(myMaze.output);
			}
		});
		btnCreate.setBounds(499, 9, 97, 52);
		frmMaze.getContentPane().add(btnCreate);

		display = new JTextArea();
		display.setFont(new Font("Consolas", Font.PLAIN, 15));
		display.setEditable(false);
		display.setBounds(24, 99, 813, 525);
		frmMaze.getContentPane().add(display);
		
		JScrollPane scroll = new JScrollPane(display);
		scroll.setBounds(24, 99, 813, 525);
		scroll.setVisible(true);
		frmMaze.add(scroll);
		
		JLabel lblDebugOn = new JLabel("Debug on:");
		lblDebugOn.setBounds(141, 42, 82, 16);
		frmMaze.getContentPane().add(lblDebugOn);
		
		debug = new JCheckBox("Yes");
		debug.setBounds(233, 38, 82, 25);
		frmMaze.getContentPane().add(debug);
		
	}
}

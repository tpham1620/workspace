import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AddDriver {

	private JFrame frmCreateNewDriver;
	private JTextField newDriverPwd;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddDriver window = new AddDriver();
					window.frmCreateNewDriver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddDriver() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreateNewDriver = new JFrame();
		frmCreateNewDriver.setResizable(false);
		frmCreateNewDriver.setTitle("Create new driver");
		frmCreateNewDriver.setBounds(100, 100, 300, 250);
		frmCreateNewDriver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreateNewDriver.getContentPane().setLayout(null);
		
		JLabel lblPleaseEnter = new JLabel("Please enter your password");
		lblPleaseEnter.setBounds(30, 30, 200, 30);
		frmCreateNewDriver.getContentPane().add(lblPleaseEnter);
		
		newDriverPwd = new JTextField();
		newDriverPwd.setBounds(30, 60, 250, 30);
		frmCreateNewDriver.getContentPane().add(newDriverPwd);
		newDriverPwd.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegister.setBounds(30, 170, 100, 25);
		frmCreateNewDriver.getContentPane().add(btnRegister);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(150, 170, 100, 25);
		frmCreateNewDriver.getContentPane().add(btnCancel);
		
		JLabel lblPhone = new JLabel("Phone #:");
		lblPhone.setBounds(30, 100, 60, 20);
		frmCreateNewDriver.getContentPane().add(lblPhone);
		
		textField = new JTextField();
		textField.setBounds(30, 130, 250, 30);
		frmCreateNewDriver.getContentPane().add(textField);
		textField.setColumns(10);
	}
}

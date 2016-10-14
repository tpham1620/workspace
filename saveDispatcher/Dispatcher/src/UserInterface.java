
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import model.Dispatcher;
import model.Driver;
import model.GPS;
import model.Job;


public class UserInterface {

	protected static Dispatcher dispatcher;
	private Job selectedJob;
	private Driver closestDriver;

	private JFrame frmDispatcher;
	private JTextField txtJobID;
	private JTextField txtJobName;
	private JTextField txtPickLoc;
	private JTextField txtDropLoc;
	private JTextField txtDriverID;
	private JTextField txtDriverName;
	private JTextField txtDriverLoc;
	private JTextField txtDriverPhone;
	private JTextField txtJobInput;
	private static JTextArea txtJobList;
        
          /** MySQL connection to Wine DB*/
    public static Connection connect = null;

	/**
	 * Launch the application.
     * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
                    try {
                        UserInterface window = new UserInterface();
                        window.frmDispatcher.setVisible(true);
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                });
	}

	/**
	 * Create the application.
	 */
	public UserInterface() {
		dispatcher = new Dispatcher();
		loadDispatcher();
		initialize();
		
	}
        
        /** Connect to MySQL driver. */
	public static void connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}
	
	/** Connect to MySQL database. */
	public static void connectionToMySQL() {
		connection();
		String host = "jdbc:mysql://localhost/dispatchdb";
		String username = "root";
		String password = "Nguyentan1620";
		try {
			connect = DriverManager.getConnection(host, username, password);		
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createFrame();
		displayJobList();
	}
	
	private void loadDispatcher(){           
             
            //get the list of VINTNERS from the DB
			try {
			Statement stmt1 = connect.createStatement();
			ResultSet onduty = stmt1.executeQuery("select * from drivers where drivers.status = 'wait';");
			while (onduty.next()) {
                            Driver d1 = dispatcher.createDriver(onduty.getInt("id"),onduty.getString(2), onduty.getString(3), onduty.getString(4));
                    d1.setCurrentLocation(new GPS(47.2067113, -122.4135743));
                        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Driver d2 = dispatcher.createDriver("123", "d1", "123");
		d2.setCurrentLocation(new GPS(47.2217113, -122.3935743));
		Driver d3 = dispatcher.createDriver("123", "d1", "123");
		d3.setCurrentLocation(new GPS(47.0167113, -122.5035743));
		Driver d4 = dispatcher.createDriver("123", "d1", "123");
		d4.setCurrentLocation(new GPS(47.5167113, -122.5035743));
		Driver d5 = dispatcher.createDriver("123", "d1", "123");
		d5.setCurrentLocation(new GPS(47.0167113, -122.2035743));
		dispatcher.addDriver(d1, dispatcher.getOndutyDriver());
		dispatcher.addDriver(d2, dispatcher.getOndutyDriver());
		dispatcher.addDriver(d3, dispatcher.getOndutyDriver());
		dispatcher.addDriver(d4, dispatcher.getOndutyDriver());
		dispatcher.addDriver(d5, dispatcher.getOndutyDriver());
		
		Job j1 = dispatcher.createJob("j1", new GPS(47.2367113, -122.1135743), new GPS(47.2967113, -122.4835743));
		Job j2 = dispatcher.createJob("j1", new GPS(47.1367113, -122.1155743), new GPS(47.2067113, -122.3735743));
		Job j3 = dispatcher.createJob("j1", new GPS(47.3367113, -122.2135743), new GPS(47.1567113, -122.3535743));
		Job j4 = dispatcher.createJob("j1", new GPS(47.2217113, -122.2335743), new GPS(47.1567113, -122.5435743));
		dispatcher.addJob(j1, dispatcher.getUnassignedJob());
		dispatcher.addJob(j2, dispatcher.getUnassignedJob());
		dispatcher.addJob(j3, dispatcher.getUnassignedJob());
		dispatcher.addJob(j4, dispatcher.getUnassignedJob());
		
		
	}
			
	protected static void displayJobList(){
		txtJobList.setText(dispatcher.unassignedJobToString());
	}

	private void locateJob(){
		int selectedID = Integer.parseInt(txtJobInput.getText());
		selectedJob = dispatcher.getJob(selectedID, dispatcher.getUnassignedJob());
		if(selectedJob!=null){
			txtJobID.setText(selectedJob.getJobID() + "");
			txtJobName.setText(selectedJob.getJobName());
			txtPickLoc.setText(selectedJob.getPickLocation().toString());
			txtDropLoc.setText(selectedJob.getDropLocation().toString());	
		}
	}

	private void locateClosestDriver(Job job){
		closestDriver = dispatcher.findClosestDriver(job);
		if(closestDriver!=null){
			txtDriverID.setText(closestDriver.getDriverID() + "");
			txtDriverName.setText(closestDriver.getDriverName());
			txtDriverLoc.setText(closestDriver.getCurrentLocation().toString());
			txtDriverPhone.setText(closestDriver.getPhoneNumer());
		}
	}

	private void assign(){
		if(selectedJob!=null && closestDriver!=null){
			dispatcher.assignJob(selectedJob, closestDriver);
			displayJobList();
			clear();
			JOptionPane.showMessageDialog(null, "Assign job successful");
		}
	}
	
	
	private void clear(){
		txtJobID.setText("");
		txtJobName.setText("");
		txtPickLoc.setText("");
		txtDropLoc.setText("");
		txtDriverID.setText("");
		txtDriverName.setText("");
		txtDriverLoc.setText("");
		txtDriverPhone.setText("");
		txtJobInput.setText("");
	}
	
	private void createFrame(){
		frmDispatcher = new JFrame();
		frmDispatcher.setTitle("Dispatcher");
		frmDispatcher.setResizable(false);
		frmDispatcher.setBounds(100, 100, 1300, 700);
		frmDispatcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmDispatcher.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem mntmAddDriver = new JMenuItem("Add Driver");
		mnMenu.add(mntmAddDriver);

		JMenuItem mntmAddJob = new JMenuItem("Add Job");
		mnMenu.add(mntmAddJob);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnMenu.add(mntmExit);
		frmDispatcher.getContentPane().setLayout(null);

		JButton btnAddJob = new JButton("Add Job");
		btnAddJob.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				AddJob newJob = new AddJob();
				newJob.getFrameAddJob().setVisible(true);
				newJob.getFrameAddJob().setAlwaysOnTop(true);				
			}
		});
		btnAddJob.setBounds(750, 200, 150, 50);
		frmDispatcher.getContentPane().add(btnAddJob);

		JButton btnRemoveJob = new JButton("Remove Job");
		btnRemoveJob.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				dispatcher.removeJob(selectedJob.getJobID(), dispatcher.getUnassignedJob());
			}
		});
		btnRemoveJob.setBounds(750, 125, 150, 50);
		frmDispatcher.getContentPane().add(btnRemoveJob);

		JButton btnAssignJob = new JButton("Assign Job");
		btnAssignJob.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				assign();
			}
		});
		btnAssignJob.setBounds(750, 50, 150, 50);
		frmDispatcher.getContentPane().add(btnAssignJob);

		JLabel lblJobId = new JLabel("Job ID:");
		lblJobId.setBounds(950, 50, 50, 30);
		frmDispatcher.getContentPane().add(lblJobId);

		txtJobID = new JTextField();
		txtJobID.setEditable(false);
		txtJobID.setBounds(1050, 50, 200, 30);
		frmDispatcher.getContentPane().add(txtJobID);
		txtJobID.setColumns(10);

		JLabel lblJobName = new JLabel("Job Name:");
		lblJobName.setBounds(950, 100, 75, 30);
		frmDispatcher.getContentPane().add(lblJobName);

		txtJobName = new JTextField();
		txtJobName.setEditable(false);
		txtJobName.setBounds(1050, 100, 200, 30);
		frmDispatcher.getContentPane().add(txtJobName);
		txtJobName.setColumns(10);

		JLabel lblPickLocation = new JLabel("Pick Location");
		lblPickLocation.setBounds(950, 150, 100, 30);
		frmDispatcher.getContentPane().add(lblPickLocation);

		txtPickLoc = new JTextField();
		txtPickLoc.setEditable(false);
		txtPickLoc.setBounds(1050, 150, 200, 30);
		frmDispatcher.getContentPane().add(txtPickLoc);
		txtPickLoc.setColumns(10);

		JLabel lblDropLocation = new JLabel("Drop Location");
		lblDropLocation.setBounds(950, 200, 100, 30);
		frmDispatcher.getContentPane().add(lblDropLocation);

		txtDropLoc = new JTextField();
		txtDropLoc.setEditable(false);
		txtDropLoc.setBounds(1050, 200, 200, 30);
		frmDispatcher.getContentPane().add(txtDropLoc);
		txtDropLoc.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)), "Closest Driver", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(750, 275, 500, 300);
		frmDispatcher.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblDriverId = new JLabel("Driver ID:");
		lblDriverId.setBounds(25, 50, 100, 30);
		panel.add(lblDriverId);

		JLabel lblDriverName = new JLabel("Driver Name:");
		lblDriverName.setBounds(25, 100, 100, 30);
		panel.add(lblDriverName);

		JLabel lblGpsLocation = new JLabel("GPS Location:");
		lblGpsLocation.setBounds(25, 150, 100, 30);
		panel.add(lblGpsLocation);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(25, 200, 100, 30);
		panel.add(lblPhone);

		txtDriverID = new JTextField();
		txtDriverID.setEditable(false);
		txtDriverID.setBounds(150, 50, 200, 30);
		panel.add(txtDriverID);
		txtDriverID.setColumns(10);

		txtDriverName = new JTextField();
		txtDriverName.setEditable(false);
		txtDriverName.setBounds(150, 100, 200, 30);
		panel.add(txtDriverName);
		txtDriverName.setColumns(10);

		txtDriverLoc = new JTextField();
		txtDriverLoc.setEditable(false);
		txtDriverLoc.setBounds(150, 150, 200, 30);
		panel.add(txtDriverLoc);
		txtDriverLoc.setColumns(10);

		txtDriverPhone = new JTextField();
		txtDriverPhone.setEditable(false);
		txtDriverPhone.setBounds(150, 200, 200, 30);
		panel.add(txtDriverPhone);
		txtDriverPhone.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)), "Jobs List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(25, 50, 700, 450);
		frmDispatcher.getContentPane().add(scrollPane);

		txtJobList = new JTextArea();
		scrollPane.setViewportView(txtJobList);
		txtJobList.setEditable(false);

		JLabel lblPleaseEnterThe = new JLabel("Please enter the job ID to assign.");
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPleaseEnterThe.setBounds(25, 500, 400, 50);
		frmDispatcher.getContentPane().add(lblPleaseEnterThe);

		JLabel label = new JLabel("Job ID:");
		label.setBounds(25, 560, 50, 30);
		frmDispatcher.getContentPane().add(label);

		txtJobInput = new JTextField();
		txtJobInput.setBounds(100, 560, 100, 30);
		frmDispatcher.getContentPane().add(txtJobInput);
		txtJobInput.setColumns(10);

		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent arg0) {
				locateJob();
				locateClosestDriver(selectedJob);
			}
		});
		btnGo.setBounds(250, 550, 100, 50);
		frmDispatcher.getContentPane().add(btnGo);
	}

}

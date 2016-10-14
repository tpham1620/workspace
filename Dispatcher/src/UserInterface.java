
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
import javax.swing.SwingConstants;
import javax.swing.Timer;
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
	private ResultSet onduty;
	private ResultSet unassignedJob;
	private ResultSet allDriver;
	private ResultSet allJob;


	private JFrame frmDispatcher;
	private JTextField txtJobID;
	private JTextField txtPickLoc;
	private JTextField txtDropLoc;
	private JTextField txtDriverID;
	private JTextField txtDriverLoc;
	private JTextField txtDriverPhone;
	private JTextField txtJobInput;
	private static JTextArea txtJobList;

	private JFrame fAddJob;
	private JTextField txtDropLat;
	private JTextField txtPickLat;
	private JTextField txtPickLong;
	private JTextField txtDropLong;

	private JFrame frmCreateNewDriver;
	private JTextField newDriverPwd;
	private JTextField txtPhone;
	private String password;
	private String phone;

	private GPS pick;
	private GPS drop;

	/** MySQL connection to Wine DB*/
	public static Connection connect = null;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		connectionToMySQL();
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
	 * @wbp.parser.entryPoint
	 */
	public UserInterface() {
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
		//String host = "jdbc:mysql://localhost/dispatchdb";
		//String username = "root";
		//String password = "Nguyentan1620";
		String host = "jdbc:mysql://sql3.freemysqlhosting.net/sql399435";
		String username = "sql399435";
		String password = "SLnkIxGVUW";
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
		createUIFrame();
		displayJobList();

		Timer timer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayJobList();
			}
		});
		timer.setRepeats(true); // Only execute once
		timer.start(); 


	}

	private void loadDispatcher(){     
		loadAllDatabase();
		dispatcher = new Dispatcher();
		loadDriver();
		loadJob();

	}

	private void loadAllDatabase(){
		try{
			Statement stmt1 = connect.createStatement();
			Statement stmt2 = connect.createStatement();
			allDriver = stmt2.executeQuery("select * from `drivers`");
			allJob = stmt1.executeQuery("select * from `jobs`");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getNewDriverID(){
		int newID = 1;
		try {
			allDriver.absolute(0);
			while(allDriver.next()){
				if(allDriver.getInt(1)>newID) newID = allDriver.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newID + 1;
	}

	private int getNewJobID(){
		int newID = 1;
		try {
			allJob.absolute(0);
			while(allJob.next()){
				if(allJob.getInt(1)>newID) newID = allJob.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newID + 1;
	}

	/**
	 * load onduty driver from database
	 */
	private void loadDriver(){
		try {
			//load current job from database
			Statement stmt1 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			onduty = stmt1.executeQuery("select * from `drivers` where `drivers`.`Status` = 'WAIT'");
			int id;
			Driver.Status status = Driver.Status.WAIT;
			String pwd, phone;
			GPS gps;
			onduty.absolute(0);
			while (onduty.next()) {
				id = onduty.getInt(1);
				pwd = onduty.getString(3);               
				gps = getGPS(onduty.getString(4));
				phone = onduty.getString(5);
				Driver driver = dispatcher.createDriver(id, status, pwd, gps, phone);
				dispatcher.addDriver(driver);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * load current waiting job from database.
	 */
	private void loadJob(){
		try {
			//load current onduty on data base
			Statement stmt2 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			unassignedJob = stmt2.executeQuery("select * from `jobs` where `jobs`.`Status` = 'UNAS'");
			int id;
			Job.Status status = Job.Status.UNAS;
			GPS pick, drop;
			unassignedJob.absolute(0);
			while (unassignedJob.next()) {
				id = unassignedJob.getInt(1);  
				pick = getGPS(unassignedJob.getString(2));
				drop = getGPS(unassignedJob.getString(3));
				Job job = dispatcher.createJob(id, pick, drop, status);
				dispatcher.addJob(job);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * convert a gps string in form of "latitude", "longitude" to an GPS object.
	 * @param strGPS
	 * @return GPS
	 */
	private GPS getGPS(String strGPS){
		GPS gps;
		double latitude, longitude;
		latitude = Double.parseDouble(strGPS.substring(0, strGPS.indexOf(",")));
		longitude = Double.parseDouble(strGPS.substring(strGPS.indexOf(",") + 1, strGPS.length()));
		gps = new GPS(latitude, longitude);
		return gps;
	}

	protected void displayJobList(){
		try {
			onduty.close();
			unassignedJob.close();
			loadDispatcher();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		txtJobList.setText(dispatcher.unassignedJobToString());
	}

	private void locateJob(){
		int selectedID = Integer.parseInt(txtJobInput.getText());
		selectedJob = dispatcher.getJob(selectedID);
		if(selectedJob!=null){
			txtJobID.setText(selectedJob.getJobID() + "");
			txtPickLoc.setText(selectedJob.getPickLocation().toString());
			txtDropLoc.setText(selectedJob.getDropLocation().toString());	
		}
	}

	private void locateClosestDriver(Job job){
		closestDriver = dispatcher.findClosestDriver(job);
		if(closestDriver!=null){
			txtDriverID.setText(closestDriver.getDriverID() + "");
			txtDriverLoc.setText(closestDriver.getCurrentLocation().toString());
			txtDriverPhone.setText(closestDriver.getPhoneNumer());
		}
	}

	private void assign(){
		if(selectedJob!=null && closestDriver!=null){
			dispatcher.assignJob(selectedJob, closestDriver);

			moveToJobRow(selectedJob.getJobID());
			try {
				unassignedJob.updateString(4, "TASK");
				unassignedJob.updateRow();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			moveToDriverRow(closestDriver.getDriverID());
			try {
				onduty.updateString(2, "TASK");
				onduty.updateInt(6, selectedJob.getJobID());
				onduty.updateRow();
			} catch (SQLException e) {
				e.printStackTrace();
			}


			displayJobList();
			clear();
			JOptionPane.showMessageDialog(null, "Assign job successful");
		}
	}

	private void addNewJob(){
		if(pick != null && drop != null){
			try {
				unassignedJob.moveToInsertRow();
				unassignedJob.updateInt(1, getNewJobID());
				unassignedJob.updateString(2, pick.toString());
				unassignedJob.updateString(3, drop.toString());
				unassignedJob.updateString(4, "UNAS");
				unassignedJob.insertRow();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}

		displayJobList();
	}

	private void addNewDriver(){
		if(password != ""){
			try{
				onduty.moveToInsertRow();
				onduty.updateInt(1, getNewDriverID());
				onduty.updateString(3, password);
				onduty.updateString(2, "OFF");
				onduty.updateString(5, phone);
				onduty.insertRow();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		displayJobList();
	}

	private void clear(){
		txtJobID.setText("");
		txtPickLoc.setText("");
		txtDropLoc.setText("");
		txtDriverID.setText("");
		txtDriverLoc.setText("");
		txtDriverPhone.setText("");
		txtJobInput.setText("");
	}

	private void moveToJobRow(int jobID){
		try {
			unassignedJob.absolute(0);
			while(unassignedJob.next()){
				if(unassignedJob.getInt(1) == jobID){
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void moveToDriverRow(int driverID){
		try {
			onduty.absolute(0);
			while(onduty.next()){
				if(onduty.getInt(1) == driverID){
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void collectData(){
		if(		txtPickLat.getText()!=null &&
				txtPickLong.getText() != null &&
				txtDropLat.getText() != null &&
				txtDropLong.getText() != null){
			try{
				double pickLat = Double.parseDouble(txtPickLat.getText());
				double pickLong = Double.parseDouble(txtPickLong.getText());
				double dropLat = Double.parseDouble(txtDropLat.getText());
				double dropLong = Double.parseDouble(txtDropLong.getText());
				pick = new GPS(pickLat, pickLong);
				drop = new GPS(dropLat, dropLong);
				fAddJob.setVisible(false);
			}catch(Exception e1){
				JOptionPane.showMessageDialog(fAddJob, "Please check your inputs and try again.");
			}
		}
	}

	private void createUIFrame(){
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
		mntmAddDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAddDriverFrm();
				frmCreateNewDriver.setVisible(true);
				frmCreateNewDriver.setAlwaysOnTop(true);

			}
		});
		mnMenu.add(mntmAddDriver);

		JMenuItem mntmAddJob = new JMenuItem("Add Job");
		mnMenu.add(mntmAddJob);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnMenu.add(mntmExit);
		frmDispatcher.getContentPane().setLayout(null);

		JButton btnAddJob = new JButton("Add Job");
		btnAddJob.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAddJobFrame();
				fAddJob.setVisible(true);
				fAddJob.setAlwaysOnTop(true);	
			}
		});
		btnAddJob.setBounds(450, 550, 150, 50);
		frmDispatcher.getContentPane().add(btnAddJob);



		JButton btnAssignJob = new JButton("Assign Job");
		btnAssignJob.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				assign();
			}
		});
		btnAssignJob.setBounds(750, 100, 150, 100);
		frmDispatcher.getContentPane().add(btnAssignJob);

		JLabel lblJobId = new JLabel("Job ID:");
		lblJobId.setBounds(950, 50, 50, 30);
		frmDispatcher.getContentPane().add(lblJobId);

		txtJobID = new JTextField();
		txtJobID.setEditable(false);
		txtJobID.setBounds(1050, 50, 200, 30);
		frmDispatcher.getContentPane().add(txtJobID);
		txtJobID.setColumns(10);

		JLabel lblPickLocation = new JLabel("Pick Location");
		lblPickLocation.setBounds(950, 100, 100, 30);
		frmDispatcher.getContentPane().add(lblPickLocation);

		txtPickLoc = new JTextField();
		txtPickLoc.setEditable(false);
		txtPickLoc.setBounds(1050, 100, 200, 30);
		frmDispatcher.getContentPane().add(txtPickLoc);
		txtPickLoc.setColumns(10);

		JLabel lblDropLocation = new JLabel("Drop Location");
		lblDropLocation.setBounds(950, 150, 100, 30);
		frmDispatcher.getContentPane().add(lblDropLocation);

		txtDropLoc = new JTextField();
		txtDropLoc.setEditable(false);
		txtDropLoc.setBounds(1050, 150, 200, 30);
		frmDispatcher.getContentPane().add(txtDropLoc);
		txtDropLoc.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)),
				"Closest Driver Available", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(750, 275, 500, 300);
		frmDispatcher.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblDriverId = new JLabel("Driver ID:");
		lblDriverId.setBounds(25, 50, 100, 30);
		panel.add(lblDriverId);

		JLabel lblGpsLocation = new JLabel("GPS Location:");
		lblGpsLocation.setBounds(25, 100, 100, 30);
		panel.add(lblGpsLocation);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(25, 150, 100, 30);
		panel.add(lblPhone);

		txtDriverID = new JTextField();
		txtDriverID.setEditable(false);
		txtDriverID.setBounds(150, 50, 200, 30);
		panel.add(txtDriverID);
		txtDriverID.setColumns(10);

		txtDriverLoc = new JTextField();
		txtDriverLoc.setEditable(false);
		txtDriverLoc.setBounds(150, 100, 200, 30);
		panel.add(txtDriverLoc);
		txtDriverLoc.setColumns(10);

		txtDriverPhone = new JTextField();
		txtDriverPhone.setEditable(false);
		txtDriverPhone.setBounds(150, 150, 200, 30);
		panel.add(txtDriverPhone);
		txtDriverPhone.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)), 
				"Jobs Waiting List", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		btnGo.setBounds(250, 550, 150, 50);
		frmDispatcher.getContentPane().add(btnGo);
	}


	private void createAddJobFrame(){
		fAddJob = new JFrame();
		fAddJob.setTitle("Add Job");
		fAddJob.setBounds(100, 100, 470, 380);
		fAddJob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fAddJob.getContentPane().setLayout(null);

		JLabel lblPickLocation = new JLabel("Pick Location:");
		lblPickLocation.setBounds(20, 150, 100, 30);
		fAddJob.getContentPane().add(lblPickLocation);

		JLabel lblDropLocation = new JLabel("Drop Location:");
		lblDropLocation.setBounds(20, 200, 100, 30);
		fAddJob.getContentPane().add(lblDropLocation);

		JLabel lblPleaseEnterThe = new JLabel("Please enter the job inforamtion.");
		lblPleaseEnterThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseEnterThe.setBounds(83, 67, 300, 30);
		fAddJob.getContentPane().add(lblPleaseEnterThe);

		txtDropLat = new JTextField();
		txtDropLat.setBounds(120, 200, 120, 30);
		fAddJob.getContentPane().add(txtDropLat);
		txtDropLat.setColumns(10);

		txtPickLat = new JTextField();
		txtPickLat.setBounds(120, 150, 120, 30);
		fAddJob.getContentPane().add(txtPickLat);
		txtPickLat.setColumns(10);

		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setBounds(120, 110, 100, 30);
		fAddJob.getContentPane().add(lblLatitude);

		JLabel lblNewLabel = new JLabel("Longitude");
		lblNewLabel.setBounds(300, 110, 100, 30);
		fAddJob.getContentPane().add(lblNewLabel);

		txtPickLong = new JTextField();
		txtPickLong.setBounds(300, 150, 120, 30);
		fAddJob.getContentPane().add(txtPickLong);
		txtPickLong.setColumns(10);

		txtDropLong = new JTextField();
		txtDropLong.setBounds(300, 200, 120, 30);
		fAddJob.getContentPane().add(txtDropLong);
		txtDropLong.setColumns(10);

		JLabel label = new JLabel(",");
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(260, 150, 10, 30);
		fAddJob.getContentPane().add(label);

		JLabel label_1 = new JLabel(",");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(260, 200, 10, 30);
		fAddJob.getContentPane().add(label_1);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collectData();
				addNewJob();
				fAddJob.dispose();
			}
		});
		btnOk.setBounds(50, 260, 100, 50);
		fAddJob.getContentPane().add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fAddJob.dispose();
			}
		});
		btnCancel.setBounds(270, 260, 100, 50);
		fAddJob.getContentPane().add(btnCancel);
	}

	public void createAddDriverFrm(){
		frmCreateNewDriver = new JFrame();
		frmCreateNewDriver.setTitle("Create new driver");
		frmCreateNewDriver.setResizable(false);
		frmCreateNewDriver.setBounds(100, 100, 320, 250);
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
				//TODO add new driver
				if(txtPhone.getText()!=null && newDriverPwd.getText()!=null){
					password = newDriverPwd.getText();
					phone = txtPhone.getText();
					addNewDriver();
					frmCreateNewDriver.dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Please check your input.");
				}
			}
		});
		btnRegister.setBounds(30, 170, 100, 25);
		frmCreateNewDriver.getContentPane().add(btnRegister);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCreateNewDriver.dispose();
			}
		});
		btnCancel.setBounds(150, 170, 100, 25);
		frmCreateNewDriver.getContentPane().add(btnCancel);

		JLabel lblPhone = new JLabel("Phone #:");
		lblPhone.setBounds(30, 100, 60, 20);
		frmCreateNewDriver.getContentPane().add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setBounds(30, 130, 250, 30);
		frmCreateNewDriver.getContentPane().add(txtPhone);
		txtPhone.setColumns(10);
	}

}

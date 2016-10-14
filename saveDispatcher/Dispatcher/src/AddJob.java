
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import model.GPS;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AddJob {

	private JFrame fAddJob;
	public JFrame getFrameAddJob(){
		return fAddJob;
	}
	private JTextField txtJobName;
	private JTextField txtDropLat;
	private JTextField txtPickLat;
	private JTextField txtPickLong;
	private JTextField txtDropLong;

	private String jobName;
	private GPS pick;
	private GPS drop;


	/**
	 * Create the application.
	 */
	public AddJob() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fAddJob = new JFrame();
		fAddJob.setTitle("Add Job");
		fAddJob.setBounds(100, 100, 470, 380);
		fAddJob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fAddJob.getContentPane().setLayout(null);

		JLabel lblJobName = new JLabel("Job Name:");
		lblJobName.setBounds(20, 50, 100, 30);
		fAddJob.getContentPane().add(lblJobName);

		JLabel lblPickLocation = new JLabel("Pick Location:");
		lblPickLocation.setBounds(20, 150, 100, 30);
		fAddJob.getContentPane().add(lblPickLocation);

		JLabel lblDropLocation = new JLabel("Drop Location:");
		lblDropLocation.setBounds(20, 200, 100, 30);
		fAddJob.getContentPane().add(lblDropLocation);

		JLabel lblPleaseEnterThe = new JLabel("Please enter the job inforamtion.");
		lblPleaseEnterThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseEnterThe.setBounds(100, 10, 300, 30);
		fAddJob.getContentPane().add(lblPleaseEnterThe);

		txtJobName = new JTextField();
		txtJobName.setBounds(150, 50, 200, 30);
		fAddJob.getContentPane().add(txtJobName);
		txtJobName.setColumns(10);

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
				
				if(getJobName() != null){
					UserInterface.dispatcher.addJob(UserInterface.dispatcher.createJob(getJobName(), getPick(), getDrop()), UserInterface.dispatcher.getUnassignedJob());
					
					UserInterface.displayJobList();
					fAddJob.dispose();
				}
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

	public void collectData(){
		if(txtJobName.getText()!=null &&
				txtPickLat.getText()!=null &&
				txtPickLong.getText() != null &&
				txtDropLat.getText() != null &&
				txtDropLong.getText() != null){
			try{
				double pickLat = Double.parseDouble(txtPickLat.getText());
				double pickLong = Double.parseDouble(txtPickLong.getText());
				double dropLat = Double.parseDouble(txtDropLat.getText());
				double dropLong = Double.parseDouble(txtDropLong.getText());
				jobName = txtJobName.getText();
				pick = new GPS(pickLat, pickLong);
				drop = new GPS(dropLat, dropLong);
				fAddJob.setVisible(false);
			}catch(Exception e1){
				JOptionPane.showMessageDialog(fAddJob, "Please check your inputs and try again.");
			}
		}
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the pick
	 */
	public GPS getPick() {
		return pick;
	}

	/**
	 * @param pick the pick to set
	 */
	public void setPick(GPS pick) {
		this.pick = pick;
	}

	/**
	 * @return the drop
	 */
	public GPS getDrop() {
		return drop;
	}

	/**
	 * @param drop the drop to set
	 */
	public void setDrop(GPS drop) {
		this.drop = drop;
	}

}

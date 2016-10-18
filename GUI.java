import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.List;

public class GUI extends Frame {
	private List machineOptions = new List();
	private Label lblMachines;

	private Label lblSerialNumber;
	private TextField tfSerialNumber;
	private Label lblConfigFN;
	private TextField tfConfigFN;
	private Label lblConfigHV;
	private TextField tfConfigHV;
	private Label lblMirrorHV;
	private TextField tfMirrorHV;
	private Label lblConfigUM;
	private Choice chConfigUM;
	private Label lblSoftwareFN;
	private TextField tfSoftwareFN;
	private Label lblCheckSum;
	private TextField tfCheckSum;
	private Label lblSoftwareUM;
	private Choice chSoftwareUM;
	private Label lblTitle;
	private TextField tfTitle;
	private Label lblExplanation;
	private TextField tfExplanation;
	private Label lblCorrectiveActions;
	private TextField tfCorrectiveActions;
	private Label lblSeverity;
	private TextField tfSeverity;

	Panel east;
	Panel pnlButtons;
	private Button save;
	private Button delete;
	private Button create;
	private Button finish;
	private Button btnNewMachine;

	private LinkedList<Machine> machineList;
	private Machine currMachine;
	private int currIndex;
	private boolean isNewMachine;

	//Make m a linkedlist, set machineList=m and currMachine the head
	public GUI(LinkedList<Machine> input) {
		isNewMachine = true;
		machineList = input;
		currMachine = machineList.get(0);
		setLayout(new BorderLayout(40, 40));


		// Left side of application
		Panel west = new Panel();
		west.setLayout(new GridLayout(4, 1, 3, 3));

		lblMachines = new Label("Select a Machine:");
		west.add(lblMachines);

		String sN;
		for (int i = 0; i < machineList.size(); i++) {
			sN = machineList.get(i).getSerialNumber();
			machineOptions.add(sN);
		}
		machineOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				isNewMachine = false;
				Machine temp = new Machine();
				temp.setSerialNumber(machineOptions.getSelectedItem());
				currIndex = machineList.indexOf(temp);
				currMachine = machineList.get(currIndex);
				tfSerialNumber.setText(currMachine.getSerialNumber());
				tfConfigFN.setText(currMachine.getConfigFileName());
				tfConfigHV.setText(currMachine.getConfigHashValue());
				tfMirrorHV.setText(currMachine.getMirrorHashValue());
				chConfigUM.select(currMachine.getConfigUpdateMode());
				tfSoftwareFN.setText(currMachine.getSoftwareFileName());
				tfCheckSum.setText(currMachine.getCheckSum());
				chSoftwareUM.select(currMachine.getSoftwareUpdateMode());
				tfTitle.setText(currMachine.getTitle());
				tfExplanation.setText(currMachine.getExplanation());
				tfCorrectiveActions.setText(currMachine.getCorrectiveActions());
				tfSeverity.setText(currMachine.getSeverity());
				tfSerialNumber.setEditable(false);
				pnlButtons.add(delete);
				pnlButtons.add(save);
				pnlButtons.remove(create);
			}
		});
		west.add(machineOptions);

		Panel pnlNM = new Panel();
		pnlNM.setLayout(new FlowLayout());
		btnNewMachine = new Button("New Machine");
		btnNewMachine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (!isNewMachine) {
					isNewMachine = true;
				}
				currIndex = -1;
				currMachine = null;
				tfSerialNumber.setText("");
				tfConfigFN.setText("");
				tfConfigHV.setText("");
				tfMirrorHV.setText("");
				tfSoftwareFN.setText("");
				tfCheckSum.setText("");
				tfTitle.setText("");
				tfExplanation.setText("");
				tfCorrectiveActions.setText("");
				tfSeverity.setText("");
				tfSerialNumber.setEditable(true);
				pnlButtons.remove(delete);
				pnlButtons.remove(save);
				pnlButtons.add(create);
			}
		});
		pnlNM.add(btnNewMachine);
		west.add(pnlNM);

		Panel pnlFinish = new Panel();
		pnlFinish.setLayout(new FlowLayout());
		finish = new Button("Finish Session and Write File");
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					PrintWriter writer = new PrintWriter("elitemgr.properties");
					writer.println("output.dir=C:\\\\Program Files\\\\HaemoCommunicator\\\\80\\\\drivers\\\\EliteMgr\\\\file");
					writer.println("");
					writer.println("");
					writer.println("");
					writer.println("config.update=\"ConfigUpdate\": { \"Folder\": \"C:\\\\Program Files\\\\HaemoCommunicator\\\\80\\\\drivers\\\\EliteMgr\\\\file\"\\, \"Filename\": \"database.dat\"\\, \"ConfigHashValue\": \"12345\"\\, \"MirrorHashValue\": \"12345\"\\, \"UpdateMode\": \"Ask\" }");
					writer.println("software.update=\"SoftwareUpdate\": { \"Folder\": \"C:\\\\Program Files\\\\HaemoCommunicator\\\\81\\\\drivers\\\\EliteMgr\\\\Software\", \"Checksum\": \"\"\\, \"UpdateMode\": \"Notify\" }");
					writer.println("notification.update=\"Notification\": { \"Title\": \"A title goes here\"\\, \"Explanation\": null\\, \"CorrectiveActions\": null\\, \"Severity\": null }");
					for (int i = 0; i < machineList.size(); i++) {
						writer.println("");
						writer.println("");
						writer.println("");
						writer.println("config."+machineList.get(i).getSerialNumber()+".update=\"ConfigUpdate\": { \"Folder\": \"C:\\\\Program Files\\\\HaemoCommunicator\\\\80\\\\drivers\\\\EliteMgr\\\\file\\\\"+machineList.get(i).getSerialNumber()+"\"\\, \"Filename\": \""+machineList.get(i).getConfigFileName()+"\"\\, \"ConfigHashValue\": \""+machineList.get(i).getConfigHashValue()+"\"\\, \"MirrorHashValue\": \""+machineList.get(i).getMirrorHashValue()+"\"\\, \"UpdateMode\": \""+machineList.get(i).getConfigUpdateMode()+"\" }");
						writer.println("software."+machineList.get(i).getSerialNumber()+".update=\"SoftwareUpdate\": { \"Folder\": \"C:\\\\Program Files\\\\HaemoCommunicator\\\\80\\\\drivers\\\\EliteMgr\\\\file\\\\"+machineList.get(i).getSerialNumber()+"\"\\, \"Filename\": \""+machineList.get(i).getSoftwareFileName()+"\"\\, \"Checksum\": \""+machineList.get(i).getCheckSum()+"\"\\, \"UpdateMode\": \""+machineList.get(i).getSoftwareUpdateMode()+"\" }");
						writer.println("notification."+machineList.get(i).getSerialNumber()+".update=\"Notification\": { \"Title\": \""+machineList.get(i).getTitle()+"\"\\, \"Explanation\": \""+machineList.get(i).getExplanation()+"\"\\, \"CorrectiveActions\": \""+machineList.get(i).getCorrectiveActions()+"\"\\, \"Severity\": \""+machineList.get(i).getSeverity()+"\" }");
					}
					writer.close();
				} catch (FileNotFoundException ex) {
					System.out.println("Problem writing file");
				}
				dispose();
				System.exit(0);
			}
		});
		pnlFinish.add(finish);
		west.add(pnlFinish);


		add(west, BorderLayout.WEST);





		// Right side of application
		east = new Panel();
		east.setLayout(new GridLayout(5, 1, 0, 40));

		Panel pnlSerialNumber = new Panel();
		pnlSerialNumber.setLayout(new FlowLayout());
		lblSerialNumber = new Label("Serial Number:");
		pnlSerialNumber.add(lblSerialNumber);
		tfSerialNumber = new TextField("", 30);
		tfSerialNumber.setEditable(true);
		pnlSerialNumber.add(tfSerialNumber);
		east.add(pnlSerialNumber);


		Panel config = new Panel();
		config.setLayout(new GridLayout(5, 1, 1, 1));
		config.add(new Label("CONFIG UPDATE"));
		Panel software = new Panel();
		software.setLayout(new GridLayout(4, 1, 1, 1));
		software.add(new Label("SOFTWARE UPDATE"));
		Panel notif = new Panel();
		notif.setLayout(new GridLayout(5, 1, 1, 1));
		notif.add(new Label("NOTIFICATION UPDATE"));

		Panel pnlConfigFN = new Panel();
		lblConfigFN = new Label("Filename:");
		pnlConfigFN.add(lblConfigFN);
		tfConfigFN = new TextField("", 30);
		tfConfigFN.setEditable(true);
		pnlConfigFN.add(tfConfigFN);
		config.add(pnlConfigFN);

		Panel pnlConfigHV = new Panel();
		lblConfigHV = new Label("ConfigHashValue:");
		pnlConfigHV.add(lblConfigHV);
		tfConfigHV = new TextField("", 30);
		tfConfigHV.setEditable(true);
		pnlConfigHV.add(tfConfigHV);
		config.add(pnlConfigHV);

		Panel pnlMirrorHV = new Panel();
		lblMirrorHV = new Label("MirrorHashValue:");
		pnlMirrorHV.add(lblMirrorHV);
		tfMirrorHV = new TextField("", 30);
		tfMirrorHV.setEditable(true);
		pnlMirrorHV.add(tfMirrorHV);
		config.add(pnlMirrorHV);

		Panel pnlConfigUM = new Panel();
		lblConfigUM = new Label("Update Mode:");
		pnlConfigUM.add(lblConfigUM);
		chConfigUM = new Choice();
		chConfigUM.add("Silent");
		chConfigUM.add("Ask");
		chConfigUM.add("Notify");
		//chConfigUM.select(currMachine.getConfigUpdateMode());
		pnlConfigUM.add(chConfigUM);
		config.add(pnlConfigUM);

		east.add(config);

		Panel pnlSoftwareFN = new Panel();
		lblSoftwareFN = new Label("Filename:");
		pnlSoftwareFN.add(lblSoftwareFN);
		tfSoftwareFN = new TextField("", 30);
		tfSoftwareFN.setEditable(true);
		pnlSoftwareFN.add(tfSoftwareFN);
		software.add(pnlSoftwareFN);

		Panel pnlCheckSum = new Panel();
		lblCheckSum = new Label("Checksum:");
		pnlCheckSum.add(lblCheckSum);
		tfCheckSum = new TextField("", 30);
		tfCheckSum.setEditable(true);
		pnlCheckSum.add(tfCheckSum);
		software.add(pnlCheckSum);

		Panel pnlSoftwareUM = new Panel();
		lblSoftwareUM = new Label("Update Mode:");
		pnlSoftwareUM.add(lblSoftwareUM);
		chSoftwareUM = new Choice();
		chSoftwareUM.add("Silent");
		chSoftwareUM.add("Ask");
		chSoftwareUM.add("Notify");
		//chSoftwareUM.select(currMachine.getSoftwareUpdateMode());
		pnlSoftwareUM.add(chSoftwareUM);
		software.add(pnlSoftwareUM);

		east.add(software);

		Panel pnlTitle = new Panel();
		lblTitle = new Label("Title:");
		pnlTitle.add(lblTitle);
		tfTitle = new TextField("", 30);
		tfTitle.setEditable(true);
		pnlTitle.add(tfTitle);
		notif.add(pnlTitle);

		Panel pnlExplanation = new Panel();
		lblExplanation = new Label("Explanation:");
		pnlExplanation.add(lblExplanation);
		tfExplanation = new TextField("", 30);
		tfExplanation.setEditable(true);
		pnlExplanation.add(tfExplanation);
		notif.add(pnlExplanation);

		Panel pnlCorrectiveActions = new Panel();
		lblCorrectiveActions = new Label("Corrective Actions:");
		pnlCorrectiveActions.add(lblCorrectiveActions);
		tfCorrectiveActions = new TextField("", 30);
		tfCorrectiveActions.setEditable(true);
		pnlCorrectiveActions.add(tfCorrectiveActions);
		notif.add(pnlCorrectiveActions);

		Panel pnlSeverity = new Panel();
		lblSeverity = new Label("Severity:");
		pnlSeverity.add(lblSeverity);
		tfSeverity = new TextField("", 30);
		tfSeverity.setEditable(true);
		pnlSeverity.add(tfSeverity);
		notif.add(pnlSeverity);

		east.add(notif);

		pnlButtons = new Panel();
		pnlButtons.setLayout(new FlowLayout());
		save = new Button("Save Machine");
		delete = new Button("Delete Machine");
		create = new Button("Create Machine");
		pnlButtons.add(create);
		pnlButtons.add(delete);
		pnlButtons.add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				currMachine.setConfigFileName(tfConfigFN.getText());
				currMachine.setCheckSum(tfCheckSum.getText());
				currMachine.setConfigHashValue(tfConfigHV.getText());
				currMachine.setMirrorHashValue(tfMirrorHV.getText());
				currMachine.setConfigUpdateMode(chConfigUM.getSelectedItem());
				currMachine.setSoftwareFileName(tfSoftwareFN.getText());
				currMachine.setCheckSum(tfCheckSum.getText());
				currMachine.setSoftwareUpdateMode(chSoftwareUM.getSelectedItem());
				currMachine.setTitle(tfTitle.getText());
				currMachine.setExplanation(tfExplanation.getText());
				currMachine.setCorrectiveActions(tfCorrectiveActions.getText());
				currMachine.setSeverity(tfSeverity.getText());
			}
		});

		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				currMachine = new Machine();
				currMachine.setSerialNumber(tfSerialNumber.getText());
				currMachine.setConfigFileName(tfConfigFN.getText());
				currMachine.setCheckSum(tfCheckSum.getText());
				currMachine.setConfigHashValue(tfConfigHV.getText());
				currMachine.setMirrorHashValue(tfMirrorHV.getText());
				currMachine.setConfigUpdateMode(chConfigUM.getSelectedItem());
				currMachine.setSoftwareFileName(tfSoftwareFN.getText());
				currMachine.setCheckSum(tfCheckSum.getText());
				currMachine.setSoftwareUpdateMode(chSoftwareUM.getSelectedItem());
				currMachine.setTitle(tfTitle.getText());
				currMachine.setExplanation(tfExplanation.getText());
				currMachine.setCorrectiveActions(tfCorrectiveActions.getText());
				currMachine.setSeverity(tfSeverity.getText());
				machineList.add(currMachine);
				machineOptions.add(currMachine.getSerialNumber());
				machineOptions.select(machineOptions.getItemCount()-1);
				tfSerialNumber.setEditable(false);
				pnlButtons.add(delete);
				pnlButtons.add(save);
				pnlButtons.remove(create);
			}
		});
		east.add(pnlButtons);
		//pnlButtons.remove(save);
		//pnlButtons.remove(delete);

		add(east, BorderLayout.CENTER);


		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
				System.exit(0);
			}
		});
		setTitle("elitemgr.properties Editor");  // "super" Frame sets its title
      	setSize(1100, 800);        // "super" Frame sets its initial window size
      	setVisible(true);
	}
}

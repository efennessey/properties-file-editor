import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MachineSetup {
	InputStream inputStream;
	LinkedList<Machine> machineList = new LinkedList<Machine>();

	public LinkedList<Machine> makeMachines() throws IOException {

		try {
			Properties prop = new Properties();
			File jarPath=new File(MachineSetup.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String propertiesPath=jarPath.getParentFile().getAbsolutePath();
			propertiesPath = propertiesPath.replace("%20", " ");
			System.out.println(" propertiesPath-"+propertiesPath);
			String propFileName = "elitemgr.properties";
			//inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			prop.load(new FileInputStream(propertiesPath+"/elitemgr.properties"));
/***
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
***/
			Enumeration<?> keys = prop.propertyNames();
			//Put the info from each key/value pair into the correct Machine in the LinkedList
			//or create a new Machine and append it to the list
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = prop.getProperty(key);
				String serialNumber = "";
				String keyType = "";
				Machine newMachine = new Machine();

				//Find the serial number using regular expressions
				Pattern configPattern = Pattern.compile("config.(.+?).update");
				Pattern softwarePattern = Pattern.compile("software.(.+?).update");
				Pattern notifPattern = Pattern.compile("notification.(.+?).update");
				Matcher configMatch = configPattern.matcher(key);
				Matcher softwareMatch = softwarePattern.matcher(key);
				Matcher notifMatch = notifPattern.matcher(key);

				//Categorize key and initialize new machine with serial number
				if (configMatch.find()) {
					serialNumber = configMatch.group(1);
					newMachine.setSerialNumber(serialNumber);
					keyType = "config";
				} else if (softwareMatch.find()) {
					serialNumber = softwareMatch.group(1);
					newMachine.setSerialNumber(serialNumber);
					keyType = "software";
				} else if (notifMatch.find()) {
					serialNumber = notifMatch.group(1);
					newMachine.setSerialNumber(serialNumber);
					keyType = "notif";
				}

				//Determine if machine already exists in the list
				int index = -1;
				if (machineList != null) {
					index = machineList.indexOf(newMachine);
				}

				if (index == -1) {
					//Add new info to initialized machine and append it to list
					newMachine = putMachineInfo(value, keyType, newMachine);
					machineList.add(newMachine);
				} else {
					//Get existing machine, update info, replace old machine
					newMachine = machineList.get(index);
					newMachine = putMachineInfo(value, keyType, newMachine);
					machineList.set(index, newMachine);
				} 
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
	/***	
		finally {
			inputStream.close();
		}
***/
		cleanMachines();
/***
		for (int x = 0; x < machineList.size(); x++) {
			machineList.get(x).printMachine();
		}
		***/
		return machineList;
	}


	//Finds desired attributes from the value of a given key and puts them in the Machine object
	private Machine putMachineInfo(String value, String keyType, Machine machine) {
		if (keyType=="config") {
			Pattern cFileNamePattern = Pattern.compile(", \"Filename\": \"(.+?)\"");
			Matcher cFNameMatch = cFileNamePattern.matcher(value);
			cFNameMatch.find();
			machine.setConfigFileName(cFNameMatch.group(1));

			Pattern cHashValuePattern = Pattern.compile(", \"ConfigHashValue\": \"(.+?)\"");
			Matcher cHValueMatch = cHashValuePattern.matcher(value);
			cHValueMatch.find();
			machine.setConfigHashValue(cHValueMatch.group(1));

			Pattern mirrorHashValuePattern = Pattern.compile(", \"MirrorHashValue\": \"(.+?)\"");
			Matcher mirrorHVMatch = mirrorHashValuePattern.matcher(value);
			mirrorHVMatch.find();
			machine.setMirrorHashValue(mirrorHVMatch.group(1));

			Pattern cUpdateModePattern = Pattern.compile(", \"UpdateMode\": \"(.+?)\"");
			Matcher cUModeMatch = cUpdateModePattern.matcher(value);
			cUModeMatch.find();
			machine.setConfigUpdateMode(cUModeMatch.group(1));

		} else if (keyType=="software") {
			Pattern sFileNamePattern = Pattern.compile(", \"Filename\": \"(.+?)\"");
			Matcher sFNameMatch = sFileNamePattern.matcher(value);
			sFNameMatch.find();
			machine.setSoftwareFileName(sFNameMatch.group(1));

			Pattern checkSumPattern = Pattern.compile(", \"Checksum\": \"(.+?)\"");
			Matcher checkSumMatch = checkSumPattern.matcher(value);
			checkSumMatch.find();
			machine.setCheckSum(checkSumMatch.group(1));

			Pattern sUpdateModePattern = Pattern.compile(", \"UpdateMode\": \"(.+?)\"");
			Matcher sUModeMatch = sUpdateModePattern.matcher(value);
			sUModeMatch.find();
			machine.setSoftwareUpdateMode(sUModeMatch.group(1));
		
		} else if (keyType=="notif") {
			Pattern titlePattern = Pattern.compile(" \"Title\": (.+?),");
			Matcher titleMatch = titlePattern.matcher(value);
			titleMatch.find();
			machine.setTitle(titleMatch.group(1));

			Pattern explanationPattern = Pattern.compile(", \"Explanation\": (.+?),");
			Matcher explanationMatch = explanationPattern.matcher(value);
			explanationMatch.find();
			machine.setExplanation(explanationMatch.group(1));

			Pattern correctiveActionsPattern = Pattern.compile(", \"CorrectiveActions\": (.+?),");
			Matcher cActionsMatch = correctiveActionsPattern.matcher(value);
			cActionsMatch.find();
			machine.setCorrectiveActions(cActionsMatch.group(1));

			Pattern severityPattern = Pattern.compile(", \"Severity\": (.+?)}");
			Matcher severityMatch = severityPattern.matcher(value);
			severityMatch.find();
			machine.setSeverity(severityMatch.group(1));
		}
		return machine;
	}

	//Removes any extraneous artifacts and put variables in desired formats
	private void cleanMachines() {

		int emptySN = -1;

		for (int i = 0; i < machineList.size(); i++) {
			Machine curr = machineList.get(i);
			if (curr.getSerialNumber() == "") {
				emptySN = i;
			}
			if (curr.getMirrorHashValue() != null) {
				if (curr.getMirrorHashValue().length() < 4 && curr.getMirrorHashValue().contains(",")) {
					curr.setMirrorHashValue("");
				}
			}
			if (curr.getConfigFileName() != null) {
				if (curr.getConfigFileName().length() < 4 && curr.getConfigFileName().contains(",")) {
					curr.setConfigFileName("");
				}
			}
			if (curr.getSoftwareFileName() != null) {
				if (curr.getSoftwareFileName().length() < 4 && curr.getSoftwareFileName().contains(",")) {
					curr.setSoftwareFileName("");
				}
			}
			if (curr.getCheckSum() != null) {
				if (curr.getCheckSum().length() < 4 && curr.getCheckSum().contains(",")) {
					curr.setCheckSum("");
				}
			}
			if (curr.getTitle() != null) {
				curr.setTitle(curr.getTitle().replace("\"", ""));
			}
			if (curr.getExplanation() != null) {
				curr.setExplanation(curr.getExplanation().replace("\"", ""));
			}
			if (curr.getCorrectiveActions() != null) {
				curr.setCorrectiveActions(curr.getCorrectiveActions().replace("\"", ""));
			}
			if (curr.getSeverity() != null) {
				curr.setSeverity(curr.getSeverity().replace("\"", ""));
			}
			
			machineList.set(i, curr);
		}

		if (emptySN != -1) {
			machineList.remove(emptySN);
		}
	}
	

}
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Driver {
	public static void main(String[] args) throws IOException, FileNotFoundException {
		MachineSetup ms = new MachineSetup();
		LinkedList<Machine> machineList = ms.makeMachines();
		new GUI(machineList);
	}
}
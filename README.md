# properties-file-editor
A simple interface for editing a *.properties* file that contains a cluster of text that often repeats itself. This Java program reads in the file, parses the property values for the desired fields using regular expressions and stores the information in a linked list of objects. Users are then able to edit this information with a cleaner GUI constructed with built-in Java packages.

`Machine.java` contains the Machine class, which is used to make objects that store the desired information.

`MachineSetup.java` assumes the file `elitemgr.properties` is in the same directory. It reads the file and creates Machine objects for each "machine" listed.

`GUI.java` sets up the Frame to input information.

`Driver.java`contains the main method for the program.

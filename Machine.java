public class Machine {
  private String serialNumber = "";
  private String configFileName;
  private String configHashValue;
  private String mirrorHashValue;
  private String configUpdateMode;
  private String softwareFileName;
  private String checkSum;
  private String softwareUpdateMode;
  private String title;
  private String explanation;
  private String correctiveActions;
  private String severity;

  public String getSerialNumber() {return serialNumber;}
  public String getConfigHashValue() {return configHashValue;}
  public String getMirrorHashValue() {return mirrorHashValue;}
  public String getConfigUpdateMode() {return configUpdateMode;}
  public String getConfigFileName() {return configFileName;}
  public String getSoftwareFileName() {return softwareFileName;}
  public String getCheckSum() {return checkSum;}
  public String getSoftwareUpdateMode() {return softwareUpdateMode;}
  public String getTitle() {return title;}
  public String getExplanation() {return explanation;}
  public String getCorrectiveActions() {return correctiveActions;}
  public String getSeverity() {return severity;}

  public void setSerialNumber(String updated) {serialNumber = updated;}
  public void setConfigHashValue(String updated) {configHashValue = updated;}
  public void setMirrorHashValue(String updated) {mirrorHashValue = updated;}
  public void setConfigUpdateMode(String updated) {configUpdateMode = updated;}
  public void setConfigFileName(String updated) {configFileName = updated;}
  public void setSoftwareFileName(String updated) {softwareFileName = updated;}
  public void setCheckSum(String updated) {checkSum = updated;}
  public void setSoftwareUpdateMode(String updated) {softwareUpdateMode = updated;}
  public void setTitle(String updated) {title = updated;}
  public void setExplanation(String updated) {explanation = updated;}
  public void setCorrectiveActions(String updated) {correctiveActions = updated;}
  public void setSeverity(String updated) {severity = updated;}

  public void printMachine() {
    System.out.println("Serial Number: "+serialNumber);
    System.out.println("Config HashValue: "+configHashValue);
    System.out.println("mirrorHashValue: "+mirrorHashValue);
    System.out.println("configUpdateMode: "+configUpdateMode);
    System.out.println("configFileName: "+configFileName);
    System.out.println("softwareFileName: "+softwareFileName);
    System.out.println("checkSum: "+checkSum);
    System.out.println("softwareUpdateMode: "+softwareUpdateMode);
    System.out.println("title: "+title);
    System.out.println("explanation: "+explanation);
    System.out.println("correctiveActions: "+correctiveActions);
    System.out.println("severity: "+severity);
    System.out.println();
  }

  @Override 
  public boolean equals(Object o) {
    Machine m = (Machine) o;
    return this.serialNumber.equals(m.getSerialNumber());
  }
}

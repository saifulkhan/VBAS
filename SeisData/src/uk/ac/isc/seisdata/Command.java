package uk.ac.isc.seisdata;

import java.util.Date;

public class Command extends AbstractSeisData {

    // written by VBAS
    private Integer evid;       // event id
    private String commandProvenance;  // the formulated command in JSON
    private String systemCommand;   // the function (s) in JSON

    // Processed by & read from the database
    private Integer id;     // command id    
    private String analyst; // analyst name
    private String pass;    // the pass information (p-primary, s-secondary, i-?)
    private Date date;      // date when the action or command was generated
    private String status;  // ?
    private String type;    // ?

    public Command() {

    }

    public Command(Integer evid,
            String commandProvenance,
            String systemCommand,
            Integer id,
            String analyst,
            String pass,
            Date date,
            String status,
            String type) {

        this.evid = evid;
        this.commandProvenance = commandProvenance;
        this.systemCommand = systemCommand;
        this.id = id;
        this.analyst = analyst;
        this.pass = pass;
        this.date = date;
        this.status = status;
        this.type = type;
    }

    public Integer getEvid() {
        return evid;
    }

    public void setEvid(Integer evid) {
        this.evid = evid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnalyst() {
        return analyst;
    }

    public void setAnalyst(String analyst) {
        this.analyst = analyst;
    }

    public String getCommandProvenance() {
        return commandProvenance;
    }

 
    public void setCommandProvenance(String command) {
        this.commandProvenance = command;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSystemCommand() {
        return systemCommand;
    }

    public void setSystemCommand(String systemCommand) {
        this.systemCommand = systemCommand;
    }

}

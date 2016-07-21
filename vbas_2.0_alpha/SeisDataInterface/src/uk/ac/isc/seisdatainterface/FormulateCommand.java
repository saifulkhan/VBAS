package uk.ac.isc.seisdatainterface;

import uk.ac.isc.seisdata.VBASLogger;
import com.orsoncharts.util.json.JSONArray;
import com.orsoncharts.util.json.JSONObject;
import java.util.Arrays;
import com.orsoncharts.util.json.parser.JSONParser;
import java.util.ArrayList;
import org.openide.util.Exceptions;

/**
 * **********************************************************************************************************
 * TODO: Move out of SeisData module.
 * *********************************************************************************************************
 */
public class FormulateCommand {

    private static final String[] COMMAND_TYPES
            = {"phaseedit", "hypocentreedit", "seiseventrelocate", "setprime", "seiseventbanish", "assess", 
                "movehypocentre", "deletehypocentre", "createevent"};

    private static final String[] DATA_TYPES
            = {"seisevent", "hypocentre", "phase"};
    private static final String[] ATTRIBUTES
            = { /*setprime*/"primehypocentre",
                /*phaseedit*/ "phasetype", "phase_fixed", "nondef", "timeshift", "deleteamp", "phasebreak", "putvalue",
                /*hypocentreedit*/ "depth", "time", "lat", "lon",
                /*seiseventrelocate*/ "fix_depth", "free_depth", "fix_depth_default", "fix_depth_median", "do_gridsearch",
                /*assess*/ "commands", "report",
                "comment", "reason"};

    private final String commandType;
    private final String dataType;
    private final int id;           // id of the dataType
    private final String agency;    // Agency used in setprome, hypocentreedit, seiseventrelocate

    public FormulateCommand(String commandType, String dataType, int id, String agency) {

        if (!Arrays.asList(COMMAND_TYPES).contains(commandType)) {
            VBASLogger.logSevere("commandType=" + commandType + ", Supported:" + Arrays.toString(COMMAND_TYPES));
        }

        if (!Arrays.asList(DATA_TYPES).contains(dataType)) {
            VBASLogger.logSevere("dataType=" + dataType + ", Supported:" + Arrays.toString(DATA_TYPES));
        }

        this.commandType = commandType;
        this.dataType = dataType;
        this.id = id;
        this.agency = agency;
    }

    /*
     **************************************************************************************
     * Related to System Command
     **************************************************************************************
     */
    private JSONArray sqlFunctionArray = new JSONArray();

    public void addSQLFunction(String functionName) {
        //Global.logDebug(functionName);
        JSONObject attrObj = new JSONObject();
        attrObj.put("name", functionName);
        sqlFunctionArray.add(attrObj);
    }

    String locatorArgStr = "";

    public void addLocatorArg(String arg) {
        locatorArgStr += arg + " ";
    }

    public JSONObject getSystemCommand() {
        JSONObject obj = new JSONObject();
        obj.put("commandType", commandType);
        obj.put("dataType", dataType);
        obj.put("id", id);

        if (sqlFunctionArray.size() > 0) {
            obj.put("sqlFunctionArray", sqlFunctionArray);
        } else {
            obj.put("sqlFunctionArray", null);
        }

        obj.put("locatorArgStr", locatorArgStr);

        return obj;
    }

    public Boolean isValidSystemCommand() {
        return !(sqlFunctionArray.size() <= 0 && locatorArgStr.equals(""));
    }

    // Used by assess - merge multiple system commands
    // Extract the SQL functions and Locator arguments 
    // Merge with the existing system command
    public void mergeSystemCommand(String cmd) {

        JSONParser parser = new JSONParser();
        Object obj = null;

        try {
            obj = parser.parse(cmd);
        } catch (com.orsoncharts.util.json.parser.ParseException ex) {
            VBASLogger.logSevere("cmd=" + cmd + " is not a valid json format.");
        }

        if (isJSONArray(cmd)) { // array of System Command
            JSONArray arr = (JSONArray) obj;
            for (Object o : arr) {
                JSONObject jObj = (JSONObject) o;
                JSONArray array = (JSONArray) jObj.get("sqlFunctionArray");
                if (array != null) {
                    sqlFunctionArray.addAll(array);     // append all 
                }
                addLocatorArg(jObj.get("locatorArgStr").toString());
            }

        } else if (isJSONObject(cmd)) {
            JSONObject jObj = (JSONObject) obj;
            JSONArray array = (JSONArray) jObj.get("sqlFunctionArray");
            if (array != null) {
                sqlFunctionArray.addAll(array);     // append all 
            }
            addLocatorArg(jObj.get("locatorArgStr").toString());
        }

        VBASLogger.logDebug("sqlFunctionArray= " + sqlFunctionArray.toString());
        VBASLogger.logDebug("locatorArgStr= " + locatorArgStr);
    }

    public ArrayList<String> getSQLFunctionArray() {
        ArrayList<String> fun = new ArrayList<String>();

        for (Object o : sqlFunctionArray) {
            JSONObject jObj = (JSONObject) o;
            String functionName = (String) jObj.get("name");
            //Global.logDebug("jObj=" + jObj.toString() +  ", name:"  + functionName);
            fun.add((String) jObj.get("name"));
        }

        return fun;
    }

    public String getLocatorArgStr() {
        // 
        return locatorArgStr;
    }

    /*
     **************************************************************************************
     * Related to Command Provenance
     **************************************************************************************
     */
    private JSONArray attrArray = new JSONArray();

    public void addAttribute(String attributeName, Object newValue, Object oldValue) {
        if (!Arrays.asList(ATTRIBUTES).contains(attributeName)) {
            VBASLogger.logSevere("attributeName=" + attributeName
                    + ", Supported:" + Arrays.toString(ATTRIBUTES));
        }

        JSONObject attrObj = new JSONObject();
        attrObj.put("name", attributeName);
        if (oldValue != null) {
            attrObj.put("oldValue", oldValue);
        }
        if (newValue != null) {
            attrObj.put("newValue", newValue);
        }

        attrArray.add(attrObj);
    }

    public JSONObject getCmdProvenance() {
        JSONObject obj = new JSONObject();
        obj.put("commandType", commandType);
        obj.put("dataType", dataType);
        obj.put("id", id);
        obj.put("agency", agency);

        if (attrArray.size() > 0) {
            obj.put("attributeArray", attrArray);
        } else {
            obj.put("attributeArray", null);
        }

        return obj;
    }

    // Analyst redable 
    public static String getAnalystReadableCommand(String cmd) {

        VBASLogger.logDebug("JSON Format: " + cmd);

        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(cmd);
        } catch (com.orsoncharts.util.json.parser.ParseException ex) {
            VBASLogger.logSevere("Not a valid JSON string: " + cmd);
        }

        String str = "";

        if (isJSONArray(cmd)) { // array of commands
            JSONArray commands = (JSONArray) obj;
            for (Object o : commands) {
                JSONObject command = (JSONObject) o;
                str += translate(command) + "; ";
            }

        } else if (isJSONObject(cmd)) {
            JSONObject command = (JSONObject) obj;
            str = translate(command);
        }

        VBASLogger.logDebug("Readable format: " + str);
        return str;
    }

    private static String translate(JSONObject command) {

        String commandType = command.get("commandType").toString();
        String str = "";

        switch (commandType) {
            case "setprime":
                str += "Setprime" + " ";
                str += command.get("agency").toString() + " ";
                break;
            
            case "movehypocentre":
                str += "MoveHypocentre" + " ";
                str += command.get("agency").toString() + " ";
                break;
            
            case "deletehypocentre":
                str += "DeleteHypocentre" + " ";
                str += command.get("agency").toString() + " ";
                break;
                
            case "createevent":
                str += "CreateEvent" + " ";
                str += command.get("agency").toString() + " ";
                break;

            case "seiseventbanish":
                str += commandType + " ";
                str += command.get("id").toString() + " ";
                break;

            case "hypocentreedit":
                str += "Change" + " ";
                str += command.get("agency").toString() + " ";
                str += translateAttributes(command);
                break;

            case "phaseedit":
                str += "Change" + " ";
                str += command.get("id").toString() + " ";

                str += translateAttributes(command);
                break;

            case "seiseventrelocate":
                str += "Relocate" + " ";
                str += command.get("agency").toString() + " ";
                str += translateAttributes(command);
                break;

            case "merge":
                str += "Merge" + " ";
                str += translateAttributes(command);
                break;
            default:
                str += commandType + " ";
                str += command.get("id").toString() + " ";
                break;

        }
        return str;
    }

    private static String translateAttributes(JSONObject command) {

        String commandType = command.get("commandType").toString();
        String str = "";

        JSONArray attributes = (JSONArray) command.get("attributeArray");
        if (attributes != null) {
            for (Object o1 : attributes) {
                JSONObject attribute = (JSONObject) o1;

                switch (commandType) {
                    case "merge":
                        str += attribute.get("newValue") + " " + attribute.get("oldValue");
                        break;
                        
                    default:
                        if (!attribute.get("name").toString().equals("reason")) {
                            str += (attribute.get("newValue") == null ? " " : attribute.get("name").toString()
                                    + "=" + attribute.get("newValue").toString()) + ", ";
                        }
                        break;
                }

            }
        }
        return str;
    }

    /*
     **************************************************************************************
     * other functions, chekc if a string is a valid JSON object {} or JSON array of objects [{}, {}]
     **************************************************************************************
     */
    private static Boolean isJSONObject(String str) {
        try {
            JSONParser parser = new JSONParser();
            Object json = parser.parse(str);
            if (json instanceof JSONObject) { // object
                return true;
            }
        } catch (com.orsoncharts.util.json.parser.ParseException ex) {
            Exceptions.printStackTrace(ex);
            VBASLogger.logSevere("str=" + str + " is not a valid json format.");
        }
        return false;
    }

    private static Boolean isJSONArray(String str) {
        try {
            JSONParser parser = new JSONParser();
            Object json = parser.parse(str);
            if (json instanceof JSONArray) { // you have an array
                return true;
            }
        } catch (com.orsoncharts.util.json.parser.ParseException ex) {
            Exceptions.printStackTrace(ex);
            VBASLogger.logSevere("str=" + str + " is not a valid json format.");
        }
        return false;
    }

    /*
    
     // Reformat the input command (Command Provenance format) for user.
     public static String getRedableCommandStr(String cmd) {

     VBASLogger.logDebug("json: " + cmd);

     JSONParser parser = new JSONParser();
     Object obj = null;
     try {
     obj = parser.parse(cmd);
     } catch (com.orsoncharts.util.json.parser.ParseException ex) {
     VBASLogger.logSevere("cmd=" + cmd + " is not a valid json format.");
     }

     String str = "";

     if (isJSONArray(cmd)) { // array of System Command
     JSONArray commands = (JSONArray) obj;
     for (Object o : commands) {
     JSONObject command = (JSONObject) o;
     str += command.get("dataType").toString() + "#" + command.get("id").toString() + ": ";

     // TODO: repeated code
     JSONArray attributes = (JSONArray) command.get("attributeArray");
     if (attributes != null) {
     for (Object o1 : attributes) {
     JSONObject attribute = (JSONObject) o1;
     str += attribute.get("name").toString() + ":"
     + (attribute.get("oldValue") == null ? "" : (attribute.get("oldValue").toString() + "->"))
     + (attribute.get("newValue") == null ? "" : attribute.get("newValue").toString())
     + " ";
     }
     }
     }

     } else if (isJSONObject(cmd)) {
     JSONObject command = (JSONObject) obj;
     str += command.get("dataType").toString() + "#" + command.get("id").toString() + ": ";

     // TODO: repeated code
     JSONArray attributes = (JSONArray) command.get("attributeArray");
     if (attributes != null) {
     for (Object o1 : attributes) {
     JSONObject attribute = (JSONObject) o1;
     str += attribute.get("name").toString() + ":"
     + (attribute.get("oldValue") == null ? "" : (attribute.get("oldValue").toString() + "->"))
     + (attribute.get("newValue") == null ? "" : attribute.get("newValue").toString())
     + " ";
     }
     }

     }

     VBASLogger.logDebug("readable format: " + str);
     return str;
     }
     */
}

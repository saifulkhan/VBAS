package uk.ac.isc.eventscontrolview;


import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import uk.ac.isc.seisdata.Command;
import uk.ac.isc.seisdata.Global;


public class CommandTableModel extends AbstractTableModel {

    private final String[] columnNames = {
        "Command ID",
        "Analyst", 
        "Command",
        "Status",
        "Type"
    };
    
    private final Class[] columns = new Class[]{
        Integer.class,
        String.class, 
        String.class,
        String.class,
        String.class
    };
    
    public final Object[] longValues = {
        new Integer(999999),
        new String(new char[50]), 
        new String(new char[500]),
        new String(new char[10]),
        new String(new char[10])};
        
    private final ArrayList<Command> commandList;


    CommandTableModel(ArrayList<Command> commandList) {
        this.commandList = commandList;
    }

        
    @Override
    public int getRowCount() {
        return commandList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    // overide it for setting values in each row and each column
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
                
        Object retObject = null;
        
        switch(columnIndex) {
            case 0:
                retObject = commandList.get(rowIndex).getId();
                break;
            case 1:
                retObject = commandList.get(rowIndex).getAnalyst();
                break;
            case 2:
                retObject = commandList.get(rowIndex).getCommand();
                break;
            case 3:
                retObject = commandList.get(rowIndex).getStatus();
                break;
            case 4:
                retObject = commandList.get(rowIndex).getType();
                break;    
            default:
                String message = Global.debugAt() + "\nSee the error log file for more information. ";
                JOptionPane.showMessageDialog(null, message, "Error",  JOptionPane.ERROR_MESSAGE);
        }

        return retObject;
    }

    /*
     * JTable uses this method to determine the default renderer/editor for each cell.  
     * If we didn't implement this method,
     * then the last column would contain text ("true"/"false"), rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        //System.out.println(Global.debugAt() + "c= " + c + ", getValueAt(0, c)=" + getValueAt(0, c));
        return getValueAt(0, c).getClass();        
    }

    // cell is editable.
    // only the select column is editable.
    @Override
    public boolean isCellEditable(int row, int col) {
        /*
        // If the status of the command is "C" (committed), it can not be assesed (selected).
        Object o = getValueAt(row, 3);
        String status = (String) o;
        if(status.equals("C")) {
            System.out.println(Global.debugAt() + " Status= " + status);
        }
        return (status.equals("C"));
        */
       
        return false;
    }

    // when something is selected.
    @Override
    public void setValueAt(Object value, int row, int col) {
        fireTableCellUpdated(row, col);
    }

}
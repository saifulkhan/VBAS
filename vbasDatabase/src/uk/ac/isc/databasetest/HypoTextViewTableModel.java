
package uk.ac.isc.databasetest;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * This is the table model
 * @author hui
 */
class HypoTextViewTableModel extends AbstractTableModel implements SeisDataChangeListener{

    private final String[] columnNames = {"Agency", "Time", 
        "Lat.", "Long.","Depth", "Depth Error","Magnitude 1", "Magnitude 2", "RMS", "Hypid"};
    
    private final Class[] columns = new Class[]{String.class, String.class, String.class, String.class, Double.class, Integer.class, 
        String.class, String.class, Double.class, Integer.class};
    
    private ArrayList<Hypocentre> hyposList;
    
    public HypoTextViewTableModel(ArrayList<Hypocentre> hyposList)
    {
        this.hyposList = hyposList;
    }
    
    public void setHyposList(ArrayList<Hypocentre> hlist)
    {
        this.hyposList = hlist;
    }
    
    @Override
    public int getRowCount() {
        return hyposList.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }
    
    public ArrayList<Hypocentre> getHyposList()
    {
        return this.hyposList;
    }

    @Override
    public Class getColumnClass(int c) {
        return columns[c];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       
        Object retObject = null;
        
        NumberFormat numFormat = DecimalFormat.getInstance();
        numFormat.setMaximumFractionDigits(1);
        numFormat.setMinimumFractionDigits(1); 
        
        if(columnIndex==0)
        {
            retObject = hyposList.get(rowIndex).getAgency();//agency
        }
        else if(columnIndex==1)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            if(hyposList.get(rowIndex).getOrigTime()!=null)
               retObject = dateFormat.format(hyposList.get(rowIndex).getOrigTime()) + "." + hyposList.get(rowIndex).getMsec()/100;
            else
               retObject = null;
        }
        else if(columnIndex==2) //lat
        {
            String latString="";
            if(hyposList.get(rowIndex).getLat()<0)
            {
                latString += numFormat.format(Math.abs(hyposList.get(rowIndex).getLat()));
                latString += "S";
            }
            else
            {
                latString += numFormat.format(hyposList.get(rowIndex).getLat());
                latString += "N";
            }
            retObject = latString;
        }
        else if(columnIndex==3) //lon
        {
            String lonString="";
            if(hyposList.get(rowIndex).getLon()<0)
            {
                lonString += numFormat.format(Math.abs(hyposList.get(rowIndex).getLon()));
                lonString += "W";
            }
            else
            {
                lonString += numFormat.format(hyposList.get(rowIndex).getLon());
                lonString += "E";
            }
            retObject = lonString;
        }
        else if(columnIndex==4) //depth
        {
           retObject = hyposList.get(rowIndex).getDepth();
        }
        else if(columnIndex==5) //depth error
        {
            if(hyposList.get(rowIndex).getErrDepth()!=null)
            {
                retObject = Math.round(hyposList.get(rowIndex).getErrDepth());
            }
            else
            {
                retObject = null;
            }
        }
        else if(columnIndex==6) //mag 1 + type
        {
             if(hyposList.get(rowIndex).getMagnitude().size()>0)
             {
                 Object key = hyposList.get(rowIndex).getMagnitude().keySet().toArray()[0];
                 retObject = hyposList.get(rowIndex).getMagnitude().get(key).toString() + key.toString();
             }
             else
                retObject = null;
        }
        else if(columnIndex==7)
        {
            if(hyposList.get(rowIndex).getMagnitude().size()>1)
            {
                Object key = hyposList.get(rowIndex).getMagnitude().keySet().toArray()[1];
                retObject = hyposList.get(rowIndex).getMagnitude().get(key).toString() + key.toString();
            }
            else
                retObject = null;
        }
        else if(columnIndex==8)
        {
            retObject = Double.valueOf(numFormat.format(hyposList.get(rowIndex).getStime()));
        }
        else
        {
            retObject = hyposList.get(rowIndex).getHypid();
        }
        
        return retObject;
    }

    @Override
    public void SeisDataChanged(SeisDataChangeEvent event) {
        this.fireTableDataChanged();
    }
    
}

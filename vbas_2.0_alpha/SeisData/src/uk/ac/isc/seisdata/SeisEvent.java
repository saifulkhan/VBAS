package uk.ac.isc.seisdata;

import java.util.Date;

/**
 * this is a real earthquake event, it is different with changeEvent which
 * triggers listener as observer model
 *
 */
public class SeisEvent extends AbstractSeisData {

    private Integer evid;           //event id
    private Hypocentre primeHypo;   //reference of prime hypocentre
    private Integer phaseNumber;    //phse number associated with this event
    private Double magnitude;       //a reference magnitude    
    private String location;        //the region name
    private Integer grn;            //these two ref, please ask James
    private Integer srn;
    private Integer defaultDepth;   //default Depth of the event
    private String eType;

    //'Done' will set this to 'now' date. If not null, i.e., the event is done and highlight as gray.  
    private Date finishDate;
    private Double defaultDepthGrid = null;
    
    public SeisEvent() {
        this.evid = 0;
    }

    public SeisEvent(Integer evid, String eType, Double defaultDepthGrid) {
        this.evid = evid;
        this.eType = eType;
        this.defaultDepthGrid = defaultDepthGrid;
        this.phaseNumber = 0;
    
        //VBASLogger.logDebug(this.evid + ", " + this.defaultDepthGrid);    
    }

    public void setValues(SeisEvent e) {
        this.evid = e.evid;
        this.primeHypo = e.primeHypo;
        this.phaseNumber = e.defaultDepth;
        this.magnitude = e.magnitude;
        this.location = e.location;
        this.grn = e.defaultDepth;
        this.srn = e.defaultDepth;
        this.defaultDepth = e.defaultDepth;
        this.eType = e.eType;
        this.defaultDepthGrid = e.defaultDepthGrid;
    }

    public void setEvid(Integer evid) {
        this.evid = evid;
    }

    public Integer getEvid() {
        return this.evid;
    }

    public void setPrimeHypo(Hypocentre prime) {
        this.primeHypo = prime;
    }

    public Hypocentre getPrimeHypo() {
        return this.primeHypo;
    }

    public Integer getPhaseNumber() {
        return this.phaseNumber;
    }

    public void setPhaseNumber(Integer phaseNumber) {
        this.phaseNumber = phaseNumber;
    }

    //normally, there are a lot of magnitude types, we use ISC magnitude here 
    //no matter if it is mb or Ms
    public Double getMagnitude() {
        return this.magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    //the location actually is a seismic region, sorry for the misuse of term
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getGrn() {
        return this.grn;
    }

    public void setGrn(Integer grn) {
        this.grn = grn;
    }

    public Integer getSrn() {
        return this.srn;
    }

    public void setSrn(Integer srn) {
        this.srn = srn;
    }

    public Integer getDefaultDepth() {
        return this.defaultDepth;
    }

    public void setDefaultDepth(Integer defaultDepth) {
        this.defaultDepth = defaultDepth;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Double getDefaultDepthGrid() {
        return defaultDepthGrid;
    }
    
    
}

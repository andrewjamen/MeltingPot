package model;

import java.util.Date;

/**
 *
 * @author Andrew Amen
 */
public class Report {

    private int reportID;
    private String reporter;
    private String offender;
    private String message;
    private String timestamp;

    public Report(int reportID, String reporter, String offender, String message, String timestamp) {
        this.reportID = reportID;
        this.reporter = reporter;
        this.offender = offender;
        this.message = message;
        this.timestamp = timestamp;
    }



    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getOffender() {
        return offender;
    }

    public void setOffender(String offender) {
        this.offender = offender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    

}

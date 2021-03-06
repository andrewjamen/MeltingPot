package controller;

import dao.AdminDAO;
import dao.UserDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Report;
import model.UserBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class AdminController {

    boolean verified;
    Report report;
    ArrayList<String> bannedAccounts = new ArrayList<>();
    ArrayList<Report> reports = new ArrayList<>();

    public AdminController() {
        bannedAccounts = getBannedAccounts();
        reports = getReports();
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public ArrayList<String> getBannedAccounts() {
        bannedAccounts = AdminDAO.findBannedAccounts();
        return bannedAccounts;
    }

    public boolean isBanned(String username) {
        bannedAccounts = getBannedAccounts();

        return bannedAccounts.contains(username);

    }

    public void setBannedAccounts(ArrayList<String> bannedAccounts) {
        this.bannedAccounts = bannedAccounts;
    }

    public ArrayList<Report> getReports() {
        reports = AdminDAO.findAllReports();
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public String banAccount(String username, String nav) {

        UserBean profile = UserDAO.findByUsername(username);
        AdminDAO.banAccount(profile);

        ProfileController pc = new ProfileController();

        if (nav.equals("profile")) {
            return pc.getURL(username);
        }

        return nav;
    }

    public String unbanAccount(String username, String nav) {

        UserBean profile = UserDAO.findByUsername(username);
        AdminDAO.unbanAccount(profile);

        ProfileController pc = new ProfileController();

        if (nav.equals("profile")) {
            return pc.getURL(username);
        }
        return nav;
    }

    public String viewReport(Report aReport) {
        report = aReport;

        return "/Admin/Report.xhtml?faces-redirect=true";
    }

    public String prepareReport() {
        if (report == null) {
            return "/Home/Home.xhtml?faces-redirect=true";
        } else {

            return "";
        }
    }

    public String searchForReport(String reportNumber) {

        try {
            int reportNum = Integer.parseInt(reportNumber);
            ArrayList<Report> reports = AdminDAO.findAllReports();

            Report theReport = null;

            for (Report aReport : reports) {
                if (aReport.getReportID() == reportNum) {
                    theReport = aReport;
                }
            }

            if (theReport != null) {

                report = theReport;

                return "/Admin/Report.xhtml?faces-redirect=true";
            }
            else return "";

        } catch (Exception e) {
            return "";
        }
    }

}

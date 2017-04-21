package controller;

import model.UnivBean;

/**
 *
 * @author Andrew
 */
public class UnivAccountController {
    
    private UnivBean univModel;
    private String accountInfo;
    
    public UnivAccountController(UnivBean univModel) {
        this.univModel = univModel;
    }

    public UnivBean getUnivModel() {
        return univModel;
    }

    public void setUnivModel(UnivBean univModel) {
        this.univModel = univModel;
    }

    public String getAccountInfo() {
        accountInfo = ""
                +univModel.getName() + "'s Account:" 
                + "Location" + "<br/>"
                + "Address: " + univModel.getAddress() + "<br/>"
                + "City: " + univModel.getCity() + "<br/>"
                + "State: " + univModel.getAddress() + "<br/><br/>"
                + "Contact Info <br/>"
                + "Email: " + univModel.getEmail() + "<br/><br/>"
                + "Statistics <br/>"
                + "Average Accepted GPA: " + univModel.getAvgGpa() + "<br/>"
                + "Average Accepted ACT: " + univModel.getAvgAct() + "<br/>";

        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
    
    
}

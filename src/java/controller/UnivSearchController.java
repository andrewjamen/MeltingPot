package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UnivBean;

/**
 *
 * @author jfoss
 */
@ManagedBean
@SessionScoped
public class UnivSearchController {
    private String results;
    private UnivBean theModel;
    
    public UnivSearchController() {
        theModel = new UnivBean();
    }

    public UnivBean getTheModel() {
        return theModel;
    }

    public void setTheModel(UnivBean theModel) {
        this.theModel = theModel;
    }

    public String getResults() {
        UnivDAO univDAO = new UnivDAO();
        String resultStr = "";
        
        ArrayList<UnivBean> users = univDAO.searchForUsers(theModel.getUsername(), theModel.getName(), theModel.getState(), theModel.getAvgAct(), theModel.getAvgGpa());
        
        if(!users.isEmpty()){
            for(int i=0; i<users.size(); i++){
                resultStr += users.get(i).getUsername() + " - " + users.get(i).getName() + "<br/>";
            }
        } else{
            resultStr += "Nothing found! No universities match the given parameters";
        }
        results = resultStr;
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
    
    public String search(){
        if(!theModel.getUsername().equals("") || !theModel.getName().equals("") || !theModel.getState().equals("")
                || theModel.getAvgAct() != 0 || theModel.getAvgGpa() != 0.0){
            return "UnivResults.xhtml";
        } else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Please provide information before searching"));
            return "UnivSearch.xhtml";
        }
    }
}

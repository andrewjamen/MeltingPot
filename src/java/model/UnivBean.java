/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Andrew
 */
public class UnivBean {
    
    private String name;
    private String userName;
    private String password;
    private String confirm;
    private String email;
    private String address;
    private String city;
    private String state;
    private String avgAct;
    private String avgGpa;
    
    public UnivBean(){
    }

    public UnivBean(String name, String userName, String password, String email, String address, String city, String state, String avgAct, String avgGpa) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.avgAct = avgAct;
        this.avgGpa = avgGpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAvgAct() {
        return avgAct;
    }

    public void setAvgAct(String avgAct) {
        this.avgAct = avgAct;
    }

    public String getAvgGpa() {
        return avgGpa;
    }

    public void setAvgGpa(String avgGpa) {
        this.avgGpa = avgGpa;
    }
    
    
}

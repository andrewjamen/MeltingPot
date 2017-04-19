/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.UnivDAO;

/**
 *
 * @author Andrew
 */
public class UnivBean{
    
    private String username;
    private String password;
    private String name;
    private String confirm;
    private String email;
    private String address;
    private String city;
    private String state;
    private String avgAct;
    private String avgGpa;
    
    public UnivBean(){
    }

    public UnivBean(String name, String username, String password, String email, String address, String city, String state, String avgAct, String avgGpa) {
        
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.avgAct = avgAct;
        this.avgGpa = avgGpa;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


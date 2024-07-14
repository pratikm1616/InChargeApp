package com.Inchargenext.loginactivity;

public class StoringData {
    String name;
    String user;
    String email;
    String phoneno;
    String password;
    String confirmpassword;
    String uid;
    String url;
    String CarCompany;
    String Carname;

    public StoringData(String emails, String passwords) {
    }

    public StoringData() {
    }

    public StoringData(String name, String user, String email, String phoneno, String password) {
        this.name = name;
        this.user = user;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;
        this.uid = uid;
        this.url = url;
    }

    public String getCarCompany() {
        return CarCompany;
    }

    public void setCarCompany(String carCompany) {
        CarCompany = carCompany;
    }

    public String getCarname() {
        return Carname;
    }

    public void setCarname(String carname) {
        Carname = carname;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

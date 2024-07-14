package com.Inchargenext.loginactivity;

public class Hour {
    private String Driver;
    private String choosen;
    private String DriverName;

    public Hour(String patient) {
        this.Driver = Driver;
        choosen = "false";
    }

    public Hour() {

    }

    public String getPatientName() {
        return DriverName;
    }

    public void setPatientName(String patientName) {
        this.DriverName = patientName;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        this.Driver = driver;
    }

    public String getChoosen() {
        return choosen;
    }

    public void setChoosen(String choosen) {
        this.choosen = choosen;
    }
}

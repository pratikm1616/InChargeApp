package com.Inchargenext.loginactivity;

public class PlacesModel {
    private String Name;
    private String Address;
    private String open;
    private String close;

    public PlacesModel(String name, String Address, String open, String close) {
        Name = name;
        this.Address = Address;
        this.open = open;
        this.close = close;
    }

    public PlacesModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }


}

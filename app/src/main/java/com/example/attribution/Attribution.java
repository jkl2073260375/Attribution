package com.example.attribution;

public class Attribution {
    private int id;
    private String areaCode;
    private String address;

    public Attribution() {
    }

    public Attribution(int id, String areaCode, String address) {
        this.id = id;
        this.areaCode = areaCode;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}

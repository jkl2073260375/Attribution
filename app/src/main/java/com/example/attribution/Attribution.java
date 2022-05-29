package com.example.attribution;

public class Attribution {
    private int id;
    private String name;
    private String tel;
    private String areaCode;
    private String address;

    public Attribution() {
    }

    public Attribution(int id, String name, String tel, String areaCode, String address) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.areaCode = areaCode;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    @Override
    public String toString() {
        return "Attribution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

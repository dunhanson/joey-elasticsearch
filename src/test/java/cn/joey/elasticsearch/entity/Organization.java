package cn.joey.elasticsearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Organization {
    private Long id;
    private String[] labels;
    private String area;
    private String province;
    private String city;
    private String district;
    private String name;
    @SerializedName("nicknames")
    private String nickNames;

    public Organization() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickNames() {
        return nickNames;
    }

    public void setNickNames(String nickNames) {
        this.nickNames = nickNames;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", labels=" + Arrays.toString(labels) +
                ", area='" + area + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", name='" + name + '\'' +
                ", nickNames='" + nickNames + '\'' +
                '}';
    }
}

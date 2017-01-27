package com.example.rndroid.zomato_app;

/**
 * Created by rndroid on 26/1/17.
 */

public class Restaurant_Pojo {
    private String name, locality, address, imageurl, latitude, longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Restaurant_Pojo(String name, String locality, String address, String imageurl, String latitude, String longitude) {
        this.name = name;
        this.locality = locality;
        this.address = address;
        this.imageurl = imageurl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

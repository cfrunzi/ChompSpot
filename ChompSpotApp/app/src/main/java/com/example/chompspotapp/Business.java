package com.example.chompspotapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Business implements Parcelable {
    private String name, type, hours, distance, busy, address, phone;
    private double latitude, longitude;
    private int[] traffic = new int[168];
    public Business(Business param1){
        // empty
    }
    public Business(String name, String type, String hours, String distance, String busy, String address, String phone, double latitude, double longitude, int[] traffic) {
        this.name = name;
        this.type = type;
        this.hours = hours;
        this.distance = distance;
        this.busy = busy;
        this.address = address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;

        for(int i = 0; i < 168; i++){
            this.traffic[i]= traffic[i];
        }
    }

    protected Business(Parcel in) {
        name = in.readString();
        type = in.readString();
        hours = in.readString();
        distance = in.readString();
        busy = in.readString();
        latitude = Double.valueOf(in.readString());
        longitude = Double.valueOf(in.readString());
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel in) {
            return new Business(in);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };

    public Business copy(){
        return new Business(this.name, this.type, this.hours, this.distance, this.busy, this.address, this.phone, this.latitude, this.longitude, this.traffic);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setBusy(String busy) {
        this.busy = busy;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getHours() {
        return hours;
    }

    public String getDistance() {
        return distance;
    }

    public String getBusy() {
        return busy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.type);
        parcel.writeString(this.distance);
        parcel.writeString(this.hours);
        parcel.writeString(String.valueOf(this.latitude));
        parcel.writeString(String.valueOf(this.longitude));
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTrafficNode(int index){
        return traffic[index];
    }
}

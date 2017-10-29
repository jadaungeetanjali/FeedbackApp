package com.silive.pc.feedbackapp.Models;

/**
 * Created by PC on 10/28/2017.
 */

public class Students {
    private String name, designation, organisation, organisationAddress, email, mobile, feedback, signature, photo, date, time;

    public Students(){

    }

    public Students(String name, String designation, String organisation, String organisationAddress,
                    String email, String mobile, String feedback, String signature, String photo, String date, String time){
        this.name = name;
        this.designation = designation;
        this.organisation = organisation;
        this.organisationAddress = organisationAddress;
        this.email = email;
        this.mobile = mobile;
        this.feedback = feedback;
        this.signature = signature;
        this.photo = photo;
        this.date = date;
        this.time = time;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getOrganisationAddress() {
        return organisationAddress;
    }

    public void setOrganisationAddress(String organisationAddress) {
        this.organisationAddress = organisationAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

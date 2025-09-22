package com.hemoplus.models;

public class Donation {
    private int id;
    private int donorId;
    private int recipientId;
    private String status;
    private String donationDate;
    private String location;

    public Donation() {}

    public Donation(int donorId, int recipientId, String donationDate, String location) {
        this.donorId = donorId;
        this.recipientId = recipientId;
        this.donationDate = donationDate;
        this.location = location;
        this.status = "pending";
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDonorId() { return donorId; }
    public void setDonorId(int donorId) { this.donorId = donorId; }

    public int getRecipientId() { return recipientId; }
    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDonationDate() { return donationDate; }
    public void setDonationDate(String donationDate) { this.donationDate = donationDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}

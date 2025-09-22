
package com.hemoplus.models;

public class BloodRequest {
    private int id;
    private int recipientId;
    private String bloodType;
    private String urgency;
    private String location;
    private String description;
    private String status;

    public BloodRequest() {}

    public BloodRequest(int recipientId, String bloodType, String urgency, String location, String description) {
        this.recipientId = recipientId;
        this.bloodType = bloodType;
        this.urgency = urgency;
        this.location = location;
        this.description = description;
        this.status = "active";
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRecipientId() { return recipientId; }
    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

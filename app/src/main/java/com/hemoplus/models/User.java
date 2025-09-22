package com.hemoplus.models;

public class User {
    private int id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String bloodType;
    private String userType;

    public User() {}

    public User(String email, String password, String fullName, String phone, String bloodType, String userType) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.bloodType = bloodType;
        this.userType = userType;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}

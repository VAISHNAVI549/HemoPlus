package com.hemoplus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hemoplus.models.BloodRequest;
import com.hemoplus.models.Donation;
import com.hemoplus.models.User;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HemoPlus.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_DONATIONS = "donations";
    private static final String TABLE_BLOOD_REQUESTS = "blood_requests";

    // Users table columns
    private static final String COL_ID = "id";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_FULL_NAME = "full_name";
    private static final String COL_PHONE = "phone";
    private static final String COL_BLOOD_TYPE = "blood_type";
    private static final String COL_USER_TYPE = "user_type";
    private static final String COL_CREATED_AT = "created_at";

    // Donations table columns
    private static final String COL_DONOR_ID = "donor_id";
    private static final String COL_RECIPIENT_ID = "recipient_id";
    private static final String COL_STATUS = "status";
    private static final String COL_DONATION_DATE = "donation_date";
    private static final String COL_LOCATION = "location";

    // Blood requests table columns
    private static final String COL_URGENCY = "urgency";
    private static final String COL_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_FULL_NAME + " TEXT NOT NULL, " +
                COL_PHONE + " TEXT NOT NULL, " +
                COL_BLOOD_TYPE + " TEXT NOT NULL, " +
                COL_USER_TYPE + " TEXT NOT NULL, " +
                COL_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        // Create donations table
        String createDonationsTable = "CREATE TABLE " + TABLE_DONATIONS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DONOR_ID + " INTEGER, " +
                COL_RECIPIENT_ID + " INTEGER, " +
                COL_STATUS + " TEXT DEFAULT 'pending', " +
                COL_DONATION_DATE + " TEXT, " +
                COL_LOCATION + " TEXT, " +
                COL_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (" + COL_DONOR_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_ID + "), " +
                "FOREIGN KEY (" + COL_RECIPIENT_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_ID + "))";

        // Create blood requests table
        String createBloodRequestsTable = "CREATE TABLE " + TABLE_BLOOD_REQUESTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPIENT_ID + " INTEGER, " +
                COL_BLOOD_TYPE + " TEXT NOT NULL, " +
                COL_URGENCY + " TEXT NOT NULL, " +
                COL_LOCATION + " TEXT NOT NULL, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_STATUS + " TEXT DEFAULT 'active', " +
                COL_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (" + COL_RECIPIENT_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_ID + "))";

        db.execSQL(createUsersTable);
        db.execSQL(createDonationsTable);
        db.execSQL(createBloodRequestsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOOD_REQUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error hashing password", e);
            return password;
        }
    }

    // User operations
    public long registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, hashPassword(user.getPassword()));
        values.put(COL_FULL_NAME, user.getFullName());
        values.put(COL_PHONE, user.getPhone());
        values.put(COL_BLOOD_TYPE, user.getBloodType());
        values.put(COL_USER_TYPE, user.getUserType());

        try {
            return db.insert(TABLE_USERS, null, values);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error registering user", e);
            return -1;
        } finally {
            db.close();
        }
    }

    public User authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedPassword = hashPassword(password);

        Cursor cursor = db.query(TABLE_USERS,
                null,
                COL_EMAIL + "=? AND " + COL_PASSWORD + "=?",
                new String[]{email, hashedPassword},
                null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
            user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(COL_FULL_NAME)));
            user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)));
            user.setBloodType(cursor.getString(cursor.getColumnIndexOrThrow(COL_BLOOD_TYPE)));
            user.setUserType(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_TYPE)));
        }

        cursor.close();
        db.close();
        return user;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FULL_NAME, user.getFullName());
        values.put(COL_PHONE, user.getPhone());

        int rowsAffected = db.update(TABLE_USERS, values, COL_ID + "=?",
                new String[]{String.valueOf(user.getId())});
        db.close();
        return rowsAffected > 0;
    }

    // Blood request operations
    public long createBloodRequest(BloodRequest request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RECIPIENT_ID, request.getRecipientId());
        values.put(COL_BLOOD_TYPE, request.getBloodType());
        values.put(COL_URGENCY, request.getUrgency());
        values.put(COL_LOCATION, request.getLocation());
        values.put(COL_DESCRIPTION, request.getDescription());

        long result = db.insert(TABLE_BLOOD_REQUESTS, null, values);
        db.close();
        return result;
    }

    public List<BloodRequest> getCompatibleBloodRequests(String donorBloodType) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BloodRequest> requests = new ArrayList<>();

        String[] compatibleTypes = getCompatibleBloodTypes(donorBloodType);

        StringBuilder selection = new StringBuilder(COL_STATUS + "='active' AND (");
        String[] selectionArgs = new String[compatibleTypes.length];

        for (int i = 0; i < compatibleTypes.length; i++) {
            if (i > 0) selection.append(" OR ");
            selection.append(COL_BLOOD_TYPE + "=?");
            selectionArgs[i] = compatibleTypes[i];
        }
        selection.append(")");

        Cursor cursor = db.query(TABLE_BLOOD_REQUESTS, null, selection.toString(),
                selectionArgs, null, null, COL_URGENCY + " DESC");

        while (cursor.moveToNext()) {
            BloodRequest request = new BloodRequest();
            request.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            request.setRecipientId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_RECIPIENT_ID)));
            request.setBloodType(cursor.getString(cursor.getColumnIndexOrThrow(COL_BLOOD_TYPE)));
            request.setUrgency(cursor.getString(cursor.getColumnIndexOrThrow(COL_URGENCY)));
            request.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)));
            request.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
            requests.add(request);
        }

        cursor.close();
        db.close();
        return requests;
    }

    public List<BloodRequest> getUserBloodRequests(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BloodRequest> requests = new ArrayList<>();

        Cursor cursor = db.query(TABLE_BLOOD_REQUESTS, null,
                COL_RECIPIENT_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, COL_CREATED_AT + " DESC");

        while (cursor.moveToNext()) {
            BloodRequest request = new BloodRequest();
            request.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            request.setRecipientId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_RECIPIENT_ID)));
            request.setBloodType(cursor.getString(cursor.getColumnIndexOrThrow(COL_BLOOD_TYPE)));
            request.setUrgency(cursor.getString(cursor.getColumnIndexOrThrow(COL_URGENCY)));
            request.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)));
            request.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
            request.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS)));
            requests.add(request);
        }

        cursor.close();
        db.close();
        return requests;
    }

    private String[] getCompatibleBloodTypes(String donorBloodType) {
        switch (donorBloodType) {
            case "O+":
                return new String[]{"O+", "A+", "B+", "AB+"};
            case "O-":
                return new String[]{"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
            case "A+":
                return new String[]{"A+", "AB+"};
            case "A-":
                return new String[]{"A+", "A-", "AB+", "AB-"};
            case "B+":
                return new String[]{"B+", "AB+"};
            case "B-":
                return new String[]{"B+", "B-", "AB+", "AB-"};
            case "AB+":
                return new String[]{"AB+"};
            case "AB-":
                return new String[]{"AB+", "AB-"};
            default:
                return new String[]{};
        }
    }

    public long createDonation(Donation donation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DONOR_ID, donation.getDonorId());
        values.put(COL_RECIPIENT_ID, donation.getRecipientId());
        values.put(COL_DONATION_DATE, donation.getDonationDate());
        values.put(COL_LOCATION, donation.getLocation());

        long result = db.insert(TABLE_DONATIONS, null, values);
        db.close();
        return result;
    }
}
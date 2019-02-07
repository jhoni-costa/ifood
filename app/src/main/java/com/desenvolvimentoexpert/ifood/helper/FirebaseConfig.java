package com.desenvolvimentoexpert.ifood.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConfig {

    private static DatabaseReference firebaseRef;
    private static FirebaseAuth firebaseAuth;
    private static StorageReference storageRef;

    public static String getIdUser() {
        return getFirebaseAuth().getCurrentUser().getUid();
    }

    public static DatabaseReference getFirebaseRef() {
        if (firebaseRef == null) {
            firebaseRef = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseRef;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static StorageReference getStorageRef() {
        if (storageRef == null) {
            storageRef = FirebaseStorage.getInstance().getReference();
        }
        return storageRef;
    }
}

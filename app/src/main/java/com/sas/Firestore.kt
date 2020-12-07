package com.sas

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Firestore {
    val instance = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance().currentUser
}
package com.sas.hackathonsocs.model

import android.os.Parcel
import android.os.Parcelable

class User(
    var name:String,
    var phoneNumber:String,
    var email:String,
    val gender:String
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }
    constructor() : this("","","",""){}


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p: Parcel?, p1: Int) {
        p!!.writeString(name)
        p!!.writeString(phoneNumber)
        p!!.writeString(email)
        p!!.writeString(gender)
    }


    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

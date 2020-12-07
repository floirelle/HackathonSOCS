package com.sas.hackathonsocs.model

import android.os.Parcel
import android.os.Parcelable

class Company(

    val name:String,
    val type:String,
    val link:HashMap<String,String> = hashMapOf()
): Parcelable{
    constructor(parcel: Parcel) : this(

        parcel.readString()!!,
        parcel.readString()!!
    ) {
        val size = parcel.readInt()
        for (i in 0..size)
        {
            link[parcel.readString()!!] = parcel.readString()!!
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p: Parcel?, p1: Int) {

        p!!.writeString(name)
        p!!.writeString(type)
        for ((key, value) in link) {
            p.writeString(key)
            p.writeString(value)
        }
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

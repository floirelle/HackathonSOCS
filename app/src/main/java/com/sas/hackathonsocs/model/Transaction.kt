package com.sas.hackathonsocs.model

import android.os.Parcel
import android.os.Parcelable

class Transaction(
    val transactionDate: Long,
    val description :String,
    val type:String,
    val status:String,
    val nominal:Long,
    val reminder:String
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()!!,
        parcel.readString()!!
        ) {
    }
    constructor() : this(0,"","","",0,""){}
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(transactionDate)
        parcel.writeString(description)
        parcel.writeString(type)
        parcel.writeString(status)
        parcel.writeLong(nominal)
        parcel.writeString(reminder)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }

}
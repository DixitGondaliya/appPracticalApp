package com.app.qfonapppracticalapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Datum {
    @SerializedName("__v")
    private var _V: Long? = null

    @Expose
    var _id: String? = null

    @Expose
    var airline: List<Airline>? = null

    @Expose
    var name: String? = null

    @Expose
    var trips: Long? = null
}
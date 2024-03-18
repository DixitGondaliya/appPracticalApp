package com.app.qfonapppracticalapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Airline {

    @Expose
    var country: String? = null

    @Expose
    var established: String? = null

    @SerializedName("head_quaters")
    var headQuaters: String? = null

    @Expose
    var id: Long? = null

    @Expose
    var logo: String? = null

    @Expose
    var name: String? = null

    @Expose
    var slogan: String? = null

    @Expose
    var website: String? = null
}
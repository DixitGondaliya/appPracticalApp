package com.app.qfonapppracticalapp.model

import com.google.gson.annotations.Expose

class AirwaysModelClass {
    @Expose
    var data: List<Datum>? = null

    @Expose
    var pagination: Pagination? = null
}
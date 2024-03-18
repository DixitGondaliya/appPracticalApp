package com.app.qfonapppracticalapp.model

import com.google.gson.annotations.Expose


class Link {
    @Expose
    var href: String? = null

    @Expose
    var page: Long? = null

    @Expose
    var rel: String? = null
}
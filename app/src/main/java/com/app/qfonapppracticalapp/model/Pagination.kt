package com.app.qfonapppracticalapp.model

import com.google.gson.annotations.Expose

class Pagination {
    @Expose
    var currentItems: Long? = null

    @Expose
    var currentPage: Long? = null

    @Expose
    var links: List<Link>? = null

    @Expose
    var totalItems: Long? = null

    @Expose
    var totalPages: Long? = null
}
package com.fyznur.itunessearchcase.data.network


import com.fyznur.itunessearchcase.data.model.SearchData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoints {

    @GET("search")
    fun getList(
        @Query("term") term: String,
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("media") media: String
    ): Observable<SearchData>

}
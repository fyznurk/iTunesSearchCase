package com.fyznur.itunessearchcase.data.model

import java.io.Serializable

data class SearchData(
    val resultCount: Int?,
    val results: ArrayList<ItemDetail?>?
) : Serializable

data class ItemDetail(
    val wrapperType: String?,
    val artworkUrl100: String?,
    val collectionPrice: Double?,
    val collectionName: String?,
    val releaseDate: String?,
    val currency: String?,
    val longDescription: String?,
    val artistName: String?
) : Serializable

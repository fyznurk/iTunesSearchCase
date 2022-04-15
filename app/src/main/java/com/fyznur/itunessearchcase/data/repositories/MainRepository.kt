package com.fyznur.itunessearchcase.data.repositories

import androidx.lifecycle.MutableLiveData
import com.fyznur.itunessearchcase.data.model.ItemDetail
import com.fyznur.itunessearchcase.data.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainRepository constructor(private val api: Api) {
    fun getList(
        term: String,
        page: Int,
        size: Int,
        media: String,
        list: MutableLiveData<ArrayList<ItemDetail?>>
    ): Any? =
        api.endpoints().getList(term, page, size, media).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list.value = it.results
            }, {
                list.value = null
            })
}
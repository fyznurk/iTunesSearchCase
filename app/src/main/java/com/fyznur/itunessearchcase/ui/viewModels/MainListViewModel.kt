package com.fyznur.itunessearchcase.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fyznur.itunessearchcase.data.model.ItemDetail
import com.fyznur.itunessearchcase.data.repositories.MainRepository

class MainListViewModel(private val repository: MainRepository) : ViewModel() {

    private val _list = MutableLiveData<ArrayList<ItemDetail?>>()
    val list = _list

    fun getAllList(term: String, page: Int, size: Int, media: String) {
         repository.getList(term, page, size, media, _list)
    }
}
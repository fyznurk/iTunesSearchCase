package com.fyznur.itunessearchcase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fyznur.itunessearchcase.R
import com.fyznur.itunessearchcase.adapter.MainListAdapter
import com.fyznur.itunessearchcase.adapter.PaginationScrollListener
import com.fyznur.itunessearchcase.adapter.RecyclerViewItemClick
import com.fyznur.itunessearchcase.data.model.ItemDetail
import com.fyznur.itunessearchcase.data.network.Api
import com.fyznur.itunessearchcase.data.repositories.MainRepository
import com.fyznur.itunessearchcase.ui.viewModels.MainListViewModel
import com.fyznur.itunessearchcase.ui.viewModels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Items are listed in this page with search query and type
 */
class MainListFragment : Fragment(), RecyclerViewItemClick {

    private lateinit var mainViewModel: MainListViewModel
    private var adapter: MainListAdapter? = null
    private var data: ArrayList<ItemDetail?>? = null
    private var layoutManager: GridLayoutManager? = null
    private var term: String? = ""
    private var page = 1
    private var media: String? = "movie"
    private var mediaTypes = arrayListOf("movie", "music", "ebook", "podcast")
    private var isLoading = false
    private var isLastPage = false
    private val totalPage = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = GridLayoutManager(this.context, 2)
        rvList.layoutManager = layoutManager
        rvList.addOnScrollListener(object : PaginationScrollListener(layoutManager!!) {
            override fun loadMoreItems() {
                isLoading = true
                page += 1
                getList()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(Api()))).get(
            MainListViewModel::class.java
        )

        getInfoFromViewModel()
        initToolbar()
        term = searchView.query.toString()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                term = newText
                page = 1
                adapter?.clearAll()
                getList()
                return false
            }
        })

        rdGroup.setOnCheckedChangeListener { _, i ->
            val radioButton: View = rdGroup.findViewById(i)
            media = mediaTypes[rdGroup.indexOfChild(radioButton)]
            page = 1
            adapter?.clearAll()
            getList()
        }
    }


    private fun getList() {
        if (term?.length ?: 0 >= 2) {
            mainViewModel.getAllList(term ?: "", page, 20, media ?: "")
        } else {
            adapter?.clearAll()
            page = 1
        }
    }

    // initialize toolbar
    private fun initToolbar() {
        toolbarTitle.text = getString(R.string.search)
        backIcon.visibility = View.GONE
    }

    // subscribe viewModel live data
    private fun getInfoFromViewModel() {
        mainViewModel.list.observe(viewLifecycleOwner) {
            if (it != null && it.size > 0) {
                data = it
                if (page == 1) {
                    adapter = MainListAdapter(data, this@MainListFragment)
                    rvList.adapter = adapter
                } else {
                    adapter?.removeLoadingFooter()
                    isLoading = false
                    adapter?.addAll(data)
                }
                if (page != totalPage) adapter?.addLoadingFooter()
                else isLastPage = true
            } else {
                adapter?.clearAll()
                Toast.makeText(requireContext(), getString(R.string.no_result), Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    // redirect to detail screen when item click
    override fun onItemClick(position: Int) {
        val bundle = bundleOf("data" to (adapter?.data?.get(position)))
        findNavController().navigate(R.id.action_mainListFragment_to_detailFragment, bundle)
    }
}
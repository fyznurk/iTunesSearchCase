package com.fyznur.itunessearchcase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fyznur.itunessearchcase.R
import com.fyznur.itunessearchcase.data.model.ItemDetail
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Detail page of clicked item
 */
class DetailFragment : Fragment() {

    private var data: ItemDetail? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        data = arguments?.getSerializable("data") as ItemDetail
        Glide.with(requireContext())
            .load(data?.artworkUrl100)
            .into(image)
        txtTitle.text = data?.collectionName ?: "-"
        txtDescription.text = data?.longDescription
        txtArtistName.text = data?.artistName ?: "-"
    }

    // initialize toolbar
    private fun initToolbar() {
        toolbarTitle.text = getString(R.string.detail)
        backIcon.visibility = View.VISIBLE
        backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
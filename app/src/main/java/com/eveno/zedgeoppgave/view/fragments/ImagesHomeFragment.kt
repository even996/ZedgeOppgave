package com.eveno.zedgeoppgave.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.adapters.ImageAdapter
import com.eveno.zedgeoppgave.util.Resource
import com.eveno.zedgeoppgave.view.ImageViewModel
import com.eveno.zedgeoppgave.view.ImagesActivity
import kotlinx.android.synthetic.main.fragment_images_home.*

class ImagesHomeFragment : Fragment(R.layout.fragment_images_home) {

    lateinit var viewModel: ImageViewModel
    lateinit var imageAdapter: ImageAdapter
    val TAG = "NewImagesFragment"



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ImagesActivity).viewModel
        setupRecyclerView()

        viewModel.newImages.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { imageResponse ->
                        imageAdapter.differ.submitList(imageResponse.hits)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured : $message")

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })

    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        imageAdapter = ImageAdapter()
        rvImagesHome.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}
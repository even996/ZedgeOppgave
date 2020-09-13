package com.eveno.zedgeoppgave.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.adapters.ImageAdapter
import com.eveno.zedgeoppgave.util.Constants.Companion.QUERY_PAGE_SIZE
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

        imageAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("hit", it)
            }
            findNavController().navigate(
                R.id.action_imagesHomeFragment_to_imagesMoreInfoFragment,
                bundle
            )
        }

        viewModel.newImages.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { imageResponse ->
                        imageAdapter.differ.submitList(imageResponse.hits.toList())
                        /**
                         * denne må kansje byttes
                         */
                        val totalPages = imageResponse.totalHits / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.newImagesPage == totalPages
                        if(isLastPage){
                            rvImagesHome.setPadding(0,0,0,0)
                        }
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
        isLoading = false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotATBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotATBeginning
                    && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getNewImages()
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }
    }


    private fun setupRecyclerView(){
        imageAdapter = ImageAdapter()
        rvImagesHome.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@ImagesHomeFragment.scrollListener)
        }
    }


}
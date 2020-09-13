package com.eveno.zedgeoppgave.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.adapters.ImageAdapter
import com.eveno.zedgeoppgave.util.Constants
import com.eveno.zedgeoppgave.util.Constants.Companion.SEARCH_IMAGE_TIME_DELAY
import com.eveno.zedgeoppgave.util.Resource
import com.eveno.zedgeoppgave.view.ImageViewModel
import com.eveno.zedgeoppgave.view.ImagesActivity
import kotlinx.android.synthetic.main.fragment_images_home.*
import kotlinx.android.synthetic.main.fragment_search_images.*
import kotlinx.android.synthetic.main.fragment_search_images.paginationProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ImagesSearchFragment : Fragment(R.layout.fragment_search_images) {

    lateinit var viewModel: ImageViewModel

    lateinit var imageAdapter : ImageAdapter

    val TAG = "ImagesSearchFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ImagesActivity).viewModel
        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("hit", it)
            }
            findNavController().navigate(
                R.id.action_imagesSearchFragment_to_imagesMoreInfoFragment,
                bundle
            )
        }

        var job: Job? = null
        etSearch.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_IMAGE_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchImages(editable.toString())
                    }
                }

            }
        }

        viewModel.searchImages.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { imageResponse ->
                        imageAdapter.differ.submitList(imageResponse.hits.toList())
                        val totalPages = imageResponse.totalHits / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchImagePage == totalPages
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
        isLoading=false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
        isLoading=true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotATBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotATBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.searchImages(etSearch.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    private fun setupRecyclerView(){
        imageAdapter = ImageAdapter()
        rvSearchImages.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@ImagesSearchFragment.scrollListener)
        }
    }
}
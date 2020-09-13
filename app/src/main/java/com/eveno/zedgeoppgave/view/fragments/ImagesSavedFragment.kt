package com.eveno.zedgeoppgave.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.adapters.ImageAdapter
import com.eveno.zedgeoppgave.view.ImageViewModel
import com.eveno.zedgeoppgave.view.ImagesActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_images.*
import kotlinx.android.synthetic.main.fragment_search_images.*

class ImagesSavedFragment : Fragment(R.layout.fragment_saved_images) {

    lateinit var viewModel: ImageViewModel

    lateinit var imageAdapter : ImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ImagesActivity).viewModel
        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("hit", it)
            }
            findNavController().navigate(
                R.id.action_imagesSavedFragment_to_imagesMoreInfoFragment,
                bundle
            )
        }

        val itemTouchHelperCallback = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val hit = imageAdapter.differ.currentList[position]
                viewModel.deleteImage(hit)

                Snackbar.make(view, "Successfully deleted image", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveImage(hit)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedImages)
        }

        viewModel.getSavedImages().observe(viewLifecycleOwner, Observer { hits ->
            imageAdapter.differ.submitList(hits)
        })

    }

    private fun setupRecyclerView(){
        imageAdapter = ImageAdapter()
        rvSavedImages.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
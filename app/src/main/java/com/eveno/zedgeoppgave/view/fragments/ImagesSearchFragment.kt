package com.eveno.zedgeoppgave.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.view.ImageViewModel
import com.eveno.zedgeoppgave.view.ImagesActivity

class ImagesSearchFragment : Fragment(R.layout.fragment_search_images) {

    lateinit var viewModel: ImageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ImagesActivity).viewModel

    }


}
package com.eveno.zedgeoppgave.view.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.view.ImageViewModel
import com.eveno.zedgeoppgave.view.ImagesActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_more_info.*

class ImagesMoreInfoFragment : Fragment(R.layout.fragment_more_info) {

    lateinit var viewModel: ImageViewModel
    val args: ImagesMoreInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ImagesActivity).viewModel
        val hit = args.hit
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(hit.pageURL)
        }
        fav_button.setOnClickListener {
            viewModel.saveImage(hit)
            Snackbar.make(view, "Image saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}
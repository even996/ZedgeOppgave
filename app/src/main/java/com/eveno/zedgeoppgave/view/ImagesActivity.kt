package com.eveno.zedgeoppgave.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
//import com.eveno.zedgeoppgave.database.ImageDatabase
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.db.ImageDatabase
import com.eveno.zedgeoppgave.repository.ImageRepository
import kotlinx.android.synthetic.main.activity_images.*

class ImagesActivity : AppCompatActivity() {

    lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)


        val imageRepository = ImageRepository(ImageDatabase(this))
        val viewModelProviderFactory = ImageViewModelProviderFactory(imageRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(ImageViewModel ::class.java)
        bottomNavigationView.setupWithNavController(homeImagesFragmentNavHost.findNavController())
    }
}
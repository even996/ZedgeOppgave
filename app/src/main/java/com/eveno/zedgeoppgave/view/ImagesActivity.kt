package com.eveno.zedgeoppgave.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eveno.zedgeoppgave.R
import kotlinx.android.synthetic.main.activity_images.*

class ImagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        bottomNavigationView.setupWithNavController(homeImagesFragmentNavHost.findNavController())
    }
}
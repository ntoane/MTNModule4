package com.example.hardwareapis

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.fragment_photos.*


class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private var mInterstitialAd: InterstitialAd? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Disable the button
        button.isEnabled = false;

        if (checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 111)
        } else {
            button.isEnabled = true

            //Load camera
            button.setOnClickListener {
                //Now, Load the Camera Intent
                var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 101)
            }
        }
        //Load Ad
        val adRequest = AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101) {
            var image: Bitmap? = data?.getParcelableExtra("data")
            imageView.setImageBitmap(image)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            button.isEnabled = true
        }
    }
}
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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
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
                //initializing the Google Admob SDK
                MobileAds.initialize(context)

                //Initialize mInterstitialAd object and set Ad Unit Id
                mInterstitialAd = InterstitialAd(context)
                mInterstitialAd!!.adUnitId = "ca-app-pub-3940256099942544/1033173712"

                //Load an Interstitial ad
                mInterstitialAd!!.loadAd(AdRequest.Builder().build())

                if (mInterstitialAd!!.isLoaded) {
                    mInterstitialAd!!.show()
                } else {
                    Toast.makeText ( context, "Failed to Load Interstitial Ad, please click Take Photo button again", Toast.LENGTH_LONG).show()
                }

                /*mInterstitialAd!!.adListener = object: AdListener() {
                    override fun onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                        Toast.makeText ( context, "Ad Finished loading", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdFailedToLoad(errorCode: Int) {
                        // Code to be executed when an ad request fails.
                        super.onAdFailedToLoad(errorCode);
                        Toast.makeText ( context, "Ad request failed", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdOpened() {
                        // Code to be executed when the ad is displayed.
                        Toast.makeText ( context, "Displaying Ad", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                        Toast.makeText ( context, "You clicked to open an Ad", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                        Toast.makeText ( context, "You left the Interstitial Ad App", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdClosed() {
                        // Code to be executed when the interstitial ad is closed.
                        Toast.makeText ( context, "Ad closed, loading new Activity", Toast.LENGTH_LONG).show()
                        //Load a new activity
                        //val intent = Intent(this,MainActivity::class.java)
                        //startActivity(intent)
                    }
                }*/

                //Now, Load the Camera Intent
                var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 101)
            }
        }



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
package com.example.animalapiviewer.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.animalapiviewer.R
import com.example.animalapiviewer.Objects.RetrofitInstance
import com.example.animalapiviewer.Objects.SaveSystem
import com.example.animalapiviewer.Objects.UserStatistics
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var currentImgUrl : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGetNextCat: Button = view.findViewById(R.id.btnGetNextCat)
        val ivCatPicture: ImageView = view.findViewById(R.id.ivCatPicture)
        val btnAddCatToFavourites : Button = view.findViewById(R.id.btnAddCatToFavourites)


        btnGetNextCat.setOnClickListener {
            handleRequest(ivCatPicture)
        }

        btnAddCatToFavourites.setOnClickListener{
            changeFavouriteStatus()
        }
    }

    private fun changeFavouriteStatus()
    {
        if(currentImgUrl.isNotEmpty()) {
            val isCatInFavourites: Boolean =
                SaveSystem.addFavouriteCatId(requireContext(), currentImgUrl)
            if (isCatInFavourites) {
                UserStatistics.setData(
                    requireContext(),
                    UserStatistics.getData.catClicks,
                    UserStatistics.getData.uniqueCatsFound,
                    UserStatistics.getData.favouriteCats + 1
                )
                Toast.makeText(requireContext(), "Cat added to favourites!", Toast.LENGTH_SHORT).show()


            } else if(SaveSystem.removeFavouriteCatId(requireContext(), currentImgUrl)) {
                Toast.makeText(requireContext(), "Cat removed from favourites!", Toast.LENGTH_SHORT)
                    .show()

                UserStatistics.setData(
                    requireContext(),
                    UserStatistics.getData.catClicks,
                    UserStatistics.getData.uniqueCatsFound,
                    UserStatistics.getData.favouriteCats - 1
                )
            }


        }
        else
            Toast.makeText(requireContext(), "No cat on the screen!", Toast.LENGTH_SHORT).show()
    }


    private fun handleRequest(ivCat: ImageView) {
        lifecycleScope.launch {
            val response = try {
                RetrofitInstance.api.getCatImage()
            } catch (e: IOException) {
                Log.e("HomeFragment", "IOException: ${e.message}")
                return@launch
            } catch (e: HttpException) {
                Log.e("HomeFragment", "HttpException: ${e.message}")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                val catImages = response.body() ?: return@launch
                if (catImages.isNotEmpty() && catImages[0].url.isNotEmpty()) {
                    val catImageUrl = catImages[0].url
                    Log.d("HomeFragment", "Cat image URL: $catImageUrl")
                    UserStatistics.setData(
                        requireContext(),
                        UserStatistics.getData.catClicks + 1,
                        UserStatistics.getData.uniqueCatsFound,
                        UserStatistics.getData.favouriteCats
                    )

                    currentImgUrl = catImageUrl

                    val isCatIdUnique : Boolean =
                        SaveSystem.addUniqueCatId(requireContext(), catImageUrl)
                    if(isCatIdUnique)
                        UserStatistics.setData(
                            requireContext(),
                            UserStatistics.getData.catClicks,
                            UserStatistics.getData.uniqueCatsFound + 1,
                            UserStatistics.getData.favouriteCats
                        )



                    Glide.with(this@HomeFragment)
                        .load(catImageUrl)
                        .into(ivCat)
                } else {
                    Log.e("HomeFragment", "Empty or invalid image URL")
                }
            } else {
                Log.e("HomeFragment", "Response not successful: ${response.errorBody()?.string()}")
            }
        }
    }
}

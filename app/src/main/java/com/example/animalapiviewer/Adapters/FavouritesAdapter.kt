package com.example.animalapiviewer.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animalapiviewer.Objects.RetrofitInstance
import com.example.animalapiviewer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class FavouritesAdapter(
    private val favouritesList: MutableList<String>,
    private val lifecycleScope: CoroutineScope
) : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    inner class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catIdTextView: TextView = itemView.findViewById(R.id.tvFavouriteCatId)
        val catImageView: ImageView = itemView.findViewById(R.id.ivFavouriteCatImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun getItemCount(): Int = favouritesList.size

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val catId = favouritesList[position]

        holder.catIdTextView.text = catId

        handleRequest(holder.catImageView, extractCatIdFromUrl(catId))
    }

    private fun handleRequest(ivCat: ImageView, catUrl: String) {
        lifecycleScope.launch {
            val response = try {
                RetrofitInstance.api.getCatImageById(catUrl)
            } catch (e: IOException) {
                Log.e("FavouritesAdapter", "IOException: ${e.message}")
                return@launch
            } catch (e: HttpException) {
                Log.e("FavouritesAdapter", "HttpException: ${e.message}")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                val catImage = response.body()!!
                val catImageUrl = catImage.url

                Log.d("FavouritesAdapter", "Cat image URL: $catImageUrl")


                Glide.with(ivCat.context)
                    .load(catImageUrl)
                    .into(ivCat)
            } else {
                Log.e("FavouritesAdapter", "Response not successful: ${response.errorBody()?.string()}")
            }
        }
    }


    private fun extractCatIdFromUrl(url: String): String {
        val withoutExtension = url.substringBeforeLast('.')

        return withoutExtension.substringAfterLast('/')
    }
}

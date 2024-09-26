package com.example.animalapiviewer.Fragments
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalapiviewer.Adapters.FavouritesAdapter
import com.example.animalapiviewer.R
import com.example.animalapiviewer.Objects.SaveSystem

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    override fun onViewCreated(view: View, savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        val favList : MutableList<String> = SaveSystem.getFavouriteCats(requireContext())

        val favouritesAdapter = FavouritesAdapter(favList, lifecycleScope)
        val rvFavouriteCats  = view.findViewById<RecyclerView>(R.id.rvFavouriteCats)
        rvFavouriteCats?.adapter = favouritesAdapter
        rvFavouriteCats?.layoutManager = LinearLayoutManager(requireContext())



    }

    override fun onResume() {
        super.onResume()

    }



}
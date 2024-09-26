package com.example.animalapiviewer.Fragments
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.animalapiviewer.R
import com.example.animalapiviewer.Objects.UserStatistics

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    val userNameVarToSave = "etUserName"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val etUserName : EditText = view.findViewById(R.id.etUserName)
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        etUserName.setText(sharedPreferences.getString(userNameVarToSave, "User"))

        // Add TextWatcher to automatically save text on change
        etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val editor = sharedPreferences.edit()
                editor.putString(userNameVarToSave, etUserName.text.toString())

                editor.apply()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        UserStatistics.setDataFromSharedPref(requireContext())

        return view
    }

    override fun onResume() {
        super.onResume()

        view?.findViewById<TextView>(R.id.tvCatClicks)?.text = "Cat Clicks : " + UserStatistics.getData.catClicks.toString()
        view?.findViewById<TextView>(R.id.tvUniqueCatsFound)?.text = "Unique\nCats Found : " + UserStatistics.getData.uniqueCatsFound.toString()
        view?.findViewById<TextView>(R.id.tvFavouriteCats)?.text = "Favourite Cats : " + UserStatistics.getData.favouriteCats.toString()
    }

}
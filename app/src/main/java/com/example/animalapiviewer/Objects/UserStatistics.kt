package com.example.animalapiviewer.Objects

import android.content.Context
import com.example.animalapiviewer.DataClasses.UserStatisticsData

object UserStatistics {

    private val dataToSave : UserStatisticsData = getDefaultData()


    val getData: UserStatisticsData
        get() = dataToSave

    private const val catClicksVarNameToSave : String  = "catClicks"
    private const val uniqueCatsFoundVarNameToSave : String  = "uniqueCatsFound"
    private const val favouriteCatsVarNameToSave : String  = "favouriteCats"

    private fun getDefaultData(): UserStatisticsData
    {
        return UserStatisticsData(0, 0, 0)
    }

    fun setData(context: Context, catClicks : Int?, uniqueCatsFound: Int?, favouriteCats: Int?)
    {
        if(catClicks != null)
            dataToSave.catClicks = catClicks
        if(uniqueCatsFound != null)
            dataToSave.uniqueCatsFound = uniqueCatsFound
        if(favouriteCats != null)
            dataToSave.favouriteCats = favouriteCats

        val sharedPreferences = context.getSharedPreferences("user_stats", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(catClicksVarNameToSave, dataToSave.catClicks)
        editor.putInt(uniqueCatsFoundVarNameToSave, dataToSave.uniqueCatsFound)
        editor.putInt(favouriteCatsVarNameToSave, dataToSave.favouriteCats)
        editor.apply()
    }

    //this function must be called in the beginning of fragment that will use this singleton
    fun setDataFromSharedPref(context: Context)
    {
        val sharedPreferences = context.getSharedPreferences("user_stats", Context.MODE_PRIVATE)
        dataToSave.catClicks = sharedPreferences.getInt(catClicksVarNameToSave, 0)
        dataToSave.uniqueCatsFound = sharedPreferences.getInt(uniqueCatsFoundVarNameToSave, 0)
        dataToSave.favouriteCats = sharedPreferences.getInt(favouriteCatsVarNameToSave, 0)
    }



}
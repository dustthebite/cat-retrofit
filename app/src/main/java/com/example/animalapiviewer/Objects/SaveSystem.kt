package com.example.animalapiviewer.Objects

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object SaveSystem {
    private fun addUniqueCat(context : Context, newId: String)
    {
        val fileName = "unique_cats.json"

        val existingIds = getUniqueCats(context) ?: mutableListOf()

        existingIds.add(newId)

        val jsonString = Gson().toJson(existingIds)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            outputStream ->

            outputStream.write(jsonString.toByteArray())
        }


    }

    private fun getUniqueCats(context: Context) : MutableList<String>?{
        val fileName = "unique_cats.json"
        val file = File(context.filesDir, fileName)

        if (!file.exists()) {
            return mutableListOf()
        }

        val jsonString = context.openFileInput(fileName).bufferedReader().use {
            it.readText()
        }

        val listType = object : TypeToken<MutableList<String>>() {}.type

        return Gson().fromJson(jsonString, listType)

    }

    private fun isIdUnique(context: Context, newId: String) : Boolean {
         val existingIds = getUniqueCats(context) ?: mutableListOf()

        return !existingIds.contains(newId)
    }


    fun addUniqueCatId(context: Context, newId: String) : Boolean {
        return if (isIdUnique(context, newId)) {
            addUniqueCat(context, newId)
            true
        } else {
            false
        }
    }


    private fun addFavouriteCat(context : Context, newId: String)
    {
        val fileName = "favourite_cats.json"

        val existingIds = getFavouriteCats(context)

        existingIds.add(newId)

        val jsonString = Gson().toJson(existingIds)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                outputStream ->

            outputStream.write(jsonString.toByteArray())
        }


    }

    private fun removeFavouriteCat(context : Context, idToRemove: String)
    {
        val fileName = "favourite_cats.json"

        val existingIds = getFavouriteCats(context)

        existingIds.add(idToRemove)

        if(existingIds.contains(idToRemove)) {
            existingIds.remove(idToRemove)
            val jsonString = Gson().toJson(existingIds)
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->

                outputStream.write(jsonString.toByteArray())
            }
        }



    }

    private fun isFavouriteIdUnique(context: Context, newId: String) : Boolean {
        val existingIds = getFavouriteCats(context)

        return !existingIds.contains(newId)
    }


    fun addFavouriteCatId(context: Context, newId: String) : Boolean {
        return if (isFavouriteIdUnique(context, newId)) {
            addFavouriteCat(context, newId)
            true
        } else {
            false
        }
    }

    fun removeFavouriteCatId(context: Context, newId: String) : Boolean {
        return if (!isFavouriteIdUnique(context, newId)) {
            removeFavouriteCat(context, newId)
            true
        } else {
            false
        }
    }

    fun getFavouriteCats(context: Context) : MutableList<String>{
        val fileName = "favourite_cats.json"
        val file = File(context.filesDir, fileName)

        if (!file.exists()) {
            return mutableListOf()
        }

        val jsonString = context.openFileInput(fileName).bufferedReader().use {
            it.readText()
        }

        val listType = object : TypeToken<MutableList<String>>() {}.type

        return Gson().fromJson(jsonString, listType)

    }


}
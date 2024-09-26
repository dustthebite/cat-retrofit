package com.example.animalapiviewer.DataClasses

data class UserStatisticsData(
    var catClicks: Int,
    var uniqueCatsFound: Int,
    var favouriteCats: Int,
    //maybe i`ll add something like count of every button click (next cat and prev cat)
)

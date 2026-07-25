package com.usermobilityprediction.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.usermobilityprediction.app.data.location.LocationService


class LocationViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {


        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {


            val locationService =
                LocationService(context)


            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(
                locationService
            ) as T
        }


        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
package com.usermobilityprediction.app.data.storage

import android.content.Context
import java.util.UUID

class DeviceManager(
    context: Context
) {

    private val preferences =
        context.getSharedPreferences(
            "device_preferences",
            Context.MODE_PRIVATE
        )

    fun getDeviceId(): String {

        var deviceId =
            preferences.getString(
                "device_id",
                null
            )

        if (deviceId == null) {

            deviceId =
                UUID.randomUUID().toString()

            preferences.edit()
                .putString(
                    "device_id",
                    deviceId
                )
                .apply()
        }

        return deviceId
    }
}
package com.adcoinfam.upperwall

import android.content.Context
import android.content.Intent

class UpperWall {

    companion object  {
        fun getUserAccessToken(googleIDToken: String):String {
            return "My first string"
        }
        fun launch(accessToken: String, appID: String, context: Context) {
            context.startActivity(Intent(context, OfferWall::class.java))
        }
    }
}
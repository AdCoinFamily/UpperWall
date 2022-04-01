package com.adcoinfam.upperwall

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.HashMap


class UpperWall {

    companion object  {
        fun launch(appID: String, customID: String, context: Context) {
            val i = Intent(context, OfferWall::class.java)
            i.putExtra("appID", appID)
            i.putExtra("customID", customID)
            context.startActivity(i)
        }
    }
}
package com.adcoinfam.upperwall

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.adcoinfam.upperwall.databinding.ActivityOfferWallBinding
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.HashMap

class OfferWall : AppCompatActivity() {

    private lateinit var binding: ActivityOfferWallBinding

    private val tasks = TasksFragment()
    private val createTasks = CreateTaskFragment()
    private val cabinet = PersonalCabinetFragment()
    var active: Fragment = tasks
    var fm: FragmentTransaction? = null
    lateinit var saved: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferWallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {

            val appID = intent.getStringExtra("appID")
            val customID = intent.getStringExtra("customID")


            val arg = Bundle()

            arg.putString("appID", appID)
            arg.putString("customID", customID)

            tasks.arguments = arg

            fm = supportFragmentManager.beginTransaction()
            fm!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            fm!!.add(R.id.containerFragment, createTasks).hide(createTasks)
            fm!!.add(R.id.containerFragment, tasks).hide(tasks)
            fm!!.add(R.id.containerFragment, cabinet).hide(cabinet)
            fm!!.show(tasks)
            fm!!.commit()
        }
        else startActivity(Intent(this, OfferWall::class.java))
        binding.navigationBar.setOnItemSelectedListener {
            fm = supportFragmentManager.beginTransaction()
            fm!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            when(it.itemId) {

                R.id.tasksFragment -> {
                    if (active != tasks) {
                        fm!!.hide(active).show(tasks)
                        active = tasks
                    }
                }

                R.id.createTaskFragment -> {
                    if (active != createTasks) {
                        fm!!.hide(active).show(createTasks)
                        active = createTasks
                    }
                }

                R.id.userCabinetFragment -> {
                    if (active != cabinet) {
                        fm!!.hide(active).show(cabinet)
                        active = cabinet
                    }
                }

                R.id.exitFromUpperWall -> {
                    onBackPressed()
                }

            }
            true
        }


    }
}
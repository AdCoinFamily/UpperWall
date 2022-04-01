package com.adcoinfam.upperwall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcoinfam.upperwall.databinding.FragmentTasksBinding
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

class TasksFragment : Fragment() {

    private var _binding : FragmentTasksBinding?= null
    private val binding get() = _binding!!

    var tasks = ArrayList<TaskObject>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)

        binding.taskList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        loadTasks()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadTasks() {
        val req = Volley.newRequestQueue(requireContext())
        val request = object : StringRequest(
            Method.POST,
            "https://adcoinapp.ru/API6/UpperWall/loadTaskWall.php",
            {
                if (it != "") {
                    for (i in it.split(">")) {
                        val jso = JSONObject(i)
                        tasks.add(0, TaskObject(jso.getString("title"), jso.getString("message"), jso.getString("link"), jso.getDouble("price"), jso.getString("id"), jso.getString("type")))
                    }
                    binding.taskList.adapter = TaskAdapter(tasks, requireContext(), requireArguments().getString("UID")!!)
                }
                else {
                    binding.activeTasks.visibility = View.GONE
                    binding.disTasks.visibility = View.VISIBLE
                }
            },
            {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["customID"] = requireArguments().getString("customID")!!
                params["appID"] = requireArguments().getString("appID")!!

                return params
            }

        }
        req.add(request)
    }


}
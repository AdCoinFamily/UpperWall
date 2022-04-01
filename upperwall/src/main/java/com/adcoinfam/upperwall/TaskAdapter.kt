package com.adcoinfam.upperwall

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.HashMap

class TaskAdapter (private val tasks: ArrayList<TaskObject>, val context : Context, val UID : String) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val panel  = view.findViewById<View>(R.id.taskPanel) as LinearLayout
        val link  = view.findViewById<View>(R.id.taskLink) as LinearLayout
        val price  = view.findViewById<View>(R.id.taskPrice) as TextView
        val message  = view.findViewById<View>(R.id.taskMessage) as TextView
        val title  = view.findViewById<View>(R.id.taskTitle) as TextView
        val inf  = view.findViewById<View>(R.id.taskInf) as LinearLayout
        val check = view.findViewById<View>(R.id.taskCheck) as LinearLayout
        val passField = view.findViewById<View>(R.id.taskPassField) as EditText
        val image = view.findViewById<View>(R.id.taskImage) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val taskObject = tasks[position]

        holder.title.text = taskObject.title
        holder.message.text = taskObject.message
        when (taskObject.type) {
            "social" -> holder.image.setImageResource(R.drawable.social)
            "install" -> holder.image.setImageResource(R.drawable.install)
            "net" -> holder.image.setImageResource(R.drawable.net)
            else -> holder.image.setImageResource(R.drawable.uncategory)
        }
        holder.price.text = context.getString(R.string.upperwall_8, taskObject.price.toString())

        holder.panel.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.onclick))
            taskObject.checked = !taskObject.checked
            notifyItemChanged(position)
        }
        holder.check.setOnClickListener{
            if (holder.passField.text.toString().isNotEmpty()) {
                val req = Volley.newRequestQueue(context)
                val stringRequest: StringRequest = object : StringRequest(
                    Method.POST, "https://adcoinapp.ru/api/adcoin/CheckTask.php",
                    Response.Listener { response: String? ->
                        when (response) {
                            "success" -> {
                                Toast.makeText(context, context.getString(R.string.upperwall_9), Toast.LENGTH_SHORT).show()
                                context.startActivity(Intent(context, OfferWall::class.java))
                            }
                            "error:task_pass" -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.upperwall_10),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            "error:count" -> {
                                Toast.makeText(
                                    context,
                                    R.string.upperwall_11,
                                    Toast.LENGTH_SHORT
                                ).show()
                                context.startActivity(Intent(context, OfferWall::class.java))
                            }
                            else -> Toast.makeText(context, response, Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    Response.ErrorListener {

                    }) {
                    override fun getParams(): Map<String, String> {
                        val map: MutableMap<String, String> = HashMap()
                        map["user_id"] = UID
                        map["task_id"] = taskObject.id
                        map["task_pass"] = holder.passField.text.toString().trim()
                        return map
                    }
                }
                req.add(stringRequest)
            }
            else Toast.makeText(context, context.getString(R.string.upperwall_12), Toast.LENGTH_SHORT).show()
        }
        holder.link.setOnClickListener{
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(taskObject.link)))
        }

        if (taskObject.checked) holder.inf.visibility = View.VISIBLE
        else holder.inf.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return tasks.count()
    }
}
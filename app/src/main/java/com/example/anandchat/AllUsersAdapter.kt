package com.example.anandchat

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anandchat.models.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.single_user.view.*

class AllUsersAdapter(options: FirestoreRecyclerOptions<User>) :
    FirestoreRecyclerAdapter<User, AllUsersAdapter.UserViewHolder>(
        options
    ) {



    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.rvusername)
        val img: ImageView = itemView.findViewById(R.id.rvuserimg)
         val uid:TextView= itemView.findViewById(R.id.faltu)


        init {
            itemView.setOnClickListener {
                //Toast.makeText(itemView.context, "Its toast!", Toast.LENGTH_SHORT).show()
                Log.d("TAG1","item click ho gaya")
                val intent = Intent(itemView.context,ChatActivity::class.java)

                intent.putExtra("samplename","${itemView.faltu.text}")
                itemView.context.startActivity(intent)


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
        holder.name.text = model.name
holder.uid.text=model.uid


        Glide.with(holder.itemView.context).load(model.image).into(holder.img)

    }
}




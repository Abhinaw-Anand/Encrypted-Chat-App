package com.example.anandchat

import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.anandchat.models.Message
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.single_user.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChatAdapter(options: FirestoreRecyclerOptions<Message>) :
        FirestoreRecyclerAdapter<Message, ChatAdapter.ChatViewHolder>(
                options
        ) {



    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val chat: TextView = itemView.findViewById(R.id.chat)
        val time: TextView = itemView.findViewById(R.id.time)





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.single_chat, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int, model: Message) {

        val myuid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        GlobalScope.launch(Dispatchers.IO) {

        Log.d("TAGG","Encryption type "+model.em)
                val ss = Decrypt.runner(model.text,model.em)
                holder.chat.text = ss

        }


        holder.time.text=model.sentAt

        if(model.sender==myuid) {
            holder.chat.gravity = Gravity.LEFT or Gravity.BOTTOM

            holder.time.gravity = Gravity.LEFT or Gravity.BOTTOM
        }
        else
        {


            holder.chat.gravity = Gravity.RIGHT or Gravity.BOTTOM

            holder.time.gravity = Gravity.RIGHT or Gravity.BOTTOM


        }





    }
}
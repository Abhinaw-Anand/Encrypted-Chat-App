package com.example.anandchat

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anandchat.models.Message
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

     private lateinit var fuid:String
    private lateinit var adapter:ChatAdapter


    var languages = arrayOf("AES","DES")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val arrayAdapter=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,languages)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()
        val ptuid: String = intent.getStringExtra("samplename").toString()
        Log.d("TAG1", ptuid)
         spinner.adapter=arrayAdapter
  val myuid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        Log.d("TAG1", myuid)
        if (ptuid > myuid)
            fuid = ptuid + myuid
        else
            fuid = myuid + ptuid
        Log.d("TAG1", fuid)
       val db = FirebaseFirestore.getInstance()
        val msgCollections = db.collection(fuid)
        val docuref = db.collection("users").document(ptuid)
        docuref.get().addOnSuccessListener {

            name2.text = it.getString("name")
        }

        val query = msgCollections.orderBy("createdAt", Query.Direction.ASCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()
        adapter = ChatAdapter(recyclerViewOptions)
        msglist.adapter = adapter
        msglist.layoutManager = LinearLayoutManager(this)

        send.setOnClickListener {


                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                val currentTime = System.currentTimeMillis()

                if (spinner.selectedItem.toString() == "AES") {

                    val m = Message(Encrypt.AES(msgtxt.text.toString()), myuid, currentDate, currentTime, "AES")


                     val ss=Encrypt.AES(msgtxt.text.toString())
                    Log.d("TAG1","Encrypted is="+ss)
                    Log.d("TAG1","Decrypted  is="+Decrypt.AES(ss))


                    msgCollections.document().set(m)


                } else {


                    val m = Message(Encrypt.DES(msgtxt.text.toString()), myuid, currentDate, currentTime, "DES")

                    msgCollections.document().set(m)


                }

msgtxt.setText("")

        }
    }

    public override fun onStart() {
        super.onStart()

        adapter.startListening()

    }


    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}
package com.example.anandchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anandchat.models.User
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_all_users.*

class AllUsers : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var adapter:AllUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_users)
        supportActionBar?.hide()

        auth = Firebase.auth


        val db = FirebaseFirestore.getInstance()
        val userCollections = db.collection("users")


        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<User>().setQuery(userCollections, User::class.java).build()
        adapter= AllUsersAdapter(recyclerViewOptions)
        rv_allusers.adapter=adapter
        rv_allusers.layoutManager= LinearLayoutManager(this)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        adapter.startListening()
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }



    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
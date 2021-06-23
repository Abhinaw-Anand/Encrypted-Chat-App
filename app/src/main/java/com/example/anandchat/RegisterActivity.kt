package com.example.anandchat

import android.app.ProgressDialog.show
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.example.anandchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterActivity : AppCompatActivity() {
    private  var imguri:Uri="".toUri()
private lateinit var mimg:StorageReference
    private lateinit var auth: FirebaseAuth
private  val TAG:String="TAG1"
    val mCurrentUser: FirebaseUser? = null
private var l:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        // Initialize Firebase Auth
        auth = Firebase.auth
        mRegProgress.visibility = View.GONE
        already.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        profImg.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 12)
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 12)
        {
imguri= data?.data!!
Glide.with(this).load(data?.data).centerCrop().into(profImg)



            GlobalScope.launch(Dispatchers.IO) {
                l = imgsize.getSize(baseContext, imguri)!!.toLong()
            }
            Log.d(TAG, l.toString())

          /*  Log.d(TAG,file.length().toString())
            val stream = FileInputStream(file)
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
            ref.putStream(stream)
                    .addOnSuccessListener {
                        Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")


                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Failed to upload image to storage: ${it.message}")
                    }*/
        }
    }

    fun registerfun(view: View) {

        val email = email.text.toString()
        val password1 = password1.text.toString()
        val password2 = password2.text.toString()

        if (email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(baseContext, "Email or Password can not be empty.", Toast.LENGTH_SHORT).show()
            return
        } else if (password1 != password2) {
            Toast.makeText(baseContext, "Both Password are not same.", Toast.LENGTH_SHORT).show()

        }
        else if(imguri=="".toUri())
        {
            Toast.makeText(
                baseContext,
                "You have to select Image",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if (l>900000)
        {
            Toast.makeText(
                baseContext,
                "Image size should be less than 900Kb.Use a Low resolution image or Crop",
                Toast.LENGTH_LONG
            ).show()

        }

        else {

            mRegProgress.visibility = View.VISIBLE

var imgurl:String=""
            auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {


                            val uid = auth.uid ?: ""

                            val currentuser= uid

                            val ref = FirebaseStorage.getInstance().getReference("/images/$currentuser")

                            ref.putFile(imguri)
                                .addOnSuccessListener {
                                    Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")
                                    mRegProgress.visibility = View.GONE
                                    ref.downloadUrl.addOnSuccessListener {
                                        Log.d(TAG, "File Location: $it")


                                        val user = User(uid.toString(), name.text.toString(),it.toString())
                                        val db = FirebaseFirestore.getInstance()
                                        val usersCollection = db.collection("users")

                                        usersCollection.document(uid.toString()).set(user)
                                        val intent = Intent(this, AllUsers::class.java)
                                        startActivity(intent)

                                    }
                                }
                                .addOnFailureListener {
                                    Log.d(TAG, "Failed to upload image to storage: ${it.message}")
                                    mRegProgress.visibility = View.GONE
                                }
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            Toast.makeText(
                                baseContext,
                                "Account Successsfully Created.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            mRegProgress.visibility = View.GONE
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, "Account Not Created.", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }


                    }

        }
    }
}
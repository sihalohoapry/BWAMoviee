package com.sihaloho.bwamov.sign.singup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.sihaloho.bwamov.R
import com.sihaloho.bwamov.sign.signin.User
import com.sihaloho.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername:String
    lateinit var sPassword:String
    lateinit var sNama:String
    lateinit var sEmail:String

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        btn_home.setOnClickListener {
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()

            if (sUsername.equals("")) {
                et_username.error = "Silahkan isi Username"
                et_username.requestFocus()
            } else if (sPassword.equals("")) {
                et_password.error = "Silahkan isi Password"
                et_password.requestFocus()
            } else if (sNama.equals("")) {
                et_nama.error = "Silahkan isi Nama"
                et_nama.requestFocus()
            } else if (sEmail.equals("")) {
                et_email.error = "Silahkan isi Email"
                et_email.requestFocus()
            } else {

                saveUser(sUsername, sPassword, sNama, sEmail)

            }
        }



    }

    private fun saveUser(sUsername: String, sPassword: String, sNama: String, sEmail: String) {

        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.nama = sNama
        user.password = sPassword

        if (sUsername != null) {
            checkingUsername(sUsername, user)

        }

    }

    private fun checkingUsername(iUsername: String, data: User) {
        mFirebaseDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mFirebaseDatabase.child(iUsername).setValue(data)

                    preferences.setValues("nama", data.nama.toString())
                    preferences.setValues("user", data.username.toString())
                    preferences.setValues("url", "")
                    preferences.setValues("email", data.email.toString())
                    preferences.setValues("status", "1")

                    val intent = Intent(this@SignUpActivity,
                        SignUpPhotoActivity::class.java).putExtra("nama", data.nama)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}

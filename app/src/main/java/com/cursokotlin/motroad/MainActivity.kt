package com.cursokotlin.motroad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.cursokotlin.motroad.R.id
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


class MainActivity : AppCompatActivity() {

    var editUsuario: EditText?=null;
    var editClave: EditText?=null;
    var botonAutenticar: Button?= null;
    private var mauth: FirebaseAuth?=null;




   public override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        Thread.sleep(1000)
        super.onCreate(savedInstanceState)



        mauth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        botonAutenticar=findViewById(id.signIn);
       var editUsuario:EditText= findViewById(id.editTextTextEmailAddress);
       var editClave:EditText= findViewById(id.editTextTextPassword);
       botonAutenticar?.setOnClickListener{
           mauth!!.signInWithEmailAndPassword(editUsuario.text.toString(),editClave.text.toString()).addOnCompleteListener(
               this
           ){
               task->
                   if(task.isSuccessful){
                       val user=mauth!!.currentUser
                       Toast.makeText(this, "Autenticado", Toast.LENGTH_SHORT).show()
                       startActivity(Intent(this, Inicio::class.java).putExtra("uid",user?.uid))

                   }else{
                       Log.e("Autenticacion","Fallo en autenticacion",task.exception)
                           Toast.makeText(this, "Conexion fallidas", Toast.LENGTH_SHORT).show()
                   }

           }
       }

    }



}
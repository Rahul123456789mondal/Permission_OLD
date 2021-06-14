package com.example.permission_demo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var mTextView : TextView
    private lateinit var btnContact : Button
    private lateinit var btnCamera : Button
    private lateinit var btnNetwork : Button
    private lateinit var btnFile : Button

    private var mPermission : Boolean = false
    private val REQ_CODE : Int = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        btnContact.setOnClickListener(this::readContacts)
        btnCamera.setOnClickListener(this::captureImage)
        btnNetwork.setOnClickListener(this::getNetwork)
        btnFile.setOnClickListener(this::writeFile)


    }

    private fun readContacts(view : View){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.READ_CONTACTS)
                return
            }
        }
    }
    private fun captureImage(view : View){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.CAMERA)
                return
            }
            val intent : Intent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }
        val intent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)

    }
    private fun getNetwork(view : View){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.INTERNET)
                return
            }
        }
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected : Boolean = networkInfo != null && networkInfo.isConnected
        Toast.makeText(this, ""+isConnected, Toast.LENGTH_SHORT).show()

    }
    private fun writeFile(view : View){

    }

    private fun initViews(){
        mTextView = findViewById(R.id.text)
        btnContact = findViewById(R.id.btnContact)
        btnCamera = findViewById(R.id.btnCamera)
        btnNetwork = findViewById(R.id.btnNetwork)
        btnFile = findViewById(R.id.btnFile)

    }

    private fun requestPermission(permission : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(permission), REQ_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mPermission = true
            Toast.makeText(this, "Permission granted...", Toast.LENGTH_SHORT).show()
        }
    }

}

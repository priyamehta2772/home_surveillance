package com.meet.photoanalyser

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.security.Permission
import java.security.Permissions
import java.util.jar.Manifest
import com.google.firebase.storage.StorageReference
import android.R.attr.name
import android.app.ProgressDialog
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.os.StrictMode
import android.support.annotation.NonNull
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.UploadTask
import java.io.File


class MainActivity : AppCompatActivity() {
    var CAMERA_PERMISSION_CODE = 0
    var STORAGE_PERMISSION_CODE = 1
    var CAMERA_PHOTO_CLICKED = 99
    private var mStorageRef: StorageReference? = null
    var filename = "Hello.jpg"
    var destination : File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askPermission()

        mStorageRef = FirebaseStorage.getInstance().getReference()

        Log.v("Analyzer", mStorageRef.toString())
        Log.v("Analyzer", mStorageRef?.bucket)


        destination = File( getExternalStorageDirectory(), filename)

        findViewById<Button>(R.id.clickPhoto).setOnClickListener {
            var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination))
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            startActivityForResult(cameraIntent, CAMERA_PHOTO_CLICKED)
        }

    }

    fun askPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            0 -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Camera Permission is required", Toast.LENGTH_SHORT).show()
                    askPermission()
                }
            }
            1 -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Storage Permission is required", Toast.LENGTH_SHORT).show()
                    askPermission()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading")
        progressDialog.setMessage("Please wait while uploading photo")
        progressDialog.setCancelable(false);
        progressDialog.show()

        when(requestCode){
            CAMERA_PHOTO_CLICKED -> {
                val riversRef = mStorageRef?.child("images/Hello.jpg")
//                Log.v("Analyzer", riversRef?.)
                riversRef?.putFile(Uri.fromFile(destination))?.addOnSuccessListener {
                    Snackbar.make(findViewById<RelativeLayout>(R.id.relativeMainLayout), "Image Uploaded", Snackbar.LENGTH_LONG).show()
                    progressDialog.hide()
                }?.addOnFailureListener{
                    Snackbar.make(findViewById<RelativeLayout>(R.id.relativeMainLayout), "Image Error Uploaded", Snackbar.LENGTH_LONG).show()
                    progressDialog.hide()
                }
            }
        }
    }
}

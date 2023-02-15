package com.example.intentwithreturningdata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create a new ActivityResultLauncher to launch the second activity and handle the result
        // When the result is returned, the result parameter will contain the data and resultCode (e.g., OK, Cancelled etc.).
        val secondActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                // extract the data
                val status = data?.getStringExtra("status")
                val phone = data?.getIntExtra("phone", 0)

                Log.d(TAG, "status: $status")
                Log.d(TAG, "phone: $phone")

                // Do something with the data
                findViewById<TextView>(R.id.tv_status).text = status.toString()
                findViewById<TextView>(R.id.tv_phone).text = phone.toString()
            }
        }

        // the second activity will only be launched when the button is clicked.
        findViewById<Button>(R.id.button_to_launch_second_activity).setOnClickListener {

            // prepare the data to be sent to the second activity
            val firstName = findViewById<EditText>(R.id.firstname_input).text.toString()
            val lastName = findViewById<EditText>(R.id.lastname_input).text.toString()
            val age = findViewById<EditText>(R.id.age_input).text.toString().toIntOrNull()

            // Check to make sure the fields are not empty
            if (firstName.isEmpty() || lastName.isEmpty() || age == null) {
                Toast.makeText(
                    this,
                    "Please enter all of the requested information",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // For testing purpose, print the values in the logcat
                Log.d(TAG, "firstName: $firstName")
                Log.d(TAG, "lastName: $lastName")
                Log.d(TAG, "age: $age")

                //Create an Intent object with two parameters: 1) context, 2) class of the activity to launch
                val myIntent = Intent(this, SecondActivity::class.java)

                // put "extras" into the intent for access in the second activity
                myIntent.putExtra("firstName", firstName)
                myIntent.putExtra("lastName", lastName)
                myIntent.putExtra("age", age)

                // Start the new Activity with the given intent and registers the ActivityResultCallback
                secondActivityLauncher.launch(myIntent)
            }
        }

    }

}
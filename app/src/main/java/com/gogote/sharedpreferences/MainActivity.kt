package com.gogote.sharedpreferences

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.gogote.sharedpreferences.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adjust the view's padding to account for system bars (e.g., status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the default shared preferences for the app
        val manager = PreferenceManager.getDefaultSharedPreferences(this)

        // Define a listener to respond to changes in shared preferences
        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "Change_UI") {
                // Change the background color based on the "Change_UI" preference
                if (manager.getBoolean("Change_UI", false) == true) {
                    binding.main.setBackgroundColor(Color.MAGENTA)
                } else {
                    binding.main.setBackgroundColor(Color.WHITE)
                }
            }
        }

        // Register the shared preferences listener
        manager.registerOnSharedPreferenceChangeListener(listener)

        // Set a click listener to open the settings activity
        binding.setting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Retrieve the shared preferences for storing user data
        val editor = getSharedPreferences("data", MODE_PRIVATE)

        // Deserialize the user data from JSON stored in shared preferences
        val user = Gson().fromJson(editor.getString("USER_DATA", null), User::class.java)

        // Set the user's name and password in the text fields
        binding.name.setText(user?.name)
        binding.pass.setText(user?.pass)

        // Handle login button click
        binding.login.setOnClickListener {
            // Create a new User object with the entered name and password
            val user = User(binding.name.text.toString(), binding.pass.text.toString())

            // Serialize the User object to JSON
            val gson = Gson()
            val data = gson.toJson(user, User::class.java)

            // Save the serialized user data in shared preferences
            val editor = getSharedPreferences("data", MODE_PRIVATE).edit()
            editor.putString("USER_DATA", data)
            editor.apply()

            // Start MainActivity2
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }
}

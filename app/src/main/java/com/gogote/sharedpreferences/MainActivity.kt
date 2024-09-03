package com.gogote.sharedpreferences

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.gogote.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        val manager =  PreferenceManager.getDefaultSharedPreferences(this)
        listener =SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "Change_UI"){
                if(manager.getBoolean("Change_UI",false)== true){
                    binding.main.setBackgroundColor(Color.MAGENTA)
                }else {
                    binding.main.setBackgroundColor(Color.WHITE)
                }

            }
        }
        manager.registerOnSharedPreferenceChangeListener(listener)

        binding.setting.setOnClickListener {
            startActivity(Intent(this,MainActivity3::class.java))
        }
        val editor = getSharedPreferences("data", MODE_PRIVATE)
        binding.name.setText(editor.getString("name",null))
        binding.pass.setText(editor.getString("pass",null))

        binding.login.setOnClickListener {
            val editor = getSharedPreferences("data", MODE_PRIVATE).edit()
            editor.putString("name",binding.name.text.toString())
            editor.putString("pass",binding.pass.text.toString())
            editor.apply()
            startActivity(Intent(this,MainActivity2::class.java))
        }
    }
}
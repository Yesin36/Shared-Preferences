package com.gogote.sharedpreferences

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gogote.sharedpreferences.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val editor = getPreferences(MODE_PRIVATE)
        binding.name.setText(editor.getString("name",null))
        binding.checkBox.isChecked = editor.getBoolean("checked",false)
        binding.save.setOnClickListener {
            val editor = getPreferences(MODE_PRIVATE).edit()
            editor.putString("name",binding.name.text.toString())
            editor.putBoolean("checked",binding.checkBox.isChecked)
            editor.apply()
        }
    }
}
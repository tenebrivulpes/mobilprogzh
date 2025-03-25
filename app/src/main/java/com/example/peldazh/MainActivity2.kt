package com.example.peldazh

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    private var score: Int = 0
    private var tries: Int = 0

    private lateinit var scoreListView: ListView
    private lateinit var tryListView: ListView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        image = findViewById(R.id.imageView)
        scoreListView = findViewById(R.id.scoreList)
        tryListView = findViewById(R.id.tryList)

        score = intent.getIntExtra("score", 0)
        tries = intent.getIntExtra("tries", 0)

        val scoreList = arrayListOf<Int>()
        scoreList.add(score)

        val tryList = arrayListOf<Int>()
        tryList.add(tries)

        val arrayAdapterSc = ArrayAdapter(this, android.R.layout.simple_list_item_1, scoreList)
        val arrayAdapterT = ArrayAdapter(this, android.R.layout.simple_list_item_1, tryList)

        scoreListView.adapter = arrayAdapterSc
        tryListView.adapter = arrayAdapterT

        ObjectAnimator.ofFloat(image, "translationY", 0f).apply {
            duration = 2000
            start()
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Új játék kezdése?").setCancelable(false)
            .setPositiveButton("Igen") {
                dialog, id -> Intent(this, MainActivity::class.java)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Nem") {
                dialog, id -> dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

}
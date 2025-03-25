package com.example.peldazh

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var imgDict: HashMap<ImageView, Int> = HashMap()
    private var counter: Int = 0
    private var tryCounter: Int = 0
    private var scoreCounter: Int = 0
    private var prevView: ImageView? = null
    private var canPlay = true

    private lateinit var seekBar: SeekBar
    private lateinit var scoreText: TextView
    private lateinit var score: TextView
    private lateinit var triesText: TextView
    private lateinit var tries: TextView

    private var viewList = listOf(
        R.id.imageView1,
        R.id.imageView2,
        R.id.imageView3,
        R.id.imageView4,
        R.id.imageView5,
        R.id.imageView6
    )

    private var imgList = mutableListOf(
        R.drawable.dog,
        R.drawable.dog,
        R.drawable.quokka,
        R.drawable.quokka,
        R.drawable.sloth,
        R.drawable.sloth
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Toast.makeText(this, "Kezdheted a játékot!", Toast.LENGTH_SHORT).show()

        seekBar = findViewById(R.id.seekBar)
        score = findViewById(R.id.score)
        scoreText = findViewById(R.id.scoreText)
        tries = findViewById(R.id.tries)
        triesText = findViewById(R.id.triesText)

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                score.textSize = progress.toFloat()
                scoreText.textSize = progress.toFloat()
                tries.textSize = progress.toFloat()
                triesText.textSize = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        });

        for (view in viewList) {
            val index = Random.nextInt(0, imgList.size)
            imgDict[findViewById(view)] = imgList[index]
            imgList.removeAt(index)

            findViewById<ImageView>(view).setOnClickListener {
                onClick(findViewById(view))
            }
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun onClick(imgView: ImageView) {
        if (prevView != imgView && canPlay){
            imgDict[imgView]?.let { imgView.setImageResource(it) }
            counter++
            if (counter == 1) prevView = imgView
            if (counter == 2) {
                tryCounter++
                findViewById<TextView>(R.id.tries).text = tryCounter.toString()
                canPlay = false
                if (imgDict[prevView] != imgDict[imgView]) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        imgView.setImageResource(R.drawable.kerdojel)
                        prevView?.setImageResource(R.drawable.kerdojel)
                        prevView = null
                        counter = 0
                        canPlay = true
                    }, 2000)
                } else {
                    scoreCounter++
                    findViewById<TextView>(R.id.score).text = scoreCounter.toString()
                    imgView.setOnClickListener {}
                    prevView?.setOnClickListener{}
                    prevView = null
                    counter = 0
                    canPlay = true
                    if (scoreCounter == 3) {
                        val intent = Intent(this, MainActivity2::class.java)
                        intent.putExtra("score", scoreCounter)
                        intent.putExtra("tries", tryCounter)
                        startActivity(intent)
                    }
                }
            }
        }
    }

}
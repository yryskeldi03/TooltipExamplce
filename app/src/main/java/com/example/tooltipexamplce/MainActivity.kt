package com.example.tooltipexamplce

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tooltipexamplce.databinding.ActivityMainBinding
import com.skydoves.balloon.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val customTooltip = CustomTooltipView(this, null)

        binding.btn2BottomCenter.setOnClickListener {
            customTooltip.show(
                anchorView = it,
                orientation = TooltipOrientation.BOTTOM,
                onBodyClick = {
                    Toast.makeText(this, "Типа реагируем", Toast.LENGTH_SHORT).show()
                },
                onIconClick = {
                    customTooltip.dismiss()
                }
            )
        }

        binding.btn2TopCenter.setOnClickListener {
            customTooltip.show(it, TooltipOrientation.TOP)
        }

        binding.btn2Right.setOnClickListener {
            customTooltip.show(it, TooltipOrientation.RIGHT)
        }

        binding.btn2Left.setOnClickListener {
            customTooltip.show(it, TooltipOrientation.LEFT)
        }



        binding.btnTopCenter.setOnClickListener {
            if (balloon.isShowing) {
                balloon.dismiss()
            } else {
                balloon.showAlignTop(it)
            }
        }
    }

    private val balloon by balloon<BalloonFactory>()
}
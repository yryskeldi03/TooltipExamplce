package com.example.tooltipexamplce

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.tooltipexamplce.databinding.CustomTooltipLayoutBinding

class CustomTooltipView(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private val binding: CustomTooltipLayoutBinding
    private val tooltipLayoutParams: ConstraintLayout.LayoutParams
    private var tooltipOrientation = TooltipOrientation.TOP
    private val popupWindow: PopupWindow

    init {
        binding = CustomTooltipLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        tooltipLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams = tooltipLayoutParams
        popupWindow = PopupWindow(
            this,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
    }

    fun show(
        anchorView: View,
        orientation: TooltipOrientation,
        onBodyClick: (() -> Unit)? = null,
        onIconClick: (() -> Unit)? = null
    ) {
        this@CustomTooltipView.tooltipOrientation = tooltipOrientation
        popupWindow.contentView = this

        binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val anchorLocation = IntArray(2)
        anchorView.getLocationOnScreen(anchorLocation)
        val anchorX = anchorLocation[0]
        val anchorY = anchorLocation[1]

        val tooltipWidth = this.measuredWidth
        val tooltipHeight = this.measuredHeight

        var x = 0
        var y = 0
        with(binding) {
            when (orientation) {
                TooltipOrientation.BOTTOM -> {
                    ivArrowTop.isVisible = true
                    goneViews(ivArrowRight, ivArrowLeft, ivArrowBottom)
                    x = anchorX + anchorView.width / 2 - tooltipWidth / 2
                    y = anchorY + anchorView.height
                }

                TooltipOrientation.TOP -> {
                    ivArrowBottom.isVisible = true
                    goneViews(ivArrowTop, ivArrowLeft, ivArrowRight)
                    x = anchorX + anchorView.width / 2 - tooltipWidth / 2
                    y = anchorY - tooltipHeight
                    if (x < 0) {
                        x = 0
                    }
                }

                TooltipOrientation.LEFT -> {
                    ivArrowLeft.isVisible = true
                    goneViews(ivArrowBottom, ivArrowRight, ivArrowTop)
                    x = anchorX - tooltipWidth
                    y = anchorY + anchorView.height / 2 - tooltipHeight / 2
                    if (x < 0) {
                        x = anchorX + anchorView.width
                    }
                }

                TooltipOrientation.RIGHT -> {
                    ivArrowRight.isVisible = true
                    goneViews(ivArrowBottom, ivArrowLeft, ivArrowTop)
                    x = anchorX + anchorView.width
                    y = anchorY + anchorView.height / 2 - tooltipHeight / 2
                    if (x + tooltipWidth > Resources.getSystem().displayMetrics.widthPixels) {
                        x = anchorX - tooltipWidth - anchorView.width
                    }
                }
            }
            root.setOnClickListener {
                onBodyClick?.invoke()
            }
            btnClose.setOnClickListener {
                onIconClick?.invoke()
            }
        }


        popupWindow.showAtLocation(anchorView, View.NO_ID, x, y)
    }

    fun dismiss() {
        popupWindow.dismiss()
    }

    fun moveArrowByX(deltaX: Int) {
        val layoutParams = binding.ivArrowTop.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.leftMargin -= deltaX
        binding.ivArrowTop.layoutParams = layoutParams
    }

    private fun goneViews(vararg views: View) {
        views.onEach {
            it.isVisible = false
        }
    }

}


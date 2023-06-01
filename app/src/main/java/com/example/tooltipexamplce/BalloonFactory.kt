package com.example.tooltipexamplce

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.*

class BalloonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return createBalloon(context) {
            setArrowOrientation(ArrowOrientation.BOTTOM)
            setArrowPosition(0.5f)
            setWidthRatio(0.55f)
            setHeight(100)
            setCornerRadius(4f)
            setText("Получи бонус 10 ГБ!")
            setAlpha(0.8f)
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setIconDrawableResource(R.drawable.ic_close)
            setIconGravity(IconGravity.END)
            setBackgroundColor(ContextCompat.getColor(context, R.color.tooltipbackground))
            setLifecycleOwner(lifecycle)
        }
    }
}
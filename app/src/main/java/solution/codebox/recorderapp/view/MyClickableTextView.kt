package solution.codebox.recorderapp.view

import android.content.Context
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView


class MyClickableTextView : TextView {

    constructor(context: Context) : super(context) {
        setTouchListener()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        setTouchListener()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setTouchListener()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        setTouchListener()
    }

    private fun setTouchListener() {
//        this.setOnTouchListener { _, e ->
//            if (hasOnClickListeners()) {
//                when (e.action) {
//                    MotionEvent.ACTION_DOWN -> isSelected = true
//                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> isSelected = false
//                }
//            }
//            true
//        }
    }
}
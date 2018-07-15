package solution.codebox.recorderapp.utils

import android.content.Context
import android.support.v4.content.ContextCompat

class ResourceUtils {
    companion object {
        fun getColorBy(context: Context, id: Int) = ContextCompat.getColor(context, id)
    }
}
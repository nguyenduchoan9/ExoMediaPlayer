package solution.codebox.recorderapp.utils

import android.content.Context
import android.content.res.Configuration

class Utils {
    companion object {
        fun isDeviceInLandscape(context: Context): Boolean{
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }

        fun isDeviceInPortrait(context: Context): Boolean{
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }
    }
}
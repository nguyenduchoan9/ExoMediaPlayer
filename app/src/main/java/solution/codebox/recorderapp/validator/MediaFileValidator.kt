package solution.codebox.recorderapp.validator

import android.content.Context
import android.net.Uri
import solution.codebox.recorderapp.utils.FileUtils
import java.io.File

class MediaFileValidator {
    companion object {
        fun isMP3File(context: Context, filePath: String): Boolean {
            val realPath = FileUtils.getRealPathFromURI(context, Uri.parse(filePath))
            val file = File(realPath)
            return file.extension == FileExtension.MP3
        }

        fun isMP4File(context: Context, filePath: String): Boolean {
            val realPath = FileUtils.getRealPathFromURI(context, Uri.parse(filePath))
            val file = File(realPath)
            return file.extension == FileExtension.MP4
        }
    }
}
package solution.codebox.recorderapp.activities.mediaPlayer

import android.net.Uri
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.model.DialogOption
import solution.codebox.recorderapp.utils.FileUtils
import solution.codebox.recorderapp.validator.MediaFileValidator
import solution.codebox.recorderapp.view.mediaPlayer.MyMediaPlayer

class HLSMediaPlayer  {
//    override fun buildMediaPlayer(): MyMediaPlayer {
//
//    }
//
//    override fun mediaUris(): List<Uri> {
//        return listOf(Uri.parse(getString(R.string.media_url_mp4)))
//    }
//
//    override fun getVideoUriAndPlay(filePath: String) {
//        if (MediaFileValidator.isMP3File(this, filePath)
//                || MediaFileValidator.isMP4File(this, filePath)) {
//            val realPath = FileUtils.getRealPathFromURI(this, Uri.parse(filePath))
//            realPath?.let {
//                mediaPlayer.changeMediaSource(listOf(createUriFromFilePath(it)))
//            }
//        } else {
//            informInvalidMediaFile()
//        }
//    }
//
//    override fun getDialogOptions(): List<DialogOption> = listOf(openVideoByUrlDialogOption())
}
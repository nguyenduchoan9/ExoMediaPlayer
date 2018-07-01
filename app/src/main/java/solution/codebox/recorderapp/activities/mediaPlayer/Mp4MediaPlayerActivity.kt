package solution.codebox.recorderapp.activities.mediaPlayer

import android.net.Uri
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.view.MediaPlayerFactory
import solution.codebox.recorderapp.view.MyMediaPlayer


class Mp4MediaPlayerActivity : BaseMediaPlayerActivity() {
    override fun mediaUris(): List<Uri> {
        return listOf(Uri.parse(getString(R.string.media_url_mp4)))
    }

    override fun buildMediaPlayer(): MyMediaPlayer {
        return MediaPlayerFactory.buildMP4Player(this)
    }
}

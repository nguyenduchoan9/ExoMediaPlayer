package solution.codebox.recorderapp.activities.mediaPlayer

import android.net.Uri
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.view.MediaPlayerFactory
import solution.codebox.recorderapp.view.MyMediaPlayer

class DashMediaPlayerActivity : BaseMediaPlayerActivity() {
    override fun mediaUris(): List<Uri> {
        return listOf(Uri.parse(getString(R.string.media_url_dash)))
    }

    override fun buildMediaPlayer(): MyMediaPlayer {
        return MediaPlayerFactory.buildDashPlayer(this)
    }
}
package solution.codebox.recorderapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout.VERTICAL
import kotlinx.android.synthetic.main.activity_media_player_options.*
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.Dash
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.LocalFile
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.MP3
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.MP4
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.Streaming
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.Youtube
import solution.codebox.recorderapp.activities.mediaPlayer.DashMediaPlayerActivity
import solution.codebox.recorderapp.activities.mediaPlayer.Mp3MediaPlayerActivity
import solution.codebox.recorderapp.activities.mediaPlayer.Mp4MediaPlayerActivity
import solution.codebox.recorderapp.adapter.MediaPlayerOptionAdapter
import solution.codebox.recorderapp.adapter.MediaPlayerOptionAdapter.MediaPlayerOptionListener
import solution.codebox.recorderapp.data.MediaPlayerOption

class MediaPlayerOptionsActivity : AppCompatActivity() {
    private var optionAdapter = MediaPlayerOptionAdapter(getOptions(), object : MediaPlayerOptionListener {
        override fun onItemClick(option: String) {
            val intent = when (option) {
                MediaType.MP3 -> Intent(this@MediaPlayerOptionsActivity, Mp3MediaPlayerActivity::class.java)
                MediaType.MP4 -> Intent(this@MediaPlayerOptionsActivity, Mp4MediaPlayerActivity::class.java)
                MediaType.Streaming -> Intent(this@MediaPlayerOptionsActivity, DashMediaPlayerActivity::class.java)
                MediaType.Dash -> Intent(this@MediaPlayerOptionsActivity, DashMediaPlayerActivity::class.java)
                MediaType.Youtube -> Intent(this@MediaPlayerOptionsActivity, Mp4MediaPlayerActivity::class.java)
                MediaType.LocalFile -> Intent(this@MediaPlayerOptionsActivity, Mp4MediaPlayerActivity::class.java)
                else -> {
                    Intent(this@MediaPlayerOptionsActivity, Mp4MediaPlayerActivity::class.java)
                }
            }
            startActivity(intent)
        }

    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player_options)

        initMediaPlayerOption()
    }

    private fun initMediaPlayerOption() {
        rvMediaPlayerOption.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        rvMediaPlayerOption.adapter = optionAdapter
    }

    private fun getOptions(): List<MediaPlayerOption> {
        return listOf(MediaPlayerOption(MP3, R.color.colorTextWhite, R.color.colorBGYouTube),
                MediaPlayerOption(MP4, R.color.colorTextWhite, R.color.colorBGCheeseFactory),
                MediaPlayerOption(Streaming, R.color.colorTextWhite, R.color.colorBGPhucLong),
                MediaPlayerOption(Dash, R.color.colorTextWhite, R.color.colorBGPorn),
                MediaPlayerOption(LocalFile, R.color.colorTextWhite, R.color.colorBGTwitter),
                MediaPlayerOption(Youtube, R.color.colorTextWhite, R.color.colorBGYouTube))
    }

    object MediaType {
        const val MP3 = "MP3"
        const val MP4 = "MP4"
        const val Streaming = "Streaming"
        const val Dash = "DASH"
        const val LocalFile = "Local File"
        const val Youtube = "Youtube"
    }
}

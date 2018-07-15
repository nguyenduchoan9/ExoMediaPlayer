package solution.codebox.recorderapp.activities

// https://github.com/google/ExoPlayer/tree/dev-v2
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout.VERTICAL
import kotlinx.android.synthetic.main.activity_media_player_options.*
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.Dash
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.MP3OrMP4
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.Streaming
import solution.codebox.recorderapp.activities.MediaPlayerOptionsActivity.MediaType.Youtube
import solution.codebox.recorderapp.activities.mediaPlayer.DashMediaPlayerActivity
import solution.codebox.recorderapp.activities.mediaPlayer.Mp3OrMp4MediaPlayerActivity
import solution.codebox.recorderapp.adapter.MediaPlayerOptionAdapter
import solution.codebox.recorderapp.adapter.MediaPlayerOptionAdapter.MediaPlayerOptionListener
import solution.codebox.recorderapp.model.MediaPlayerOption
import solution.codebox.recorderapp.utils.Constant

class MediaPlayerOptionsActivity : BaseActivity() {
    private var optionAdapter = MediaPlayerOptionAdapter(getOptions(), object : MediaPlayerOptionListener {
        override fun onItemClick(option: String) {
            when {
                isAllowToReadAndWrite() -> openMediaPlayerBy(option)
                else -> requestReadAndWriteFilePermission()
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player_options)
        initMediaPlayerOption()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.REQUEST_CODE_WRITE_AND_READ_PERMISSION -> optionAdapter?.let { openMediaPlayerBy(it.lastSelectedItem.option) }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initMediaPlayerOption() {
        rvMediaPlayerOption.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        rvMediaPlayerOption.adapter = optionAdapter
    }

    private fun getOptions(): List<MediaPlayerOption> {
        return listOf(MediaPlayerOption(MP3OrMP4, R.color.colorTextWhite, R.color.colorBGTwitter),
                MediaPlayerOption(Streaming, R.color.colorTextWhite, R.color.colorBGPhucLong),
                MediaPlayerOption(Dash, R.color.colorTextWhite, R.color.colorBGPorn),
                MediaPlayerOption(Youtube, R.color.colorTextWhite, R.color.colorBGYouTube))
    }

    object MediaType {
        const val MP3OrMP4 = "MP3 Or MP4"
        const val Streaming = "Streaming"
        const val Dash = "DASH"
        const val Youtube = "Youtube"
    }

    private fun openMediaPlayerBy(option: String) {
        val intent = when (option) {
            MediaType.MP3OrMP4 -> Intent(this@MediaPlayerOptionsActivity, Mp3OrMp4MediaPlayerActivity::class.java)
            MediaType.Streaming -> Intent(this@MediaPlayerOptionsActivity, DashMediaPlayerActivity::class.java)
            MediaType.Dash -> Intent(this@MediaPlayerOptionsActivity, DashMediaPlayerActivity::class.java)
            MediaType.Youtube -> Intent(this@MediaPlayerOptionsActivity, DashMediaPlayerActivity::class.java)
            else -> {
                Intent(this@MediaPlayerOptionsActivity, DashMediaPlayerActivity::class.java)
            }
        }
        startActivity(intent)
    }
}

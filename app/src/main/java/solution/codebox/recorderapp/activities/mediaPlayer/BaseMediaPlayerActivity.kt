package solution.codebox.recorderapp.activities.mediaPlayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.activities.BaseActivity
import solution.codebox.recorderapp.model.DialogOption
import solution.codebox.recorderapp.model.MediaPlayerSavedInstance
import solution.codebox.recorderapp.utils.Constant
import solution.codebox.recorderapp.view.dialogFactory.DialogFactory
import solution.codebox.recorderapp.view.dialogFactory.OptionEventListener
import solution.codebox.recorderapp.view.mediaPlayer.MyMediaPlayer
import solution.codebox.recorderapp.view.mediaPlayer.MyMediaPlayerListener
import java.io.File

abstract class BaseMediaPlayerActivity : BaseActivity() {
    lateinit var mediaPlayer: MyMediaPlayer
    val MEDIA_PLAYER_SAVED_INSTANCE = "MEDIA_PLAYER_SAVED_INSTANCE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer = buildMediaPlayer()
        mediaPlayer?.let {
            it.myMediaPlayerListener = myMediaListener
            it.setMediaUris(mediaUris())
            savedInstanceState?.let { bundle ->
                if (bundle.containsKey(MEDIA_PLAYER_SAVED_INSTANCE)) {
                    val mediaPlayerSavedInstance = bundle.get(MEDIA_PLAYER_SAVED_INSTANCE) as MediaPlayerSavedInstance
                    it.setMediaPlayerState(mediaPlayerSavedInstance)
                }
            }
        }

        tvOpenYourVideo.setOnClickListener { _ ->
            DialogFactory.showDialogContainOptionsWithoutTitle(this@BaseMediaPlayerActivity, getDialogOptions())
        }
    }

    abstract fun buildMediaPlayer(): MyMediaPlayer

    abstract fun mediaUris(): List<Uri>

    abstract fun getVideoUriAndPlay(filePath: String)

    abstract fun getDialogOptions(): List<DialogOption>

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            mediaPlayer?.initializePlayer(playerView)
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || mediaPlayer == null) {
            mediaPlayer?.initializePlayer(playerView)
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            mediaPlayer?.releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            mediaPlayer?.releasePlayer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            val mediaPlayerSavedInstance = mediaPlayer.createMediaPlayerSavedInstance()
            outState.putParcelable(MEDIA_PLAYER_SAVED_INSTANCE, mediaPlayerSavedInstance)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK ->
                when (requestCode) {
                    Constant.REQUEST_CODE_PICK_VIDEO_FROM_GALLERY -> data?.dataString?.let { getVideoUriAndPlay(it) }
                    else -> super.onActivityResult(requestCode, resultCode, data)
                }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }

    }

    protected fun informInvalidMediaFile() {
        showLongMessageToast(R.string.error_msg_your_video_format_is_invalid)
    }

    protected fun createUriFromFilePath(filePath: String) = Uri.fromFile(File(filePath))

    private var myMediaListener = object : MyMediaPlayerListener {
        override fun openInLandscapeMode() {
            mediaPlayer.releasePlayer()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        override fun openInPortraitMode() {
            mediaPlayer.releasePlayer()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        override fun onMediaControllerVisibilityChange(visibility: Int) {
            tvOpenYourVideo.visibility = visibility
        }
    }

    protected fun openVideoOnYourDeviceDialogOption(): DialogOption {
        return DialogOption(getString(R.string.DialogOptionOpenVideoOnDevice), object : OptionEventListener<Unit> {
            override fun <T> onClick(e: T) {
                mediaPlayer?.releasePlayer()
                openGalleryToChooseVideo()
            }
        })
    }

    protected fun openVideoByUrlDialogOption(): DialogOption {
        return DialogOption(getString(R.string.DialogOptionOpenByUrl), object : OptionEventListener<Unit> {
            override fun <T> onClick(e: T) {
                showLongMessageToast(R.string.information_msg_this_feature_is_coming_soon)
            }
        })
    }
}
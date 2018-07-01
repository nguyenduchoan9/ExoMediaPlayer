package solution.codebox.recorderapp.activities.mediaPlayer

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.view.MyMediaPlayer

abstract class BaseMediaPlayerActivity : AppCompatActivity() {
    protected lateinit var mediaPlayer: MyMediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer = buildMediaPlayer()
        mediaPlayer?.setMediaUris(mediaUris())
    }

    abstract fun buildMediaPlayer(): MyMediaPlayer

    abstract fun mediaUris(): List<Uri>

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

    protected open fun buildMediaSource(uri: Uri) {
        //        Dash
//        var dataSourceFactory =
//                DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER)
//        var dashChunkSourceFactory =
//                DefaultDashChunkSource.Factory(dataSourceFactory)
//        return DashMediaSource.Factory(dashChunkSourceFactory, dataSourceFactory)
//                .setManifestParser(DashManifestParser())
//                .createMediaSource(uri)

        //        // these are reused for both media sources we create below
        //        DefaultExtractorsFactory extractorsFactory =
        //                new DefaultExtractorsFactory();
        //        DefaultHttpDataSourceFactory dataSourceFactory =
        //                new DefaultHttpDataSourceFactory("user-agent");
        //
        //        ExtractorMediaSource videoSource =
        //                new ExtractorMediaSource(uri, dataSourceFactory,
        //                        extractorsFactory, null, null);
        //
        //        Uri audioUri = Uri.parse(getString(R.string.media_url_mp3));
        //        ExtractorMediaSource audioSource =
        //                new ExtractorMediaSource(audioUri, dataSourceFactory,
        //                        extractorsFactory, null, null);
        //
        //        return new ConcatenatingMediaSource(audioSource, videoSource);
    }
}
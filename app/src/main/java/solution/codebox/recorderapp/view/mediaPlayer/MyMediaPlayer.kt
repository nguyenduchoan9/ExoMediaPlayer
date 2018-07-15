package solution.codebox.recorderapp.view.mediaPlayer

import android.content.Context
import android.net.Uri
import android.util.Pair
import android.view.View
import android.widget.ImageButton
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.util.ErrorMessageProvider
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.model.MediaPlayerSavedInstance
import solution.codebox.recorderapp.utils.Utils

interface MyMediaPlayerListener {
    fun onMediaControllerVisibilityChange(visibility: Int)
    fun openInPortraitMode()
    fun openInLandscapeMode()
}

class MyMediaPlayer(private var context: Context,
                    private var buildMediaSource: BuildMediaSource,
                    private var bandwidthMeter: BandwidthMeter) {
    private lateinit var exoScreenMode: ImageButton

    lateinit var uris: List<Uri>
    var playWhenReady: Boolean = false
    var playbackPosition: Long = 0
    var currentWindow: Int = 0
    var repeatMode: Int = Player.REPEAT_MODE_ALL

    var myMediaPlayerListener: MyMediaPlayerListener? = null
    private var player: SimpleExoPlayer? = null

    fun setMediaUris(uris: List<Uri>) {
        this.uris = uris
    }

    fun initializePlayer(playerView: PlayerView) {
        if (player == null) {
            val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            player = ExoPlayerFactory.newSimpleInstance(
                    DefaultRenderersFactory(context),
                    DefaultTrackSelector(adaptiveTrackSelectionFactory),
                    DefaultLoadControl()
            )
            playerView?.let {
                exoScreenMode = it.findViewById(R.id.exo_screen_mode)
                when {
                    Utils.isDeviceInLandscape(context) -> {
                        exoScreenMode.setImageResource(R.drawable.ic_portrait_mode)
                    }
                    else -> exoScreenMode.setImageResource(R.drawable.ic_landscape_mode)
                }
                exoScreenMode.setOnClickListener {
                    myMediaPlayerListener?.let {
                        when {
                            Utils.isDeviceInLandscape(context) -> {
                                exoScreenMode.setImageResource(R.drawable.ic_portrait_mode)
                                it.openInPortraitMode()
                            }
                            else -> {
                                exoScreenMode.setImageResource(R.drawable.ic_landscape_mode)
                                it.openInLandscapeMode()
                            }
                        }
                    }
                }
                it.player = player
                it.setControllerVisibilityListener(controllerVisibility)
                it.setErrorMessageProvider(PlayerErrorMessageProvider())
            }
        }
        preparePlayerBy(uris)
    }

    fun changeMediaSource(uris: List<Uri>) {
        resetPlayerTrack()
        this.uris = uris
    }


    fun releasePlayer() {
        player?.let {
            repeatMode = it.repeatMode
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }

    fun setMediaPlayerState(mediaPlayerSavedInstance: MediaPlayerSavedInstance){
        uris = mediaPlayerSavedInstance.uris
        playWhenReady = mediaPlayerSavedInstance.playWhenReady
        playbackPosition = mediaPlayerSavedInstance.playBackPosition
        currentWindow = mediaPlayerSavedInstance.currentWindow
        repeatMode = mediaPlayerSavedInstance.repeatMode
    }

    fun createMediaPlayerSavedInstance(): MediaPlayerSavedInstance {
        return MediaPlayerSavedInstance(uris, playWhenReady, playbackPosition, currentWindow, repeatMode)
    }

    private fun resetPlayerTrack() {
        repeatMode = Player.REPEAT_MODE_ALL
        playbackPosition = 0
        currentWindow = 0
    }

    private fun preparePlayerBy(uris: List<Uri>) {
        player?.let {
            it.repeatMode = repeatMode
            it.seekTo(currentWindow, playbackPosition)
            it.playWhenReady = playWhenReady
            val mediaSource = buildMediaSource(uris)
            it.prepare(mediaSource, false, false)
        }
    }

    private fun buildMediaSource(uris: List<Uri>): MediaSource {
        return buildMediaSource.buildMediaSource(uris)

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


    private val controllerVisibility = PlayerControlView.VisibilityListener { visibility -> myMediaPlayerListener?.onMediaControllerVisibilityChange(visibility) }

    private inner class PlayerErrorMessageProvider : ErrorMessageProvider<ExoPlaybackException> {
        override fun getErrorMessage(e: ExoPlaybackException): Pair<Int, String> {
            var errorString = getString(R.string.error_generic, null)
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                val cause = e.rendererException
                if (cause is MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    errorString = if (cause.decoderName == null) {
                        when {
                            cause.cause is MediaCodecUtil.DecoderQueryException -> getString(R.string.error_querying_decoders, null)
                            cause.secureDecoderRequired -> getString(
                                    R.string.error_no_secure_decoder, cause.mimeType)
                            else -> getString(R.string.error_no_decoder, cause.mimeType)
                        }
                    } else {
                        getString(
                                R.string.error_instantiating_decoder,
                                cause.decoderName)
                    }
                }
            }
            return Pair.create(0, errorString)
        }
    }

    private fun onScreenModeClick() = View.OnClickListener {

    }

    private fun getString(id: Int, formats: String?): String = context.getString(id, formats)
}
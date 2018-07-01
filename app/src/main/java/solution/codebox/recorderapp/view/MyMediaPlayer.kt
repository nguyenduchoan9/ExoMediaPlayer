package solution.codebox.recorderapp.view

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import solution.codebox.recorderapp.R

class MyMediaPlayer(private var context: Context,
                    private var buildMediaSource: BuildMediaSource,
                    private var bandwidthMeter: BandwidthMeter) {
    private lateinit var uris: List<Uri>

    private var player: ExoPlayer? = null
    private var playWhenReady: Boolean = false
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0

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
            playerView?.player = player
        }
        player?.let {
            it.playWhenReady = playWhenReady
            it.seekTo(currentWindow, playbackPosition)
            val mediaSource = buildMediaSource(uris)
            it.prepare(mediaSource, true, false)
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

    public fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }
}
package solution.codebox.recorderapp.view

import android.content.Context
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter

class MediaPlayerFactory {
    companion object {
        fun buildMP3Player(context: Context) : MyMediaPlayer {
            var bandwidthMeter = DefaultBandwidthMeter()
            var buildMediaSource = BuildMP3MediaSource(context, bandwidthMeter)
            return MyMediaPlayer(context, buildMediaSource, bandwidthMeter)
        }

        fun buildMP4Player(context: Context) : MyMediaPlayer {
            var bandwidthMeter = DefaultBandwidthMeter()
            var buildMediaSource = BuildMP3MediaSource(context, bandwidthMeter)
            return MyMediaPlayer(context, buildMediaSource, bandwidthMeter)
        }

        fun buildDashPlayer(context: Context) : MyMediaPlayer {
            var bandwidthMeter = DefaultBandwidthMeter()
            var buildMediaSource = BuildDashMediaSource(context, bandwidthMeter)
            return MyMediaPlayer(context, buildMediaSource, bandwidthMeter)

        }
    }
}
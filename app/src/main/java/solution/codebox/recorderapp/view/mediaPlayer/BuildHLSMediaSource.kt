package solution.codebox.recorderapp.view.mediaPlayer

import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.TransferListener

class BuildHLSMediaSource(context: Context,
                          bandwidthMeter: TransferListener<in DataSource>)
    {
//    override fun buildMediaSource(uris: List<Uri>): MediaSource {
//        val loadControl = DefaultLoadControl()
//        val dataSourceFactory = DefaultDataSourceFactory(context,
//                Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter)
//        // This is the MediaSource representing the media to be played.
//        val extractorsFactory = DefaultExtractorsFactory()
//        val hlsMediaSource = HlsMediaSource(uris.first(), dataSourceFactory, extractorsFactory, )
//        return null
//    }
}
package solution.codebox.recorderapp.view

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util

class BuildMP3MediaSource(context: Context,
                                   bandwidthMeter: TransferListener<in DataSource>)
    : BuildMediaSource(context, bandwidthMeter) {

    override fun buildMediaSource(uris: List<Uri>): MediaSource {
        val uri = uris.firstOrNull()

        val dataSourceFactory = DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter)
        // This is the MediaSource representing the media to be played.
        val extractorsFactory = DefaultExtractorsFactory()
        return ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(extractorsFactory)
                .createMediaSource(uri)
    }
}
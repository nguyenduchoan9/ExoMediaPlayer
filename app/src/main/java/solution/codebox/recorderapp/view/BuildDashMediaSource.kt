package solution.codebox.recorderapp.view

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener

class BuildDashMediaSource(context: Context,
                           bandwidthMeter: TransferListener<in DataSource>)
    : BuildMediaSource(context, bandwidthMeter) {

    override fun buildMediaSource(uris: List<Uri>): MediaSource {
        val uri = uris.firstOrNull()
        var dataSourceFactory =
                DefaultHttpDataSourceFactory("ua", bandwidthMeter)
        var dashChunkSourceFactory =
                DefaultDashChunkSource.Factory(dataSourceFactory)
        return DashMediaSource(uri, dataSourceFactory,
                dashChunkSourceFactory, null, null)
    }
}
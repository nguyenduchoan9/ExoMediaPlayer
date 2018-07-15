package solution.codebox.recorderapp.view.mediaPlayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.TransferListener

abstract class BuildMediaSource(val context: Context,
                                val bandwidthMeter: TransferListener<in DataSource>) {
    abstract fun buildMediaSource(uris: List<Uri>): MediaSource
}
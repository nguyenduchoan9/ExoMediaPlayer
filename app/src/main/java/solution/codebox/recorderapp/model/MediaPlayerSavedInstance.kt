package solution.codebox.recorderapp.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

class MediaPlayerSavedInstance(val uris: List<Uri>,
                               val playWhenReady: Boolean,
                               val playBackPosition: Long,
                               val currentWindow: Int,
                               val repeatMode: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Uri.CREATOR),
            parcel.readByte() != 0.toByte(),
            parcel.readLong(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(uris)
        parcel.writeByte(if (playWhenReady) 1 else 0)
        parcel.writeLong(playBackPosition)
        parcel.writeInt(currentWindow)
        parcel.writeInt(repeatMode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaPlayerSavedInstance> {
        override fun createFromParcel(parcel: Parcel): MediaPlayerSavedInstance {
            return MediaPlayerSavedInstance(parcel)
        }

        override fun newArray(size: Int): Array<MediaPlayerSavedInstance?> {
            return arrayOfNulls(size)
        }
    }
}
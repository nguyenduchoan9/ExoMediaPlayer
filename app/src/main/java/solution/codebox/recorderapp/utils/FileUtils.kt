package solution.codebox.recorderapp.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

class FileUtils {
    companion object {
        fun isExternalStorageMounted(): Boolean
                = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)

        fun getRealPathFromURI(context: Context, uri: Uri): String? {
            var uri = uri
            var selection: String?
            var selectionArgs: Array<String>?
            if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.applicationContext, uri)) {
                when {
                    isExternalStorageDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                    isDownloadsDocument(uri) -> {
                        val id = DocumentsContract.getDocumentId(uri)
                        if (id.startsWith(Constant.RAW_FILE_SCHEME)) {
                            return id.replaceFirst(Constant.RAW_FILE_SCHEME.toRegex(), Constant.BLANK)
                        }
                        uri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                        return getDataColumn(context, uri, null, null)
                    }
                    isMediaDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]
                        when (type) {
                            "image" -> uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                        selection = "_id=?"
                        selectionArgs = arrayOf(split[1])
                        return getDataColumn(context, uri, selection, selectionArgs)
                    }
                    else -> {
                    }
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
            return null
        }

        private fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }

        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        private fun getDataColumn(context: Context, uri: Uri, selection: String?,
                                  selectionArgs: Array<String>?): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }
    }
}
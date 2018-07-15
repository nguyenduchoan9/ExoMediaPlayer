package solution.codebox.recorderapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import solution.codebox.recorderapp.utils.Constant
import solution.codebox.recorderapp.utils.FileUtils

abstract class BaseActivity : AppCompatActivity() {

    fun showShortMessageToast(msg: String) {
        Toast.makeText(this, msg, LENGTH_SHORT).show()
    }

    fun showShortMessageToast(stringId: Int) {
        Toast.makeText(this, getString(stringId), LENGTH_SHORT).show()
    }

    fun showLongMessageToast(msg: String) {
        Toast.makeText(this, msg, LENGTH_LONG).show()
    }

    fun showLongMessageToast(stringId: Int) {
        Toast.makeText(this, getString(stringId), LENGTH_LONG).show()
    }

    fun requestReadAndWriteFilePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@BaseActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this@BaseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        } else
            ActivityCompat.requestPermissions(this@BaseActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Constant.REQUEST_CODE_WRITE_AND_READ_PERMISSION)
    }

    fun isAllowToReadAndWrite(): Boolean {
        val resultReadExternalStorage = ContextCompat.checkSelfPermission(this@BaseActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
        val resultWriteExternalStorage = ContextCompat.checkSelfPermission(this@BaseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return resultReadExternalStorage == PackageManager.PERMISSION_GRANTED && resultWriteExternalStorage == PackageManager.PERMISSION_GRANTED
    }

    fun openGalleryToChooseVideo() {
        var intent = if (FileUtils.isExternalStorageMounted())
            Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        else
            Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, Constant.REQUEST_CODE_PICK_VIDEO_FROM_GALLERY)
    }
}
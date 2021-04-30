package com.patelestate.fragment.home.pdf

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gcacace.signaturepad.views.SignaturePad
import com.patelestate.R
import com.patelestate.fragment.home.pdf.PDFViewActivity.Companion.drwnImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class SignatureActivity : AppCompatActivity() {
    private var mSignaturePad: SignaturePad? = null
    private var mClearButton: Button? = null
    private var mSaveButton: Button? = null
    private var back_btn: ImageView? = null
    fun replaceColor(src: Bitmap?): Bitmap? {
        if (src == null) return null
        val width: Int = src.getWidth()
        val height: Int = src.getHeight()
        val pixels = IntArray(width * height)
        src.getPixels(pixels, 0, 1 * width, 0, 0, width, height)
        for (x in pixels.indices) {
            //    pixels[x] = ~(pixels[x] << 8 & 0xFF000000) & Color.BLACK;
            if (pixels[x] == Color.WHITE) pixels[x] = 0
        }
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)
        mSignaturePad = findViewById<View>(R.id.signature_pad) as SignaturePad?
        mSignaturePad!!.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
//                Toast.makeText(this@SignatureActivity, "OnStartSigning", Toast.LENGTH_SHORT).show()
            }

            override fun onSigned() {
                mSaveButton!!.isEnabled = true
                mClearButton!!.isEnabled = true
            }

            override fun onClear() {
                mSaveButton!!.isEnabled = false
                mClearButton!!.isEnabled = false
            }
        })
        mClearButton = findViewById<View>(R.id.clear_button) as Button?
        mSaveButton = findViewById<View>(R.id.save_button) as Button?
        back_btn = findViewById<View>(R.id.back_btn) as ImageView?
        mClearButton!!.setOnClickListener { mSignaturePad!!.clear() }
        mSaveButton!!.setOnClickListener {
            val signatureBitmap: Bitmap = mSignaturePad!!.getSignatureBitmap()
            if (addJpgSignatureToGallery(replaceColor(signatureBitmap))) {
//                    Toast.makeText(SignatureActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                    this@SignatureActivity,
                    "Unable to store the signature",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        back_btn!!.setOnClickListener {
            finish()
        }
    }

    fun getAlbumStorageDir(albumName: String?): File {
        // Get the directory for the user's public pictures directory.
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ), albumName
        )
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created")
        }
        return file
    }

    @Throws(IOException::class)
    fun saveBitmapToJPG(bitmap: Bitmap?, photo: File?) {
        val newBitmap: Bitmap =
            Bitmap.createBitmap(bitmap!!.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        //        canvas.drawColor(Color.TRANSPARENT);

//        newBitmap.setHasAlpha(true);
        canvas.drawBitmap(bitmap!!, 0f, 0f, null)
        val stream: OutputStream = FileOutputStream(photo)
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun addJpgSignatureToGallery(signature: Bitmap?): Boolean {
        var result = false
        try {
            val photo = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.png", System.currentTimeMillis())
            )
            drwnImage = photo.absolutePath
            saveBitmapToJPG(signature, photo)
            //            scanMediaFile(photo);
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }
}
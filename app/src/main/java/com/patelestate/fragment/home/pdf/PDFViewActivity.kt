package com.patelestate.fragment.home.pdf

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itextpdf.text.pdf.PRStream
import com.itextpdf.text.pdf.PdfName
import com.itextpdf.text.pdf.PdfObject
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfImageObject
import com.patelestate.R
import com.patelestate.fragment.home.pdf.PDFViewActivity
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class PDFViewActivity : AppCompatActivity() {
    var button: TextView? = null
    var images: MutableList<String> = ArrayList()
    var pdfView: PDFView? = null
    var file: File? = null
    private var back_btn: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        button = findViewById(R.id.button)
        pdfView = findViewById<View>(R.id.pdfView) as PDFView

        back_btn = findViewById<View>(R.id.back_btn) as ImageView?
        if (checkPermission()) {
            copyAsset("test_dummy.pdf")
        } else {
            requestPermission()
        }
        button!!.setOnClickListener(View.OnClickListener { v: View? ->
//            Pickpdfstorage();
            if (checkPermission()) {
                val intent = Intent(this@PDFViewActivity, SignatureActivity::class.java)
                startActivityForResult(intent, 0)
            } else {
                requestPermission()
            }
        })
        back_btn!!.setOnClickListener {
            finish()
        }
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
        file = File(Environment.getExternalStorageDirectory().toString() + "/test_dummy.pdf")
        pdfView!!.fromFile(file).load()

    }

    private fun copyAsset(filename: String) {
        val dirPath = Environment.getExternalStorageDirectory().absolutePath
        val assetManager = assets
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            `in` = assetManager.open(filename)
            val outFile = File(dirPath, filename)
            out = FileOutputStream(outFile)
            copyFile(`in`, out)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this@PDFViewActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return if (result == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            false
        }
    }

    private fun overlay(bmp1: String): Bitmap {
        val imageOne = BitmapFactory.decodeFile(bmp1).copy(Bitmap.Config.ARGB_8888, true)
        var imageTwo = BitmapFactory.decodeFile(drwnImage)
        imageTwo = Bitmap.createScaledBitmap(imageTwo!!, 100, 60, false)
        val canvas = Canvas(imageOne)
        canvas.drawBitmap(imageTwo, 80f, 710f, Paint())

        val destination = File(Environment.getExternalStorageDirectory().toString() + "/Test")
        val file = File(images[images.size - 1])

        // check if file already exists, then delete it.
        if (file.exists()) file.delete()

        // Saving image in PNG format with 100% quality.
        try {
            val out = FileOutputStream(file)
            imageOne.compress(Bitmap.CompressFormat.PNG, 100, out)
            Log.v("Saved Image - ", file.absolutePath)
            out.flush()
            out.close()
            createPDFWithMultipleImage()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return imageOne
    }

    private fun createPDFWithMultipleImage() {
        val file = outputFile
        if (file != null) {
            try {
                val fileOutputStream = FileOutputStream(file)
                val pdfDocument = PdfDocument()
                for (i in images.indices) {
                    val bitmap = BitmapFactory.decodeFile(images[i])
                    val pageInfo = PageInfo.Builder(bitmap.width, bitmap.height, i + 1).create()
                    val page = pdfDocument.startPage(pageInfo)
                    val canvas = page.canvas
                    val paint = Paint()
                    paint.color = Color.BLUE
                    canvas.drawPaint(paint)
                    canvas.drawBitmap(bitmap, 0f, 0f, null)
                    pdfDocument.finishPage(page)
                    bitmap.recycle()
                }
                pdfDocument.writeTo(fileOutputStream)
                pdfDocument.close()
                pdfView!!.fromFile(file).load()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private val outputFile: File?
        private get() {
            val root = File(Environment.getExternalStorageDirectory(), "My PDF Folder")
            var isFolderCreated = true
            if (!root.exists()) {
                isFolderCreated = root.mkdir()
            }
            else
            {
                root.delete()
            }
            return if (isFolderCreated) {
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                    .format(Date())
                val imageFileName = "PDF_$timeStamp"
                File(root, "$imageFileName.pdf")
            } else {
                Toast.makeText(this, "Folder is not created", Toast.LENGTH_SHORT).show()
                null
            }
        }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@PDFViewActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            ActivityCompat.requestPermissions(
                this@PDFViewActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        } else {
            ActivityCompat.requestPermissions(
                this@PDFViewActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .")
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .")
            }
        }
    }

    private fun Pickpdfstorage() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "application/pdf"
        startActivityForResult(intent, 10)
    }

    @Throws(IOException::class)
    private fun OpenPdfActivity(absolutePath: File) {
        // Images will be saved in Test folder.
        val destination = File(Environment.getExternalStorageDirectory().toString() + "/Test")
        getImagesFromPDF(absolutePath, destination)
    }

    // This method is used to extract all pages in image (PNG) format.
    @Throws(IOException::class)
    private fun getImagesFromPDF(pdfFilePath: File, DestinationFolder: File) {
        images.clear()
        // Check if destination already exists then delete destination folder.
        if (DestinationFolder.exists()) {
            DestinationFolder.delete()
        }

        // Create empty directory where images will be saved.
        DestinationFolder.mkdirs()

        // Reading pdf in READ Only mode.
        val fileDescriptor =
            ParcelFileDescriptor.open(pdfFilePath, ParcelFileDescriptor.MODE_READ_ONLY)

        // Initializing PDFRenderer object.
        val renderer = PdfRenderer(fileDescriptor)

        // Getting total pages count.
        val pageCount = renderer.pageCount
        // Iterating pages
        for (i in 0 until pageCount) {
//            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            // Getting Page object by opening page.
            val page = renderer.openPage(i)

            // Creating empty bitmap. Bitmap.Config can be changed.
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)

            // Creating Canvas from bitmap.
            val canvas = Canvas(bitmap)

            // Set White background color.
            canvas.drawColor(Color.WHITE)

            // Draw bitmap.
            canvas.drawBitmap(bitmap, 0f, 0f, null)

            // Rednder bitmap and can change mode too.
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            // closing page
            page.close()

            // saving image into sdcard.
            val file = File(DestinationFolder.absolutePath, "image$i.png")

            // check if file already exists, then delete it.
            if (file.exists()) file.delete()

            // Saving image in PNG format with 100% quality.
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                Log.v("Saved Image - ", file.absolutePath)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            images.add(file.absolutePath) //path of image in storage

//   images.add("/storage/emulated/0/imageThree");
//   ... //add more images
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) { // Get the Uri of the selected file
            if (resultCode == RESULT_OK) {
                if (null != data!!.data) {
                    val uri = data.data
                    val file: File
                    if (uri!!.scheme == "content") {
                        file = File(cacheDir, data.data!!.lastPathSegment)
                        try {
                            val iStream = contentResolver.openInputStream(
                                uri
                            )
                            var output: FileOutputStream? = null
                            output = FileOutputStream(file)
                            val buffer = ByteArray(1024)
                            var size: Int
                            while (iStream!!.read(buffer).also { size = it } != -1) {
                                output.write(buffer, 0, size)
                            }
                            iStream.close()
                            output.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } else file = File(uri.path)
                    try {
                        OpenPdfActivity(file)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } else if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {
                    OpenPdfActivity(file!!)
                    overlay(images[images.size - 1])
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
        var drwnImage = ""
        fun getBitmap(filePath: String?): Bitmap {
            val options = BitmapFactory.Options()
            //        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);

//        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math
//                .abs(options.outWidth - 100);
//        if (options.outHeight * options.outWidth * 2 >= 16384) {
//            double sampleSize = scaleByHeight
//                    ? options.outHeight / 100
//                    : options.outWidth / 100;
//            options.inSampleSize =
//                    (int) Math.pow(2d, Math.floor(
//                            Math.log(sampleSize) / Math.log(2d)));
//        }
//        options.inJustDecodeBounds = false;
//        options.inTempStorage = new byte[512];
            return BitmapFactory.decodeFile(filePath)
        }
    }
}
package QuizApp.com.Controller

import QuizApp.com.Model.DownloadingObject
import QuizApp.com.Model.Plant
import QuizApp.com.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var cameraButton: Button? = null
    private var galleryButton: Button? = null
    private var imageTaken: ImageView? = null
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null

    val OPEN_CAMERA_BUTTON_REQUEST_ID = 1000
    val OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if(isNetworkAvailable()){
            val innerClassObject = DownloadingPlantText()
            innerClassObject.execute()
        }

    //    val innerClassObject = DownloadingPlantText()
      //  innerClassObject.execute()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val myPlant: Plant =
            Plant("", "", "", "", "", "", 0, 0)


        cameraButton?.setOnClickListener(View.OnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, OPEN_CAMERA_BUTTON_REQUEST_ID)

        })

        btnOpenPhotoGallery?.setOnClickListener(View.OnClickListener {


            Toast.makeText(this, "The Photo Gallery Button  is clicked",
                Toast.LENGTH_SHORT).show()


            val intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID)



        })




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == OPEN_CAMERA_BUTTON_REQUEST_ID) {



            if (resultCode == Activity.RESULT_OK) {

                val imageData = data?.getExtras()?.get("data") as Bitmap
                imageTaken?.setImageBitmap(imageData)


            }

        }

        if (requestCode == OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID
        ) {

            if (resultCode == Activity.RESULT_OK) {

                val contentURI = data?.getData()
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,
                    contentURI)
                imgView.setImageBitmap(bitmap)

            }


        }

    }
    fun firstClicked() = Unit
    fun secondClicked() = Unit
    fun thirdClicked() = Unit
    fun fourthClicked() = Unit

    inner class DownloadingPlantText: AsyncTask<String, Int, List<Plant>>(){
        override fun doInBackground(vararg params: String?): List<Plant>? {
            //background process

            val downloadingObject = DownloadingObject()
            var jsonData =  downloadingObject.downloadJSONDataFrom(
                "http://plantplaces.com/perl/mobile/flashcard.pl")

            Log.i("JSON", jsonData)

            return null
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)

            //Can access user interface thread. Not background
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Check internet connection
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        createAlert()
        return false
    }
    private fun createAlert(){
        val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("Network Error")
        alertDialog.setMessage("Please check your internet connection")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Retry") { dialog: DialogInterface?, which: Int ->
           isNetworkAvailable()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel") {
                dialog: DialogInterface?, which: Int ->
            Toast.makeText(this@MainActivity, "You nust be connected to the internet", Toast.LENGTH_SHORT).show()
            finish()
        }
        alertDialog.show()
    }

    }


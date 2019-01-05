package homura.tech.mediapicktest

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorFilter
import android.graphics.Point
import android.os.Bundle
import android.support.v4.graphics.BitmapCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
//import com.robertlevonyan.components.picker.ItemModel
//import com.robertlevonyan.components.picker.PickerDialog
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import homura.tech.lunagenerator.GetBitmapSize
import homura.tech.lunagenerator.ResizeInImageView
import homura.tech.lunagenerator.ResizeToGeneratorSize
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val IMAGE_GET_REQ = 112;
    //    private lateinit var picker :PickerDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)

        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
//            val options =MediaOptions.Builder()
//                .setIsCropped(true)
//                .setFixAspectRatio(true)
//                .canSelectMultiPhoto(false)
//                .selectPhoto()
//                .build()
//            MediaPickerActivity.open(this,IMAGE_GET_REQ,options)


//            val item =ItemModel(ItemModel.ITEM_GALLERY)
//             picker = PickerDialog
//                 .Builder(activity = this )// Activity or Fragment
//                .setTitle("Pick")          // String value or resource ID
//            .setTitleTextSize(12f)  // Text size of title
//            .setListType(PickerDialog.TYPE_GRID)       // Type of the picker, must be PickerDialog.TYPE_LIST or PickerDialog.TYPE_Grid
//            .setItems(items = arrayListOf(item))          // List of ItemModel-s which should be in picker
//            .create()
//            picker.setPickerCloseListener { type, uri ->
//                Log.d("Picker",uri.path)
//                when(type){
//                    ItemModel.ITEM_GALLERY -> Log.d("Picker",uri.path)
//                }
//            }
//
//            picker.show()
            Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(false)
                .maxSelectable(1)
                .imageEngine(PicassoEngine())
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .forResult(IMAGE_GET_REQ)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        picker.onPermissionsResult(requestCode,permissions,grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when(requestCode){
//            IMAGE_GET_REQ ->{
//                val media=MediaPickerActivity.getMediaItemSelected(data)
//                val img =media?.get(0)?.getPathOrigin(applicationContext)
//                img?.let {
//                    Log.d("IMG","IMG URI:"+it)
//                }
//            }
//            else ->{}
//        }

        /**
         * BitmapをimageViewに合わせてresize
         * Lunagenerator用にさらにresize
         */
        if (requestCode == IMAGE_GET_REQ && resultCode == RESULT_OK) {
            Log.d("Matisse", "Uris: " + Matisse.obtainResult(data));
            Log.d("Matisse", "Paths: " + Matisse.obtainPathResult(data));
            Log.e("Matisse", "Use the selected photos with original: " + (Matisse.obtainOriginalState(data)));

            val imgPath = Matisse.obtainPathResult(data)[0]
            val size = GetBitmapSize(imgPath)

            val col = 16
            val imgW = size.x
            val imgH = size.y

            //imageViewのサイズからBitmapを変更
            val imgBitmap = ResizeInImageView(got_image,imgPath)

            got_image.setImageBitmap(imgBitmap)

            val img4Gene = ResizeToGeneratorSize(imgBitmap,col)

            resize_image.setImageBitmap(img4Gene)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

package homura.tech.lunagenerator

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.util.Log
import android.widget.ImageView


fun ResizeInImageView(imgView:ImageView,imgPath:String):Bitmap{
    val imgRW = imgView.width
    val imgRH = imgView.height

    val size = GetBitmapSize(imgPath)
    val sampleSize = calculateInSampleSize(size, imgRW, imgRH)
    val options = BitmapFactory.Options().also {
        it.inJustDecodeBounds =false
        it.inSampleSize =sampleSize
        it.inPreferredConfig =Bitmap.Config.RGB_565
    }

    return BitmapFactory.decodeFile(imgPath,options)

}
fun ResizeToGeneratorSize(img:Bitmap,column:Int):Bitmap{
    /*
        e.g. imgW =100,imgH=200 col=14
     *  imageResizedWidth(imgRW) = col*2 =28
     *  imageResizedHeight(imgRH) =X
     *  imgW:imgH =imgRW:imgRH =100:200=28:X
     *  5600 =100x x=5600/100,imgRH=(imgRW*imgH)/imgW
     * */

    val imgW =img.width
    val imgH =img.height
    val imgRW = column * 2
    val imgRH = (imgRW * imgH) / imgW


    val reimg= Bitmap.createScaledBitmap(img,imgRW,imgRH,false)

    Log.d("ImgInfo","${reimg.width}:${reimg.height},${reimg.config.name}")
    return reimg
}

fun GetBitmapSize(path:String): Point {
    val options =BitmapFactory.Options().also {
        it.inJustDecodeBounds=true
    }
    BitmapFactory.decodeFile(path,options)
    return Point(options.outWidth,options.outHeight)
}
fun calculateInSampleSize(size: Point, reqWidth: Int, reqHeight: Int): Int {

    // 画像の元サイズ
    val height = size.y
    val width = size.x
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        if (width > height) {
            inSampleSize = Math.round(height.toFloat() / reqHeight.toFloat())
        } else {
            inSampleSize = Math.round(width.toFloat() / reqWidth.toFloat())
        }
    }
    return inSampleSize
}


package ru.lentrodev.retrofitrxjavaexample

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val builder = Picasso.Builder(this).apply {
            downloader(
                OkHttp3Downloader(
                    this@CustomApplication,
                    Int.MAX_VALUE.toLong()
                )
            )
            listener { picasso, uri, exception ->
                exception.printStackTrace()
            }
        }
        Picasso.setSingletonInstance(builder.build())
    }
}
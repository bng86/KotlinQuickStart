package tw.andyang.kotlinquickstart

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.stetho.Stetho

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: Application? = null

        fun instance() = INSTANCE!!
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

}
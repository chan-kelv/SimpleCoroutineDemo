package com.kelvin.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bttn_fetchSite.setOnClickListener { getHomeSite() }
    }

    private fun getHomeSite() {
        val networkService = NetworkService()

        // exception in case anything happens inside the coroutine
        val networkException = CoroutineExceptionHandler {_, throwable ->
            Log.e(MainActivity::class.java.simpleName, throwable.localizedMessage)
        }

        // this block will use the IO thread for network calls + switch to exception block if anything happens
        CoroutineScope(IO + networkException).launch {
            val siteHome = networkService.getHomePage()

            withContext(Main) { // whatever the previous context was, switch to doing this block on the main thread
                if (siteHome.isNotBlank()) {
                    text_page_result.text = siteHome
                }
            }
        }
    }
}

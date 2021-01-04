package com.pauk.justminingcompanion.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.pauk.justminingcompanion.R
import com.pauk.justminingcompanion.api.JustMiningService
import com.pauk.justminingcompanion.utils.PreferenceUtils
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors


class LoginActivity : AppCompatActivity() {
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var service: JustMiningService
    lateinit var keyView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.just-mining.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        service = retrofit.create(JustMiningService::class.java)

        if(PreferenceUtils.key?.length == 64) {
            canConnect(PreferenceUtils.key.orEmpty())
        }

        keyView = findViewById(com.pauk.justminingcompanion.R.id.apiKeyEditText)
        keyView.text = Editable.Factory.getInstance().newEditable(PreferenceUtils.key.orEmpty())
        keyView.doOnTextChanged { text, _, _, _ ->
            if(text?.length != 64) {
                keyView.error = getString(R.string.apiKeyLengthError)
            }
            else {
                canConnect(keyView.text.toString())
            }
        }
    }

    private fun canConnect(key: String) {
        executor.execute {
            Log.d("connect", "trying $key")
            val req = service.getStatus(key)
            val res = req.execute()

            if(res.isSuccessful) {
                val body = res.body()
                Log.d("connect", "$body")
                if(body != null) {
                    if(body.success != null && body.success) {
                        Log.d("connect", "success")
                        startActivity(getHomeIntent(key))
                    }
                }
            }
            else {
                runOnUiThread {
                    keyView.error = getString(R.string.apiKeyNotWorking)
                }
            }
        }
    }

    private fun getHomeIntent(key: String): Intent? {
        val intent = Intent(this, MainActivity::class.java)
        val extras = Bundle()
        PreferenceUtils.key = key
        intent.putExtras(extras)
        return intent
    }
}
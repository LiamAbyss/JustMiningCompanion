package com.pauk.justminingcompanion.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pauk.justminingcompanion.R
import com.pauk.justminingcompanion.api.JustMiningService
import com.pauk.justminingcompanion.ui.fragments.MasternodeListFragment
import com.pauk.justminingcompanion.ui.fragments.StakingListFragment
import com.pauk.justminingcompanion.ui.fragments.WalletListFragment
import com.pauk.justminingcompanion.utils.PreferenceUtils
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var service: JustMiningService
    private lateinit var currentTab: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentTab = findViewById(R.id.currentTabTextView)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.just-mining.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        service = retrofit.create(JustMiningService::class.java)

        showWallets()
    }

    private fun showMasternodes() {
        executor.execute {
            val request = service.listMasternodes(PreferenceUtils.key.toString())
            val res = request.execute()

            if(res.isSuccessful){
                val masternodes = res.body()
                if(masternodes != null) {
                    runOnUiThread {
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentFrameLayout, MasternodeListFragment(masternodes.data.orEmpty()))
                                .commit()
                        currentTab.text = getString(R.string.masternodes)
                    }
                }
            }
        }
    }

    private fun showStakings() {
        executor.execute {
            val request = service.listStakings(PreferenceUtils.key.toString())
            val res = request.execute()

            if(res.isSuccessful){
                val stakings = res.body()
                if(stakings != null) {
                    runOnUiThread {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentFrameLayout, StakingListFragment(stakings.data.orEmpty()))
                            .commit()
                        currentTab.text = getString(R.string.stakings)
                    }
                }
            }
        }
    }

    private fun showWallets() {
        executor.execute {
            val request = service.listWallets(PreferenceUtils.key.toString())
            val res = request.execute()

            if(res.isSuccessful){
                val wallets = res.body()
                if(wallets != null) {
                    runOnUiThread {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentFrameLayout, WalletListFragment(wallets.data.orEmpty()))
                            .commit()
                        currentTab.text = getString(R.string.wallet)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.masternodesButton -> {
                showMasternodes()
                true
            }
            R.id.stakingsButton -> {
                showStakings()
                true
            }
            R.id.walletButton -> {
                showWallets()
                true
            }
            R.id.logOutButton -> {
                PreferenceUtils.key = ""
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
package com.pauk.justminingcompanion.api

import com.pauk.justminingcompanion.models.masternodes.MasternodeResponse
import com.pauk.justminingcompanion.models.StatusResponse
import com.pauk.justminingcompanion.models.stakings.StakingResponse
import com.pauk.justminingcompanion.models.wallets.WalletResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface JustMiningService {
    @GET("masternodes")
    fun listMasternodes(@Header("API-KEY") key: String): Call<MasternodeResponse>

    @GET("stakings")
    fun listStakings(@Header("API-KEY") key: String): Call<StakingResponse>

    @GET("wallets")
    fun listWallets(@Header("API-KEY") key: String): Call<WalletResponse>

    @GET("status")
    fun getStatus(@Header("API-KEY") key: String): Call<StatusResponse>
}
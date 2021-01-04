package com.pauk.justminingcompanion.models.wallets

import java.util.*

/**
 * Class containing informations about a staking
 */
class Wallet {

    /**
     * Currency code of the staking
     */
    public final val currencyCode:String = String()

    /**
     * Amount of the stake
     */
    public final val balance:Double = 0.0


    public final val requestTime = Calendar.getInstance().time.time

}

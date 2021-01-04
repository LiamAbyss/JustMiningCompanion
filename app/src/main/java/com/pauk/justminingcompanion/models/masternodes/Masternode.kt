package com.pauk.justminingcompanion.models.masternodes

import java.util.*

/**
 * Class containing informations about a masternode
 */
class Masternode {
    /**
     * Id of the masternode
     */
    public final val id: Int = 0

    /**
     * Status of the masternode
     * Either active or inactive
     */
    public final val status: String = String()

    /**
     * Amount of collateral of the masternode
     */
    public final val collateral:Int = 0

    /**
     * Currency code of the masternode
     */
    public final val currencyCode:String = String()

    /**
     * Rewards earned by the masternode
     */
    public final val reward:Double = 0.0

    /**
     * Start date of the masternode
     */
    public final val startDate: Long? = null

    /**
     * End date of the masternode
     */
    public final val endDate: Long? = null

    public final val requestTime = Calendar.getInstance().time.time

}

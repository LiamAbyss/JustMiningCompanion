package com.pauk.justminingcompanion.models.stakings

import java.util.*

/**
 * Class containing informations about a staking
 */
class Staking {

    /**
     * Currency code of the staking
     */
    public final val currencyCode:String = String()

    /**
     * Amount of the stake
     */
    public final val amount:Double = 0.0

    /**
     * Rewards earned by the staking
     */
    public final val reward:Double = 0.0

    /**
     * Locked rewards earned by the staking
     */
    public final val lockedReward:Double = 0.0

    /**
     * Start date of the staking
     */
    public final val startDate: Long? = null

    public final val variations: List<StakingVariation>? = null

    public final val credits: List<StakingCredit>? = null

    public final val requestTime = Calendar.getInstance().time.time

}

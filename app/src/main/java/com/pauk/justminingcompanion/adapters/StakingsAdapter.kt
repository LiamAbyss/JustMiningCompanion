package com.pauk.justminingcompanion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pauk.justminingcompanion.R
import com.pauk.justminingcompanion.models.stakings.Staking
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.SimpleDateFormat

class StakingsAdapter(stakings: List<Staking>) : RecyclerView.Adapter<StakingsAdapter.ViewHolder>() {

    private var mStakings: List<Staking> = stakings
    private var mParent: ViewGroup? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var label: TextView = itemView.findViewById(R.id.stakingLabelTextView)
        var name: TextView = itemView.findViewById(R.id.stakingNameTextView)
        var collateral: TextView = itemView.findViewById(R.id.stakingCollateralTextView)
        var image: ImageView = itemView.findViewById(R.id.stakingImageView)
        var launchDate: TextView = itemView.findViewById(R.id.stakingLaunchDateTextView)
        var launchDateLabel: TextView = itemView.findViewById(R.id.stakingLaunchDateLabelTextView)
        var reward: TextView = itemView.findViewById(R.id.stakingCurrentRewardTextView)
        var rewardUnit: TextView = itemView.findViewById(R.id.stakingRewardUnitTextView)
        var roi: TextView = itemView.findViewById(R.id.stakingEstimatedRoiTextView)
        var roiLabel: TextView = itemView.findViewById(R.id.stakingEstimatedRoiLabelTextView)
        var totalMined: TextView = itemView.findViewById(R.id.stakingTotalMinedTextView)
        var totalMinedUnit: TextView = itemView.findViewById(R.id.stakingTotalMinedUnitTextView)
        var totalMinedLabel: TextView = itemView.findViewById(R.id.stakingTotalMinedLabelTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_staking_item, parent, false)
        mParent = parent
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mStakings.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = mStakings[position]

        // Render currency icon
        Picasso.get().load("https://icons.bitbot.tools/api/${s.currencyCode}/128x128")
                .resize(64, 64)
                .centerCrop()
                .into(holder.image)

        // Collateral value and name
        holder.label.text = s.currencyCode
        holder.name.text = s.currencyCode
        holder.collateral.text = s.amount.toString()

        // Launch and end dates
        val format: SimpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
        if(s.startDate != null) {
            holder.launchDate.text = format.format(s.startDate * 1000)
        }
        else {
            holder.launchDate.visibility = View.GONE
            holder.launchDateLabel.visibility = View.GONE
        }

        // Reward value and name, ROI and total mined
        if(s.startDate != null) {
            val delta = (s.requestTime - s.startDate * 1000) / 60 / 60 / 24 / 1000
            val rewardPerDay = (s.reward / delta)
            val rewardPerMinuteBigDecimal = rewardPerDay.toBigDecimal().setScale(8, RoundingMode.UP)

            holder.reward.text = "~$rewardPerMinuteBigDecimal"
            holder.rewardUnit.text = "${s.currencyCode} / jour"

            val roi = rewardPerDay * 365 / s.amount * 100
            val roiBigDecimal = roi.toBigDecimal().setScale(1, RoundingMode.UP)
            holder.roi.text = "~$roiBigDecimal%"

            val rewardBigDecimal = s.reward.toBigDecimal().setScale(8, RoundingMode.UP)
            holder.totalMined.text = rewardBigDecimal.toString()
            holder.totalMinedUnit.text = s.currencyCode
        }
        else {
            holder.reward.visibility = View.GONE
            holder.rewardUnit.visibility = View.GONE
            holder.roi.visibility = View.GONE
            holder.roiLabel.visibility = View.GONE
            holder.totalMined.visibility = View.GONE
            holder.totalMinedLabel.visibility = View.GONE
            holder.totalMinedUnit.visibility = View.GONE
        }
    }
}
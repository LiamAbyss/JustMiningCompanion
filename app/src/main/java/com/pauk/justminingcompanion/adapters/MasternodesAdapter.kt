package com.pauk.justminingcompanion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pauk.justminingcompanion.R
import com.pauk.justminingcompanion.models.masternodes.Masternode
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.SimpleDateFormat

class MasternodesAdapter(masternodes: List<Masternode>) : RecyclerView.Adapter<MasternodesAdapter.ViewHolder>() {

    private var mMasternodes: List<Masternode> = masternodes
    private var mParent: ViewGroup? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.masternodeNameTextView)
        var collateral: TextView = itemView.findViewById(R.id.masternodeCollateralTextView)
        var image: ImageView = itemView.findViewById(R.id.stakingImageView)
        var launchDate: TextView = itemView.findViewById(R.id.masternodeLaunchDateTextView)
        var launchDateLabel: TextView = itemView.findViewById(R.id.stakingLaunchDateLabelTextView)
        var endDate: TextView = itemView.findViewById(R.id.masternodeEndDateTextView)
        var endDateLabel: TextView = itemView.findViewById(R.id.masternodeEndDateLabelTextView)
        var reward: TextView = itemView.findViewById(R.id.masternodeCurrentRewardTextView)
        var rewardUnit: TextView = itemView.findViewById(R.id.masternodeRewardUnitTextView)
        var roi: TextView = itemView.findViewById(R.id.masternodeEstimatedRoiTextView)
        var roiLabel: TextView = itemView.findViewById(R.id.masternodeEstimatedRoiLabelTextView)
        var totalMined: TextView = itemView.findViewById(R.id.masternodeTotalMinedTextView)
        var totalMinedUnit: TextView = itemView.findViewById(R.id.masternodeTotalMinedUnitTextView)
        var totalMinedLabel: TextView = itemView.findViewById(R.id.masternodeTotalMinedLabelTextView)
        var isActive: TextView = itemView.findViewById(R.id.masternodeIsActiveTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_masternode_item, parent, false)
        mParent = parent
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mMasternodes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = mMasternodes[position]

        holder.isActive.text = m.status
        if(m.status == "active"){
            mParent?.context?.getColor(R.color.text_valid)?.let { holder.isActive.setTextColor(it) }
        }
        else {
            mParent?.context?.getColor(R.color.text_error)?.let { holder.isActive.setTextColor(it) }
        }

        // Render currency icon
        Picasso.get().load("https://icons.bitbot.tools/api/${m.currencyCode}/128x128")
                .resize(64, 64)
                .centerCrop()
                .into(holder.image)

        // Collateral value and name
        holder.name.text = m.currencyCode
        holder.collateral.text = m.collateral.toString()

        // Launch and end dates
        val format: SimpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
        if(m.startDate != null) {
            holder.launchDate.text = format.format(m.startDate * 1000)
        }
        else {
            holder.launchDate.visibility = View.GONE
            holder.launchDateLabel.visibility = View.GONE
        }

        if(m.endDate != null) {
            holder.endDate.text = format.format(m.endDate * 1000)
        }
        else {
            holder.endDate.visibility = View.GONE
            holder.endDateLabel.visibility = View.GONE
        }

        // Reward value and name, ROI and total mined
        if(m.startDate != null) {
            val delta = (m.requestTime - m.startDate * 1000) / 60 / 1000
            val rewardPerMinute = (m.reward / delta)
            val rewardPerMinuteBigDecimal = rewardPerMinute.toBigDecimal().setScale(8, RoundingMode.UP)

            holder.reward.text = "~$rewardPerMinuteBigDecimal"
            holder.rewardUnit.text = "${m.currencyCode} / min"

            val roi = rewardPerMinute * 60 * 24 * 365 / m.collateral * 100
            val roiBigDecimal = roi.toBigDecimal().setScale(1, RoundingMode.UP)
            holder.roi.text = "~$roiBigDecimal%"

            val rewardBigDecimal = m.reward.toBigDecimal().setScale(8, RoundingMode.UP)
            holder.totalMined.text = rewardBigDecimal.toString()
            holder.totalMinedUnit.text = m.currencyCode
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
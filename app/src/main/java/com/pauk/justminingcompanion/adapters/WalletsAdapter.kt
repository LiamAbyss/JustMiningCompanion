package com.pauk.justminingcompanion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pauk.justminingcompanion.R
import com.pauk.justminingcompanion.models.wallets.Wallet
import com.squareup.picasso.Picasso
import java.math.RoundingMode

class WalletsAdapter(wallets: List<Wallet>) : RecyclerView.Adapter<WalletsAdapter.ViewHolder>() {

    private var mWallets: List<Wallet> = wallets
    private var mParent: ViewGroup? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.walletNameTextView)
        var collateral: TextView = itemView.findViewById(R.id.walletCollateralTextView)
        var image: ImageView = itemView.findViewById(R.id.walletImageView)
        var label: TextView = itemView.findViewById(R.id.walletLabelTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_wallet_item, parent, false)
        mParent = parent
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mWallets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val w = mWallets[position]

        // Render currency icon
        Picasso.get().load("https://icons.bitbot.tools/api/${w.currencyCode}/128x128")
                .resize(64, 64)
                .centerCrop()
                .into(holder.image)

        // Collateral value and name
        val balance = w.balance.toBigDecimal().setScale(8, RoundingMode.UP)
        holder.name.text = w.currencyCode
        holder.collateral.text = balance.toString()
        holder.label.text = w.currencyCode
    }
}
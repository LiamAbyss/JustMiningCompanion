package com.pauk.justminingcompanion.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pauk.justminingcompanion.R
import com.pauk.justminingcompanion.adapters.MasternodesAdapter
import com.pauk.justminingcompanion.models.masternodes.Masternode

class MasternodeListFragment(masternodes: List<Masternode>) : Fragment() {
    private var mMasternodes = masternodes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_masternode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<View>(R.id.masternodesRecyclerView) as RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = MasternodesAdapter(mMasternodes)
        }
    }
}
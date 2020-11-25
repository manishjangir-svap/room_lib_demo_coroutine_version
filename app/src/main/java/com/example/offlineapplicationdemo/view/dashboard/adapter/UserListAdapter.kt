package com.example.offlineapplicationdemo.view.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.offlineapplicationdemo.R
import com.example.offlineapplicationdemo.databinding.ViewUserDetailsBinding
import com.example.offlineapplicationdemo.model.bean.UserData

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.Holder>() {
    private lateinit var layoutInflater: LayoutInflater
    private var data: ArrayList<UserData> = ArrayList()

    fun setData(data: ArrayList<UserData>) {
        this.data = data
        notifyItemRangeChanged(0, this.data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = run {
        if(!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewUserDetailsBinding>(layoutInflater, R.layout.view_user_details, parent, false)
        Holder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(holder.viewDataBinding is ViewUserDetailsBinding) {
            holder.viewDataBinding.user = data[holder.adapterPosition]
        }
    }

    override fun getItemCount() = data.size

    open class Holder(val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root)
}
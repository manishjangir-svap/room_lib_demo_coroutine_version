package com.example.offlineapplicationdemo.utility

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.example.offlineapplicationdemo.model.bean.UserData
import com.example.offlineapplicationdemo.view.dashboard.adapter.UserListAdapter

object UtilityBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:userList")
    fun RecyclerView.createUserList(data: ArrayList<UserData>?) {
        data?.let {
            if(this.adapter == null) {
                this.layoutManager = LinearLayoutManager(this.context)
                this.adapter = UserListAdapter()
                this.itemAnimator?.let { (it as SimpleItemAnimator).supportsChangeAnimations = false }
                this.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        outRect.top = 8.toPx(parent.context).toInt()
                        outRect.bottom = 8.toPx(parent.context).toInt()
                        outRect.right = 16.toPx(parent.context).toInt()
                        outRect.left = 16.toPx(parent.context).toInt()
                    }
                })
            }

            if(this.adapter is UserListAdapter) (this.adapter as UserListAdapter).setData(data)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun ImageView.imageUrl(url: String?) {
        url?.let { Glide.with(this.context).load(url).into(this) }
    }
}
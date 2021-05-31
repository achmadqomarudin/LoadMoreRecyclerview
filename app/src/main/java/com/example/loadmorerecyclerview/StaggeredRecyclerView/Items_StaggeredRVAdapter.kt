package com.example.loadmorerecyclerview.StaggeredRecyclerView

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.loadmorerecyclerview.Constant
import com.example.loadmorerecyclerview.R
import com.example.loadmorerecyclerview.databinding.ProgressLoadingBinding
import com.example.loadmorerecyclerview.databinding.StaggeredItemRowBinding

class Items_StaggeredRVAdapter(private var itemsCells: ArrayList<StaggeredModel?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<StaggeredModel?>) {
        this.itemsCells.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): StaggeredModel? {
        return itemsCells[position]
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            itemsCells.add(null)
            notifyItemInserted(itemsCells.size - 1)
        }
    }

    fun removeLoadingView() {
        //remove loading item
        if (itemsCells.size != 0) {
            itemsCells.removeAt(itemsCells.size - 1)
            notifyItemRemoved(itemsCells.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.staggered_item_row, parent, false)
            ItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.progress_loading, parent, false)
            val binding = ProgressLoadingBinding.bind(view)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.progressbar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                binding.progressbar.indeterminateDrawable.setColorFilter(
                    Color.WHITE,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsCells[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val binding = StaggeredItemRowBinding.bind(holder.itemView)

            binding.staggeredItemImageview.setAspectRatio(itemsCells[position]!!.aspectRatio)
            binding.staggeredItemImageview.setBackgroundColor(itemsCells[position]!!.color)
        } else {
            val layoutParams =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
        }
    }
}
package com.example.loadmorerecyclerview.GridRecyclerView

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
import com.example.loadmorerecyclerview.Constant
import com.example.loadmorerecyclerview.R
import com.example.loadmorerecyclerview.databinding.GridItemRowBinding
import com.example.loadmorerecyclerview.databinding.ProgressLoadingBinding

class Items_GridRVAdapter(private var itemsCells: ArrayList<Int?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<Int?>) {
        this.itemsCells.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): Int? {
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
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.grid_item_row, parent, false)
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
            val binding = GridItemRowBinding.bind(holder.itemView)

            binding.gridItemImageview.setBackgroundColor(itemsCells[position]!!)
        }
    }
}
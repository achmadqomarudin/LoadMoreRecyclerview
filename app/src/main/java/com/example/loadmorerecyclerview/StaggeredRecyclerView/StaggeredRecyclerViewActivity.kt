package com.example.loadmorerecyclerview.StaggeredRecyclerView

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.loadmorerecyclerview.OnLoadMoreListener
import com.example.loadmorerecyclerview.R
import com.example.loadmorerecyclerview.RecyclerViewLoadMoreScroll
import com.example.loadmorerecyclerview.databinding.ActivityStaggeredRecyclerViewBinding
import java.util.*
import kotlin.collections.ArrayList

class StaggeredRecyclerViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityStaggeredRecyclerViewBinding
    lateinit var itemsCells: ArrayList<StaggeredModel?>
    lateinit var loadMoreItemsCells: ArrayList<StaggeredModel?>
    lateinit var adapterStaggered: Items_StaggeredRVAdapter
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaggeredRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //** Set the data for our ArrayList
        setItemsData()

        //** Set the adapterLinear of the RecyclerView
        setAdapter()

        //** Set the Layout Manager of the RecyclerView
        setRVLayoutManager()

        //** Set the scrollListener of the RecyclerView
        setRVScrollListener()
    }

    private fun setItemsData() {
        itemsCells = ArrayList()
        for (i in 0..41) {
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            val aspectRatio = rnd.nextFloat() * (1.5f - 0.5f) + 0.5f
            itemsCells.add(StaggeredModel(color, aspectRatio))
        }
    }

    private fun setAdapter() {
        adapterStaggered = Items_StaggeredRVAdapter(itemsCells)
        adapterStaggered.notifyDataSetChanged()
        binding.itemsStaggeredRv.adapter = adapterStaggered
    }

    private fun setRVLayoutManager() {
        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.itemsStaggeredRv.layoutManager = mLayoutManager
        binding.itemsStaggeredRv.setHasFixedSize(true)
        binding.itemsStaggeredRv.adapter = adapterStaggered
    }

    private fun setRVScrollListener() {
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as StaggeredGridLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })

        binding.itemsStaggeredRv.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        //Add the Loading View
        adapterStaggered.addLoadingView()
        //Create the loadMoreItemsCells Arraylist
        loadMoreItemsCells = ArrayList()
        //Get the number of the current Items of the main Arraylist
        val start = adapterStaggered.itemCount
        //Load 16 more items
        val end = start + 16
        //Use Handler if the items are loading too fast.
        //If you remove it, the data will load so fast that you can't even see the LoadingView
        Handler().postDelayed({
            for (i in start..end) {
                val rnd = Random()
                val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                val aspectRatio = rnd.nextFloat() * (1.5f - 0.5f) + 0.5f
                //Get data and add them to loadMoreItemsCells ArrayList
                loadMoreItemsCells.add(StaggeredModel(color, aspectRatio))
            }
            //Remove the Loading View
            adapterStaggered.removeLoadingView()
            //We adding the data to our main ArrayList
            adapterStaggered.addData(loadMoreItemsCells)
            //Change the boolean isLoading to false
            scrollListener.setLoaded()
            //Update the recyclerView in the main thread
            binding.itemsStaggeredRv.post {
                adapterStaggered.notifyDataSetChanged()
            }
        }, 3000)
    }
}

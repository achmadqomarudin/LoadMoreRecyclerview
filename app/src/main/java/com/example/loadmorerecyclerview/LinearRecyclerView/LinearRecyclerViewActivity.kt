package com.example.loadmorerecyclerview.LinearRecyclerView

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmorerecyclerview.OnLoadMoreListener
import com.example.loadmorerecyclerview.RecyclerViewLoadMoreScroll
import com.example.loadmorerecyclerview.databinding.ActivityLinearRecyclerViewBinding

class LinearRecyclerViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityLinearRecyclerViewBinding
    lateinit var itemsCells: ArrayList<String?>
    lateinit var loadMoreItemsCells: ArrayList<String?>
    lateinit var adapterLinear: Items_LinearRVAdapter
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinearRecyclerViewBinding.inflate(layoutInflater)
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
        for (i in 0..40) {
            itemsCells.add("Item $i")
        }
    }

    private fun setAdapter() {
        adapterLinear = Items_LinearRVAdapter(itemsCells)
        adapterLinear.notifyDataSetChanged()
        binding.itemsLinearRv.adapter = adapterLinear
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(this)
        binding.itemsLinearRv.layoutManager = mLayoutManager
        binding.itemsLinearRv.setHasFixedSize(true)
    }

    private  fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(this)
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        binding.itemsLinearRv.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        //Add the Loading View
        adapterLinear.addLoadingView()
        //Create the loadMoreItemsCells Arraylist
        loadMoreItemsCells = ArrayList()
        //Get the number of the current Items of the main Arraylist
        val start = adapterLinear.itemCount
        //Load 16 more items
        val end = start + 16
        //Use Handler if the items are loading too fast.
        //If you remove it, the data will load so fast that you can't even see the LoadingView
        Handler().postDelayed({
            for (i in start..end) {
                //Get data and add them to loadMoreItemsCells ArrayList
                loadMoreItemsCells.add("Item $i")
            }
            //Remove the Loading View
            adapterLinear.removeLoadingView()
            //We adding the data to our main ArrayList
            adapterLinear.addData(loadMoreItemsCells)
            //Change the boolean isLoading to false
            scrollListener.setLoaded()
            //Update the recyclerView in the main thread
            binding.itemsLinearRv.post {
                adapterLinear.notifyDataSetChanged()
            }
        }, 3000)
    }
}

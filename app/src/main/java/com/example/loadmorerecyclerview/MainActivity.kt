package com.example.loadmorerecyclerview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.loadmorerecyclerview.GridRecyclerView.GridRecyclerViewActivity
import com.example.loadmorerecyclerview.LinearRecyclerView.LinearRecyclerViewActivity
import com.example.loadmorerecyclerview.StaggeredRecyclerView.StaggeredRecyclerViewActivity
import com.example.loadmorerecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linearRvBtn.setOnClickListener {
            val intent = Intent(this,
                LinearRecyclerViewActivity::class.java)
            startActivity(intent)
        }

        binding.gridRvBtn.setOnClickListener {
            val intent = Intent(this,
                GridRecyclerViewActivity::class.java)
            startActivity(intent)
        }

        binding.staggeredRvBtn.setOnClickListener {
            val intent = Intent(this,
                StaggeredRecyclerViewActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_github) {
            val uri: Uri = Uri.parse("https://github.com/achmadqomarudin")
            startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW, uri), "Choose Browser"))
        }
        return true
    }
}
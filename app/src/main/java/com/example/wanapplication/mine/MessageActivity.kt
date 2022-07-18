package com.example.wanapplication.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wanapplication.FragmentViewPagerAdapter
import com.example.wanapplication.R
import com.example.wanapplication.databinding.ActivityMessageBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private val titles = arrayOf("新消息", "历史消息")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        var fragments = ArrayList<Fragment>();
        binding.messageViewpager.apply {
            adapter = FragmentViewPagerAdapter(supportFragmentManager,lifecycle,fragments)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
            })
        }
        TabLayoutMediator(binding.tablayout, binding.messageViewpager, object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = titles[position]
            }
        }).attach()
    }
}
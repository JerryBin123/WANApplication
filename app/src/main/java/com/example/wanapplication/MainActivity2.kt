package com.example.wanapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.example.wanapplication.databinding.ActivityMain2Binding
import com.example.wanapplication.utils.LottieAnimation
import com.example.wanapplication.utils.LottieUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

    private var mPreClickPosition: Int = 0

    private lateinit var binding: ActivityMain2Binding
    val mNavigationAnimationList = arrayListOf(
        LottieAnimation.HOME,
        LottieAnimation.TEST2,
        LottieAnimation.TEST3,
        LottieAnimation.TEST4,
    )
    private val mNavigationTitleList = arrayListOf(
        "首页",
        "问答",
        "体系",
        "我的"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initBottomNavigationView()

    }


    private fun initBottomNavigationView() {
        binding.bottomNavigation.menu.apply {
            for (i in 0 until mNavigationTitleList.size) {
                add(Menu.NONE, i, Menu.NONE, mNavigationTitleList[i])
            }
            setLottieDrawable(getLottieAnimationList(this@MainActivity2))
        }
        initEvent()
    }

    private fun initEvent() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.bottomNavigation.setOnNavigationItemReselectedListener(this)
        // 默认选中第一个
        binding.bottomNavigation.selectedItemId = 0
    }

    private fun Menu.setLottieDrawable(lottieAnimationList: ArrayList<LottieAnimation>) {
        for (i in 0 until mNavigationTitleList.size) {
            Log.d("测试", "setLottieDrawable: ${getLottieDrawable(lottieAnimationList[i], binding.bottomNavigation)}")
            findItem(i)?.icon =
                getLottieDrawable(lottieAnimationList[i], binding.bottomNavigation)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        handleNavigationItem(item)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        handleNavigationItem(item)
    }

    private fun handleNavigationItem(item: MenuItem) {
        handlePlayLottieAnimation(item)
        mPreClickPosition = item.itemId
    }

    private fun handlePlayLottieAnimation(item: MenuItem) {
        val currentIcon = item.icon as? LottieDrawable
        currentIcon?.apply {
            playAnimation()
        }
        // 处理 tab 切换，icon 对应调整
        if (item.itemId != mPreClickPosition) {
            binding.bottomNavigation.menu.findItem(mPreClickPosition).icon =
                getLottieDrawable(
                    getLottieAnimationList(this)[mPreClickPosition],
                    binding.bottomNavigation
                )
        }
    }

    fun getLottieDrawable(
        animation: LottieAnimation,
        bottomNavigationView: BottomNavigationView
    ): LottieDrawable {
        return LottieDrawable().apply {
            val result = LottieCompositionFactory.fromRawRes(
                bottomNavigationView.context.applicationContext, animation.value
            )
            callback = bottomNavigationView
            result.addListener{
                composition = it
            }
        }
    }

    fun getLottieAnimationList(context: Context): ArrayList<LottieAnimation> {
        return mNavigationAnimationList
    }
}
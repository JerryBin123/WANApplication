package com.example.wanapplication.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.wanapplication.bean.home.BannerData
import com.example.wanapplication.bean.home.Data
import com.example.wanapplication.bean.home.Datas
import com.example.wanapplication.databinding.FragmentHomeBinding
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class NewHomeFragment : Fragment(),HomeContract.IHomeView {

    private lateinit var homeTopRecyclerAdapter: HomeTopRecyclerAdapter
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private lateinit var homePresenter: HomePresenter
    private lateinit var viewBinding: FragmentHomeBinding
    private var currPage : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        initListener()
        return viewBinding.root
    }

    private fun initListener() {
        viewBinding.ivSearch.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
    }

    private fun initViews() {
        homePresenter = HomePresenter(requireActivity(), this)
        homePresenter.getBanners()
        homeRecyclerAdapter = HomeRecyclerAdapter(requireActivity())
        homeTopRecyclerAdapter = HomeTopRecyclerAdapter(requireActivity())
        viewBinding.homeRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = homeRecyclerAdapter
        }
        viewBinding.topRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = homeTopRecyclerAdapter
        }
        homePresenter.getArticles(0)
        homePresenter.getTopArticles()

        viewBinding.smartRefresh.apply {
            setRefreshFooter(ClassicsFooter(requireActivity()))
            setRefreshHeader(ClassicsHeader(requireActivity()))
            setOnRefreshListener {
                homePresenter.getArticles(0)
                finishRefresh()
            }
            setOnLoadMoreListener{
                homePresenter.getLoadMoreArticles(++currPage)
                finishLoadMore()
            }
        }
    }

    override fun getBannerSuccess(data: List<BannerData>) {
        viewBinding.banner.setAdapter(object :BannerImageAdapter<BannerData>(data){
            override fun onBindView(
                holder: BannerImageHolder?,
                data: BannerData?,
                position: Int,
                size: Int
            ) {
                //图片加载自己实现
                Glide.with(holder!!.itemView)
                    .load(data!!.imagePath)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(holder!!.imageView)
            }
        }).addBannerLifecycleObserver(requireActivity())
            .setIndicator(CircleIndicator(requireActivity()))

    }

    override fun getArticlesSuccess(data: Data) {
        homeRecyclerAdapter.setData(data.datas)
    }

    override fun getLoadMoreArticlesSuccess(data: Data) {
        homeRecyclerAdapter.addData(data.datas)
    }

    override fun getTopArticleSuccess(data: List<Datas>) {
        homeTopRecyclerAdapter.setData(data)
    }
}
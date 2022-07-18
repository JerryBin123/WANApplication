package com.example.wanapplication.mine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanapplication.bean.mine.NewRecordData
import com.example.wanapplication.bean.mine.ReadRecordData
import com.example.wanapplication.databinding.ActivityNewReadRecordBinding
import kotlinx.coroutines.launch

class NewRecordActivity: AppCompatActivity() {


    private lateinit var readRecordDao: ReadRecordDao
    private lateinit var binding: ActivityNewReadRecordBinding
    private var datas: List<NewRecordData> = ArrayList()
    private lateinit var NewAdapter : NewReadRecordRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewReadRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initListener()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.readRecordDeleteAll.setOnClickListener {
            readRecordDao.deleteAll()
            NewAdapter.setData(ArrayList())
        }

    }

    private fun initViews() {
        NewAdapter = NewReadRecordRecyclerAdapter(datas,this)
        binding.readRecordRecycler.apply {
            layoutManager = LinearLayoutManager(this@NewRecordActivity)
            adapter = NewAdapter
        }
        readRecordDao = ReadRecordDatabase.getInstance(this)
            .getReadRecordDao()
        lifecycleScope.launch {
            datas = readRecordDao
                .getAll()
        }

        NewAdapter.setData(datas)
    }
}
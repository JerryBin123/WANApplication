package com.example.wanapplication.qa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanapplication.R
import com.example.wanapplication.bean.home.Datas
import com.example.wanapplication.databinding.FragmentNewQABinding


class NewQAFragment : Fragment(), NewQAContact.IQAView{

    private var _binding : FragmentNewQABinding ?= null
    private val binding
    get() =_binding!!
    private lateinit var newQAPresenter : NewQAPresenter
    private lateinit var qaRecyclerAdapter : NewQARecyclerAdapter
    private var data = ArrayList<Datas>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewQABinding.inflate(layoutInflater, container, false)
        val root = binding.root
        initViews()
        return root
    }

    private fun initViews() {
        qaRecyclerAdapter = NewQARecyclerAdapter( requireContext(),data)
        binding.qaRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.qaRecycler.adapter = qaRecyclerAdapter
        newQAPresenter = NewQAPresenter(requireContext(),this)
        newQAPresenter.getQA(0)
    }

    override fun getSuccess(datas: List<Datas>) {
       qaRecyclerAdapter.addData(datas)
    }


}
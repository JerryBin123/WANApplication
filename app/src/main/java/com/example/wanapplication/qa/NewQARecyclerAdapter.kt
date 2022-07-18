package com.example.wanapplication.qa

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.wanapplication.R
import com.example.wanapplication.bean.home.Datas
import com.example.wanapplication.databinding.QaItemBinding
import com.example.wanapplication.utils.HtmlUtils

class NewQARecyclerAdapter(
    val context: Context,
    var datas: ArrayList<Datas>) : RecyclerView.Adapter<NewQARecyclerAdapter.myAdapter>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myAdapter {
        val view = QaItemBinding.inflate(layoutInflater, parent, false)
        return myAdapter(view)
    }

    override fun onBindViewHolder(holder: myAdapter, position: Int) {
        if (datas[position].author.equals("")){
            holder.binding.qaAuthor.text = datas[position].shareUser
        }else{
            holder.binding.qaAuthor.text = datas[position].author
        }
        holder.binding.qaDate.text = datas[position].niceDate
        holder.binding.qaTitle.text = datas[position].title
        holder.binding.qaChapter.text = "${datas[position].chapterName}·${datas[position].superChapterName}"
        val desc = Html.fromHtml(datas[position].desc).toString()
        HtmlUtils.removeAllBank(desc)
        holder.binding.qaDesc.text = desc

        if (datas[position].collect){
            holder.binding.qaCollect.setImageResource(R.drawable.ic_collect_after)
        }


    }

    override fun getItemCount() = datas.size

    fun addData(newData: List<Datas>){
        datas.addAll(newData)
        notifyDataSetChanged()
    }

    class myAdapter(itemView: QaItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }
}
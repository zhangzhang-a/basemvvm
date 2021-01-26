package com.example.mykotlin.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * adapter基类
 * layout  布局id与界面绑定name的id匹配使用
 * adapter  通过DataBindingUtils实现界面和数据的绑定
 */
open abstract class BaseAdapter<D>(
    val context: Context,
    val list: List<D>,
    val layouts: SparseArray<Int>,
    var itemClick: IItemClick<D>
) : RecyclerView.Adapter<BaseAdapter<D>.BaseVH>() {

    /**
     * 用来初始化创建viewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {

        var dataBinding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        return BaseVH(dataBinding)

    }

    /**
     * item的赋值  {绑定,动态赋值}
     */
    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        //获取当前条目布局的id
        var layoutId = getItemViewType(position)
        //获取layout id 所对应的BR的id
        var type = layouts.get(layoutId)
        //界面组件显示数据的绑定
        holder.dataBinding.setVariable(type, list.get(position))

        holder.dataBinding.root.tag = list.get(position)

        //item的动态点击事件
        holder.dataBinding.root.setOnClickListener {
            if (itemClick != null) {
                itemClick.itemClick(list.get(position))
            }

        }
        bindData(holder.dataBinding, list.get(position),layoutId)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId(position)
    }


    /**
     * 获取对应的饿布局
     */

    protected abstract fun layoutId(position: Int): Int

    protected abstract fun bindData(binding: ViewDataBinding, data: D,layId:Int)

    inner class BaseVH(val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
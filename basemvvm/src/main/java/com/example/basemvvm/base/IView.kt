package com.example.mykotlin.base

interface IView {
    fun showLoading()
    /**
     * 显示提示信息
     */
    fun showTips(tips:String)
}
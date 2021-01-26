package com.example.mykotlin.net.repository


import com.example.mykotlin.net.RetrofitFactory


/**
 * 数据仓库
 * 用来连接ViewModel和Model
 * 定义业务相关的网络请求接口的api
 */

class SystemRepository {

    private lateinit var serviceApi: ServiceApi

    //初始化的方法
    init {
        serviceApi = RetrofitFactory.instant.create(ServiceApi::class.java)
    }


}
package com.mrwho.kotlindemo.beans

/**
 * Created by mr.gao on 2018/5/23.
 * Package:    com.mrwho.kotlindemo.beans
 * Create Date:2018/5/23
 * Project Name:KotlinDemo
 * Description:
 */



//Android和IOS，前端的数据格式
data class DataBean(
        val error: Boolean,
        val results: List<NormalResult>
)

data class NormalResult(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val images: List<String>,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
)


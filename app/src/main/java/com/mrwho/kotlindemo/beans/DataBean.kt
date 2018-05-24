package com.mrwho.kotlindemo.beans

/**
 * Created by mr.gao on 2018/5/23.
 * Package:    com.mrwho.kotlindemo.beans
 * Create Date:2018/5/23
 * Project Name:KotlinDemo
 * Description:
 */

//福利的数据格式
data class RewardBean(
        val error: Boolean,
        val results: List<RewardResult>
)

data class RewardResult(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
)


//Android和IOS的数据格式
data class NormalDataBean(
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


//休息视频的数据格式
data class VideoDataBean(
        val error: Boolean,
        val results: List<VideoResult>
)

data class VideoResult(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
)


//拓展资源的数据格式
data class ExpandDataBean(
        val error: Boolean,
        val results: List<ExpandResult>
)

data class ExpandResult(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: Any
)


//前端的数据格式


data class WebDataBean(
        val error: Boolean,
        val results: List<WebResult>
)

data class WebResult(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String,
        val images: List<String>
)
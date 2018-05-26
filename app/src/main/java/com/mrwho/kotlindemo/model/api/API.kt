package com.mrwho.kotlindemo.model.api

import com.mrwho.kotlindemo.Constants
import com.mrwho.kotlindemo.beans.DataBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.model.api
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
interface API {
    @GET("data/{type}/{pageSize}/{page}")
    fun api(@Path("type") type: String, @Path("page") page: Int, @Path("pageSize") pageSize: Int = Constants.pageSize): Observable<DataBean>

    @GET("search/query/{search}/category/{type}/count/{pageSize}/page/{page}")
    fun search(@Path("search") search:String,@Path("type") type: String, @Path("page") page: Int, @Path("pageSize")pageSize: Int = Constants.pageSize ):Observable<DataBean>
}
package com.chen.kotlintext.utils

import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mr.gao on 17/10/23.
 */
object DateUtils {
    val ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z"
    val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    val YYYY_MM_DDT_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss"

    val YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"

    val YYYY_MM_DD = "yyyy-MM-dd"
    val YYYY = "yyyy"
    val MM = "MM"
    val DD = "dd"

    fun generateTime(time: Long): String {
        val totalSeconds = (time / 1000).toInt()
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) String.format("%02d:%02d:%02d", hours, minutes, seconds) else String.format("%02d:%02d", minutes, seconds)
    }

    /**
     * 根据long毫秒数，获得时分秒
     */
    fun getDateFormatByLong(time: Long): String {
        val totalSeconds = (time / 1000).toInt()
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    /**
     * Author: MrGao
     * CreateTime: 17/12/12 21:59
     * MethodName:
     * Des：将时间戳转换成字符串
     * Params：
     * Return:
     */
    fun formatLongToTime(time: Long): String {
        val date = Date(time)
        val sdf = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
        return sdf.format(date)
    }

    fun formatDate(format: String): String {
        val date = Date()
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }

    /**
     * @param
     * *
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— 格式化日期对象
     */
    fun date2date(date: Date): Date? {
        var date = date
        val sdf = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
        val str = sdf.format(date)
        try {
            date = sdf.parse(str)
        } catch (e: Exception) {
            return null
        }

        return date
    }


    /**
     * @param
     * *
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— 时间对象转换成字符串
     */
    fun date2string(date: Date): String {
        var strDate = ""
        val sdf = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
        strDate = sdf.format(date)
        return strDate
    }

    /**
     * 通过时间获得文件名

     * @param date
     * *
     * @return
     */
    fun getFileNameByDate(date: Date): String {
        var strDate = ""
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        strDate = sdf.format(date)
        return strDate
    }

    /**
     * @param
     * *
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— sql时间对象转换成字符串
     */
    fun timestamp2string(timestamp: Timestamp): String {
        var strDate = ""
        val sdf = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
        strDate = sdf.format(timestamp)
        return strDate
    }

    /**
     * @param dateString
     * *
     * @return
     * *
     * @创建日期 2012-7-13
     * *
     * @创建时间
     * *
     * @描述 —— 字符串转换成时间对象
     */
    fun string2date(dateString: String,formatS: String= YYYY_MM_DD_HH_MM_SS): Date {
        var formateDate: Date? = null
        val format = SimpleDateFormat(formatS)
        try {
            formateDate = format.parse(dateString)
            return formateDate
        } catch (e: ParseException) {
            return Date()
        }


    }


    /**
     * @param date
     * *
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— Date类型转换为Timestamp类型
     */
    fun date2timestamp(date: Date?): Timestamp? {
        if (date == null)
            return null
        return Timestamp(date.time)
    }

    /**
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— 获得当前年份
     */
    val nowYear: String
        get() {
            val sdf = SimpleDateFormat(YYYY)
            return sdf.format(Date())
        }

    /**
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— 获得当前月份
     */
    val nowMonth: String
        get() {
            val sdf = SimpleDateFormat(MM)
            return sdf.format(Date())
        }

    /**
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— 获得当前日期中的日
     */
    val nowDay: String
        get() {
            val sdf = SimpleDateFormat(DD)
            return sdf.format(Date())
        }

    /**
     * @param time
     * *
     * @return
     * *
     * @作者
     * *
     * @创建日期
     * *
     * @创建时间
     * *
     * @描述 —— 指定时间距离当前时间的中文信息
     */
    fun getFriendlyTime(time: Long): String {
        val cal = Calendar.getInstance()
        val timel = cal.timeInMillis - time
        if (timel / 1000 < 60) {
            return "1分钟以内"
        } else if (timel / 1000 / 60 < 60) {
            return (timel / 1000 / 60).toString() + "分钟前"
        } else if (timel / 1000 / 60 / 60 < 24) {
            return (timel / 1000 / 60 / 60).toString() + "小时前"
        } else {
            return (timel / 1000 / 60 / 60 / 24).toString() + "天前"
        }
    }

    /**
     * 以友好的方式显示时间

     * @param time
     * *
     * @return
     */
    fun getFriendlyTime(time: Date): String {
        //获取time距离当前的秒数
        val ct = ((System.currentTimeMillis() - time.time) / 1000).toInt()
        if (ct == 0) {
            return "刚刚"
        }
        if (ct in 1..59) {
            return ct.toString() + "秒前"
        }
        if (ct in 60..3599) {
            return Math.max(ct / 60, 1).toString() + "分钟前"
        }
        if (ct in 3600..86399)
            return (ct / 3600).toString() + "小时前"
        if (ct in 86400..2591999) { //86400 * 30
            val day = ct / 86400
            return day.toString() + "天前"
        }
        if (ct in 2592000..31103999) { //86400 * 30
            return (ct / 2592000).toString() + "月前"
        }
        return (ct / 31104000).toString() + "年前"
    }

    /**
     * 格式化日期字符串

     * @param currentTime
     * *
     * @return
     */
    fun formatString(currentTime: String): String {
        val format = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
        return format.format(currentTime)
    }

}

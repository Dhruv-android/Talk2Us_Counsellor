package com.talk2us_Counsellor.utils

import android.util.Log
import android.widget.Toast
import com.talk2us_Counsellor.MainApplication
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.models.Counsellor
import java.sql.Timestamp
import java.util.*

class Utils {
    companion object {
        fun toast(str: String) {
            Toast.makeText(MainApplication.instance.applicationContext, str, Toast.LENGTH_SHORT)
                .show()
        }
        fun log(str:String){
            Log.d("hello",str)
        }

        fun getCurrentTime(): Timestamp {
            return Timestamp(232)
        }

        fun sortList(list: List<Counsellor?>): List<Counsellor?> {
            val comparator = compareBy<Counsellor?> { it?.clients }
            return list.sortedWith(comparator)
        }

        fun getTime(): String {
            val date = Calendar.getInstance().time
            return date.toString()
        }

        fun getCounsellorId():String{
            return PrefManager.getString(Constants.COUNSELLOR_ID,Constants.NOT_DEFINED) as String
        }
    }
}

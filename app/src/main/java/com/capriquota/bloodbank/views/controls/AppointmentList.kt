package com.capriquota.bloodbank.views.controls;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capriquota.bloodbank.R
import android.util.Log
import android.widget.ListView
import com.capriquota.bloodbank.model.AppointmentModel
import com.capriquota.bloodbank.model.CustomAdapter
import org.json.JSONException
import org.json.JSONObject
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import java.util.ArrayList

class AppointmentList: AppCompatActivity() {
        internal var URL = "http://apps.capriquota.com/biobank/all_booking.php"
        private val jsoncode = 1
        private var listView: ListView? = null
        private var playersModelArrayList: ArrayList<AppointmentModel>? = null
        private var customeAdapter: CustomAdapter? = null

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_apointment_list)
                listView = findViewById(R.id.lv) as ListView
                sampleKo()
        }

        private fun sampleKo() {
                try {

                        Fuel.post(URL, listOf()).responseJson { request, response, result ->
                                Log.d("plzzzzz", result.get().content)
                                onTaskCompleted(result.get().content)
                        }
                } catch (e: Exception) {

                } finally {

                }
        }


        fun onTaskCompleted(response: String) {
                Log.d("responsejson", response)

                playersModelArrayList = getInfo(response)
                customeAdapter = CustomAdapter(this, playersModelArrayList!!)
                listView!!.adapter = customeAdapter
        }

        fun getInfo(response: String): ArrayList<AppointmentModel> {
                val playersModelArrayList = ArrayList<AppointmentModel>()
                try {
                        val jsonObject = JSONObject(response)
                        if (jsonObject.getString("id") >= "1") {

                             //   val dataArray = jsonObject.getJSONArray("data")

                                for (i in 0 until jsonObject.length()) {
                                        val playersModel = AppointmentModel()
                                       // val dataobj = dataArray.getJSONObject(i)
                                        playersModel.setNames(jsonObject.getString("bio_organ"))
                                        playersModel.setCountrys(jsonObject.getString("bio_note"))
                                        playersModel.setCitys(jsonObject.getString("date"))
                                        playersModelArrayList.add(playersModel)
                                }
                        }

                } catch (e: JSONException) {
                        e.printStackTrace()
                }

                return playersModelArrayList
        }

}
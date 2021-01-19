package com.capriquota.bloodbank.views.controls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.capriquota.bloodbank.R
import org.json.JSONException
import org.json.JSONObject

class AppointmentList: AppCompatActivity() {
        private val PHP_MYSQL_SITE_URL = "http://192.168.12.2/php/quotika"

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_apointment_list)

                val myListView = findViewById<ListView>(R.id.listView)
                val value = intent.getStringExtra("username")
                val URL ="https://anonymitychat.com/api/biobank/all_booking.php?user=$value"

                JSONDownloader(this@AppointmentList, URL, myListView).execute()

              /*  val downloadBtn = findViewById<Button>(R.id.btnLoad)
                downloadBtn.setOnClickListener({ JSONDownloader(this@AppointmentList, URL, myListView).execute() })*/
        }
}
/*
        private var listView: ListView? = null
        private var artistList: MutableList<BioList>? = null

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_apointment_list)
                val value = intent.getStringExtra("username")
                var URL = "https://anonymitychat.com/api/biobank/all_booking.php?user=$value"}*/
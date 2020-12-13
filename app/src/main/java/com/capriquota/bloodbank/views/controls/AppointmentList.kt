package com.capriquota.bloodbank.views.controls;

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capriquota.bloodbank.R
import android.widget.ListView
import com.capriquota.bloodbank.model.CustomAdapter
import org.json.JSONObject
import android.view.View
import android.widget.ProgressBar
import com.capriquota.bloodbank.model.Model
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import kotlin.collections.ArrayList

class AppointmentList: AppCompatActivity() {

        internal var URL = "http://capriquota.com/api/biobank/all_booking.php"
        private var mProgressDialog: ProgressDialog? = null
        lateinit var listView_details: ListView
        var arrayList_details:ArrayList<Model> = ArrayList();
        //OkHttpClient creates connection pool between client and server
        val client = OkHttpClient()
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_apointment_list)
              // progress = findViewById(R.id.progressBar)
               // progress.visibility = View.VISIBLE
                listView_details = findViewById(R.id.listView)
                run("http://capriquota.com/api/biobank/all_booking.php")
        }

        fun run(url: String) {
               // showSimpleProgressDialog(this, null, "Loading...", false)
                //progress.visibility = View.VISIBLE
                val request = Request.Builder()
                        .url(url)
                        .build()

                client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                               // progress.visibility = View.GONE
                            //    removeSimpleProgressDialog()
                        }

                        override fun onResponse(call: Call, response: Response) {
                                var str_response = response.body()!!.string()
                                //creating json object
                                val json_contact:JSONObject = JSONObject(str_response)
                                //creating json array
                                var jsonarray_info:JSONArray= json_contact.getJSONArray("info")
                                var i:Int = 0
                                var size:Int = jsonarray_info.length()
                                arrayList_details= ArrayList();
                                for (i in 0.. size-1) {
                                          var json_objectdetail:JSONObject=jsonarray_info.getJSONObject(i)
                                        var model:Model= Model();
                                        model.id=json_objectdetail.getString("bio_organ")
                                        model.name=json_objectdetail.getString("bio_name")
                                        model.email=json_objectdetail.getString("date")
                                        arrayList_details.add(model)
                                }

                                runOnUiThread {
                                        //stuff that updates ui
                                        val obj_adapter : CustomAdapter
                                        obj_adapter = CustomAdapter(applicationContext,arrayList_details)
                                        listView_details.adapter=obj_adapter
                                }
                                        //    removeSimpleProgressDialog()
                               // progress.visibility = View.GONE
                        }
                })
        }

        private fun showSimpleProgressDialog(context: Context, title: String?, msg: String, isCancelable: Boolean) {
                try {
                        if (mProgressDialog == null) {
                                mProgressDialog = ProgressDialog.show(context, title, msg)
                                mProgressDialog!!.setCancelable(isCancelable)
                        }
                        if (!mProgressDialog!!.isShowing) {
                                mProgressDialog!!.show()
                        }
                } catch (ie: IllegalArgumentException) {
                        ie.printStackTrace()
                } catch (re: RuntimeException) {
                        re.printStackTrace()
                } catch (e: Exception) {
                        e.printStackTrace()
                }
        }

        private fun removeSimpleProgressDialog() {
                try {
                        if (mProgressDialog != null) {
                                if (mProgressDialog!!.isShowing) {
                                        mProgressDialog!!.dismiss()
                                        mProgressDialog = null
                                }
                        }
                } catch (ie: IllegalArgumentException) {
                        ie.printStackTrace()

                } catch (re: RuntimeException) {
                        re.printStackTrace()
                } catch (e: Exception) {
                        e.printStackTrace()
                }
        }
}
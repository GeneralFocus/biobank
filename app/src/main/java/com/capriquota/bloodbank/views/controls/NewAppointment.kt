package com.capriquota.bloodbank.views.controls;

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.capriquota.bloodbank.R
import com.capriquota.bloodbank.databinding.ActivityNewAppointmentBinding
import com.capriquota.bloodbank.model.AppConfig
import com.capriquota.bloodbank.viewmodel.newAppointmentData.NewAppointmentData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class NewAppointment: AppCompatActivity() {

    private lateinit var binding: ActivityNewAppointmentBinding
    private val newAppointment : NewAppointmentData = NewAppointmentData()
    private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_appointment)
        val value = intent.getStringExtra("username")
        // Toast.makeText(this,"variable value $value ",Toast.LENGTH_LONG).show()

        binding.donateNow.setOnClickListener {
            newAppointment.donateOrgan = binding.donateOrgan.text.toString().trim()
            newAppointment.donateDate = binding.donateDate.text.toString().trim()
            newAppointment.donateText = binding.donateText.text.toString().trim()
            if ( newAppointment.donateOrgan.isEmpty()){
                binding.donateOrgan.error = "Please specify the organ / specimen"
            }
            if(newAppointment.donateDate.isEmpty()){
                binding.donateDate.error ="Please specify a date"
            }
            if(newAppointment.donateText.isEmpty()){
                binding.donateText.error="Please write a short note..."
            }
            donate(value)
        }

        binding.donateDate.setText(SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()))

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.donateDate.setText(sdf.format(cal.time))

        }

         binding.donateDate.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    @Throws(IOException::class, JSONException::class)
    private fun donate(username: String?){

        showSimpleProgressDialog(this, null, "Loading...", false)
        try {
           // val username = intent.getStringExtra("username")
            Fuel.post(AppConfig.DONATE_URL, listOf("user" to username ,"donate_organ" to newAppointment.donateOrgan
                    , "donate_date" to newAppointment.donateDate
                    , "donate_text" to newAppointment.donateText
            )).responseJson { _, _, result ->
                Log.d("plzzzzz", result.get().content)

                val response  : String = result.get().content
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
                    removeSimpleProgressDialog()
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, AppointmentList::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)

                   /*val intent = Intent(applicationContext, AppointmentList::class.java)
                   startActivity(intent)*/
                  //  finish()
                }else{
                    removeSimpleProgressDialog()
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                }


                /* removeSimpleProgressDialog()
                 Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                 val intent = Intent(applicationContext, LoginActivity::class.java)
                 startActivity(intent)
                 finish() */
            }
        } catch (e: Exception) {
            Log.d("Fuel Error ",e.message.toString())
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()

        }finally {

        }
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

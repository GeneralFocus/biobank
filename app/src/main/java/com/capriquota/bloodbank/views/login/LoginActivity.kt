package com.capriquota.bloodbank.views.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.capriquota.bloodbank.R
import com.capriquota.bloodbank.databinding.ActivityLoginBinding
import com.capriquota.bloodbank.model.AppConfig
import com.capriquota.bloodbank.viewmodel.LoginData
import com.capriquota.bloodbank.views.dashboard.Dashboard
import com.capriquota.bloodbank.views.register.RegistrationActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityLoginBinding
    private var loginDetails :LoginData = LoginData()
    private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        databinding.login.setOnClickListener{
            loginDetails.username = databinding.username .text.toString().trim()
            loginDetails.password = databinding.password.text.toString().trim()
            if (loginDetails.username.isEmpty()){
                databinding.username.error = "Field Can't Be Empty"
            }
            if( loginDetails.password.isEmpty()){
                databinding.password.error = "Field Can't Be Empty"
            }
            if(loginDetails.password.isNotEmpty() && loginDetails.username.isNotEmpty()){
                login()
            }
        }
        databinding.accountNo.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }//.account_no
    }

    @Throws(IOException::class, JSONException::class)
    private fun login(){
        showSimpleProgressDialog(this, null, "Loading...", false)
        try {
            Fuel.post(AppConfig.LOGIN_URL, listOf( "username" to  loginDetails.username
                    , "password" to  loginDetails.password
            )).responseJson { _, _, result ->
                Log.d("plzzzzz", result.get().content)
                val response  : String = result.get().content
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
                    removeSimpleProgressDialog()
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, Dashboard::class.java)
                    intent.putExtra("username", loginDetails.username)
                    startActivity(intent)
                    finish()
                }else{
                    removeSimpleProgressDialog()
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.d("Fuel Error ",e.message.toString())
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }finally {

        }
    }

    fun gotoHome(){
        startActivity(Intent(this, Dashboard::class.java))
        finish()
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

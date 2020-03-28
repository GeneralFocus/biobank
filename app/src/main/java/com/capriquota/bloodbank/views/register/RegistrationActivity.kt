package com.capriquota.bloodbank.views.register

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.capriquota.bloodbank.R
import com.capriquota.bloodbank.databinding.ActivityRegistrationBinding
import com.capriquota.bloodbank.model.AppConfig
import com.capriquota.bloodbank.viewmodel.RegistrationData
import com.capriquota.bloodbank.views.login.LoginActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private val regDetails : RegistrationData = RegistrationData()
   private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        binding.register.setOnClickListener {
            regDetails.name = binding.regName.text.toString().trim()
            regDetails.username = binding.regUserName.text.toString().trim()
            regDetails.password = binding.regPassword.text.toString().trim()
            regDetails.rpassword = binding.regConfirmPassword.text.toString().trim()
            if (regDetails.name.isEmpty()){
                binding.regName.error = "Field Can't Be Empty"
            }
            if(regDetails.username.isEmpty()){
                binding.regUserName.error ="Please enter your email address"
            }
            if(regDetails.password.isEmpty()){
                binding.regPassword.error = "Please enter a username"
            }

            if(regDetails.rpassword.equals(regDetails.password) && regDetails.password.isNotEmpty()){
                    register()
            }else{

                binding.regConfirmPassword.error = "Password do not match and cannot be empty"
            }
        }

        binding.accountYes.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @Throws(IOException::class, JSONException::class)
    private fun register(){
        showSimpleProgressDialog(this, null, "Loading...", false)
        try {
            Fuel.post(AppConfig.REGISTER_URL, listOf("name" to  regDetails.name
                    , "username" to  regDetails.username
                    , "password" to  regDetails.password
            )).responseJson { _, _, result ->
                Log.d("plzzzzz", result.get().content)

                val response  : String = result.get().content
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
                    removeSimpleProgressDialog()
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
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
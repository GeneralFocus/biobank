package com.capriquota.bloodbank.views.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.capriquota.bloodbank.R
import com.capriquota.bloodbank.databinding.ActivityDashboardBinding
import com.capriquota.bloodbank.views.controls.NewAppointment
import com.capriquota.bloodbank.views.controls.AppointmentList
import com.capriquota.bloodbank.views.controls.SettingsActivity
import com.google.android.material.snackbar.Snackbar

 class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        val value = intent.getStringExtra("username")

        // val intent  = getIntent()

        binding.cardAppointment.setOnClickListener {
           // Toast.makeText(this,username,Toast.LENGTH_LONG).show()
            val intent = Intent(this, NewAppointment::class.java)
           // intent.putExtra("username", intent.getStringExtra("username"))
            intent.putExtra("username", value)
         //   Toast.makeText(this,"variable value $value ",Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        binding.cardSettings.setOnClickListener {
            Snackbar.make(it,"Currently not available", Snackbar.LENGTH_LONG).show()
         //   val intent = Intent(applicationContext, SettingsActivity::class.java)
           // startActivity(intent)
        }

        binding.cardList.setOnClickListener {
            val intent = Intent(applicationContext, AppointmentList::class.java)
            startActivity(intent)
        }

    }

}

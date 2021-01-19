package com.capriquota.bloodbank.views.controls


import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.capriquota.bloodbank.R
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.util.Log


import java.util.ArrayList

@Suppress("DEPRECATION")

class JSONParser(private var c: Context, private var jsonData: String, private var myListView: ListView) : AsyncTask<Void, Void, Boolean>() {

    private lateinit var pd: ProgressDialog
    private var quotes = ArrayList<Quote>()


    class Quote(private var bio_name :String, private var bio_note : String, private var bio_date: String) {

        fun getBioName(): String {
            return bio_name
        }

        fun getBioNote(): String {
            return bio_note
        }

        fun getBioDate(): String {
            return bio_date
        }
    }
    class MrAdapter(private var c: Context, private var quotes: ArrayList<Quote>) : BaseAdapter() {

        override fun getCount(): Int {
            return quotes.size
        }

        override fun getItem(pos: Int): Any {
            return quotes[pos]
        }

        override fun getItemId(pos: Int): Long {
            return pos.toLong()
        }

        /*
        Inflate row_model.xml and return it
         */
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var convertView = view
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.row_item, viewGroup, false)
            }

            val bioOrgan = convertView!!.findViewById(R.id.bioOrgan) as TextView
            val bioNote = convertView.findViewById(R.id.bioNote) as TextView
            val bioDate = convertView.findViewById(R.id.bioDate) as TextView


            val quote = this.getItem(i) as Quote

            bioOrgan.text = quote.getBioName()
            bioNote.text = quote.getBioNote()
            bioDate.text = quote.getBioDate()


            convertView.setOnClickListener { Toast.makeText(c,quote.getBioName(),Toast.LENGTH_SHORT).show() }

            return convertView
        }
    }

    /*
    Parse JSON data
     */
    private fun parse(): Boolean {
        try {
            val ja = JSONArray(jsonData)
            var jo: JSONObject

            quotes.clear()
            var quote: Quote

            for (i in 0 until ja.length()) {
                jo = ja.getJSONObject(i)

                val bioName = jo.getString("bio_organ")
                val bioNote = jo.getString("bio_note")
                val bioDate = jo.getString("bio_date")

                quote = Quote(bioName,bioNote,bioDate)
                quotes.add(quote)
            }

            return true
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
    }
    override fun onPreExecute() {
        super.onPreExecute()

        pd = ProgressDialog(c)
        pd.setTitle("Parse JSON")
        pd.setMessage("Parsing...Please wait")
        pd.show()
    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        return parse()
    }

    override fun onPostExecute(isParsed: Boolean?) {
        super.onPostExecute(isParsed)

        pd.dismiss()
        if (isParsed!!) {
            //BIND
            myListView.adapter = MrAdapter(c, quotes)
        } else {
            Toast.makeText(c, "Unable To Parse that data. ARE YOU SURE IT IS VALID JSON DATA? JsonException was raised. Check Log Output.", Toast.LENGTH_LONG).show()
            Toast.makeText(c, "THIS IS THE DATA WE WERE TRYING TO PARSE :  "+ jsonData, Toast.LENGTH_LONG).show()
        }
    }
    //end

}

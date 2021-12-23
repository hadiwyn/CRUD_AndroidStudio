package com.example.exercise10
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.myapplication.adapter_data
import com.example.myapplication.m_data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var btnInput: FloatingActionButton
    lateinit var lvData: ListView
    var listData: ArrayList<m_data> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvData = findViewById(R.id.lvdata)

        input_data()

        fetch_data()



    }

    fun input_data() {

        btnInput = findViewById(R.id.btnInput)

        btnInput.setOnClickListener() {

            val mBuilder = LayoutInflater.from(this@MainActivity).inflate(R.layout.add_object, null)

            val alert = AlertDialog.Builder(this@MainActivity).setView(mBuilder).setTitle("Input Data")

            val alrt = alert.show()

            val Nama = mBuilder.findViewById<EditText>(R.id.Nama)
            val Nim = mBuilder.findViewById<EditText>(R.id.NIM)
            val btnAdd = mBuilder.findViewById<Button>(R.id.btnTambah)
            val btnCanc = mBuilder.findViewById<Button>(R.id.btnBatal)





            btnAdd.setOnClickListener() {

                AndroidNetworking.post("https://latihan.aviljepara.com/add_biodata.php")
                    .addBodyParameter("nama_mahasiswa", Nama.text.toString())
                    .addBodyParameter("nim", Nim.text.toString())
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(object: JSONObjectRequestListener{
                        override fun onResponse(response: JSONObject?) {
                            Toast.makeText(this@MainActivity, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(anError: ANError?) {
                            Toast.makeText(this@MainActivity, "Gagal Ditambahkam", Toast.LENGTH_SHORT).show()
                        }

                    })

                alrt.dismiss()



            }

            btnCanc.setOnClickListener() {

                alrt.dismiss()

            }


        }

    }

    fun fetch_data() {

        AndroidNetworking.get("https://latihan.aviljepara.com/fetch_biodata.php")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val data: JSONArray? = response?.getJSONArray("data")

                    for(i in 0 until data?.length()!!){
                        val item = data.getJSONObject(i)
                        listData.add(m_data(
                        item.getString("nama_mahasiswa"),
                        item.getString("nim"))) }

                        lvData = findViewById(R.id.lvdata)
                        val adp = adapter_data(this@MainActivity, listData)
                        lvData.adapter = adp
                }

                override fun onError(anError: ANError?) {
                    print("error")
                }

            })

    }


}
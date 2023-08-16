package com.example.retrofit

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.model.ResponseDataClass
import com.example.retrofit.model.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Btfetch.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        val progressDialog=ProgressDialog(this)
        progressDialog.setMessage("please wait while data is fetch")
        progressDialog.show()


        val apiInterface=RetrofitInstance.apiInterface
        apiInterface.getData().enqueue(object : Callback<ResponseDataClass?> {
            override fun onResponse(
                call: Call<ResponseDataClass?>,
                response: Response<ResponseDataClass?>,
            ) {

                binding.Tvauthour.text=response.body()?.author
                binding.Tvpostlink.text=response.body()?.postLink
                binding.Tvtitle.text=response.body()?.title
                binding.Tvsubreddit.text=response.body()?.subreddit
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.Ivimage);
                
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseDataClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity,"${t.localizedMessage}",Toast.LENGTH_LONG)
                    .show()

                progressDialog.dismiss()
            }
        })
    }
}



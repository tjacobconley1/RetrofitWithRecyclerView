package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    // variable to "adapt" the data from the
    // data class into the recycler view
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set the recycler view to a fixed size
        recyclerview_users.setHasFixedSize(true)
        // sets the context of where the layout will
        // be inflated
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_users.layoutManager = linearLayoutManager


        // executes the building of the
        // retrofit
        getMyData()

    }

    // this is where the retrofit is implemented
    private fun getMyData() {
        // create a variable to be the
        // Retrofit Builder
        val retrofitBuilder = Retrofit.Builder()
                // to convert the json response
            .addConverterFactory(GsonConverterFactory.create())
                // the base URL defined above the class
            .baseUrl(BASE_URL)
                // builds the retrofit instance
            .build()
                // instantiates an object of the ApiInterface
            .create(ApiInterface::class.java)

        // Uses the created Retrofit instance with use of
        // of the attributes we just defined for it such
        // as the Gson converter and the value of what we
        // set the BASE_URL to be
        // (the base url is defined here and the endpoint
        //    is defined in the ApiInterface with the @GET)
        val retrofitData = retrofitBuilder.getData()


        // queues a Callback the the (hopefully filled) data class
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            // this function pulls in the response
            // from the api that is being held in the
            // data class and assignes it to a variable
            override fun onResponse(call: Call<List<MyDataItem>?>, response: Response<List<MyDataItem>?>){
                val responseBody = response.body()!!
                //
                myAdapter = MyAdapter(baseContext, responseBody)
                myAdapter.notifyDataSetChanged()
                // pulls the values from myAdapter into
                // a usable recyclerview instance
                recyclerview_users.adapter = myAdapter

            }
            // this prints a Log if there was a failure
            // to retrieve any data, like if there was no
            // network connection or the api is down
            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}
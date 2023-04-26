package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Newsitemclicked {
    private lateinit var mviewadaptor:NewsAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RecycleView.layoutManager=LinearLayoutManager(this)
        Log.d("----------------->","url     fskkfjfklflksfsjfklsjfkl")
        data("us")
        mviewadaptor=NewsAdaptor(this)
        RecycleView.adapter=mviewadaptor
        searchsubmit.setOnClickListener {
            data(searchinput.text.toString()) ; val country=searchinput.text.toString()
            searchinput.setText("")

            Toast.makeText(this, "You are selected $country news ", Toast.LENGTH_SHORT).show()
        }
    }
    private fun data( country:String) {
          val urlinit="https://newsapi.org/v2/top-headlines?country="
          val urlend="&category=business&apiKey=d952872bfcda4129b3b48e0ce3d75b03"
//        val url = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey="//we have to take api key from newsApi.org 
        val url=urlinit+country+urlend
        Log.d("----------------->",url)
        Log.d("----------------->",country)
        val jsonObjectRequest = object :JsonObjectRequest(Request.Method.GET, url, null,//the request will run at the localhost only as done by get headers()

            { response ->
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mviewadaptor.updateNews(newsArray)

            },
            { _ ->

            })

        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {//to use the news API at localhost as the API is not working on internet in free version
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    override fun onitemClick(st: News) {
        val url = st.url
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
        Toast.makeText(this, "You are selected ${st.title} news ", Toast.LENGTH_SHORT).show()
    }
}
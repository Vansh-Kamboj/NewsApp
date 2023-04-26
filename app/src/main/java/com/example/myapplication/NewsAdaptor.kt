package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdaptor(val listen:Newsitemclicked):RecyclerView.Adapter<NewsVeiwholder>() {
    val item:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVeiwholder {//when we are going to create a new newx/item view
        val  vieww=LayoutInflater.from(parent.context).inflate(R.layout.news_view,parent,false)//here we have converted a xml format to view using layout inflator
        val curentview=NewsVeiwholder(vieww)
        Log.d("tag--->", curentview.bindingAdapterPosition.toString())
        vieww.setOnClickListener{ Log.d("tag--->", curentview.bindingAdapterPosition.toString())
             listen.onitemClick(item[curentview.bindingAdapterPosition])
            NewsVeiwholder(vieww)
        }
        return curentview
    }

    override fun onBindViewHolder(holder: NewsVeiwholder, position: Int) {    //to fill the data to a particular item(veiw)
      val currentnews=item[position]
        holder.newsview.setText(currentnews.title)
        holder.authorinfo.setText(currentnews.author)
        Glide.with(holder.itemView.context).load(currentnews.Imageurl).into(holder.newsimageinfo)
    }
    override fun getItemCount(): Int {//this tells the size of data we have
        return item.size
    }
    fun updateNews(updatedNews: ArrayList<News>){
        item.clear()
        item.addAll(updatedNews)
        notifyDataSetChanged()
    }
}

class NewsVeiwholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
val newsview:TextView=itemView.findViewById(R.id.newstextveiw)
   val newsimageinfo:ImageView=itemView.findViewById(R.id.Newsimage)
    val authorinfo:TextView=itemView.findViewById(R.id.authornews)

}
interface Newsitemclicked{
    fun onitemClick(st:News)
}
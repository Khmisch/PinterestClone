package com.example.pinterest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

import com.example.pinterest.R
import com.example.pinterest.activity.DetailsActivity
import com.example.pinterest.model.Photo
import com.example.pinterest.model.RelatedPhotos
import com.example.pinterest.network.RetrofitHttp
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsAdapter(private var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photoList = ArrayList<Photo>()

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photoList: ArrayList<Photo>) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPhotos(photoList: ArrayList<Photo>) {
        this.photoList.clear()
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_view, parent, false)
        return PhotoViewHolder(view)
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_pin)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val ivMore: ImageView = view.findViewById(R.id.iv_more)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val photoItem = photoList[position]
        val photoColor = photoItem.color
        val photoUrl = photoItem.urls!!.thumb

        if (holder is PhotoViewHolder) {
            holder.tvDescription.text = photoItem.user!!.bio
            Glide.with(context).load(photoUrl).placeholder(ColorDrawable(Color.parseColor(photoColor)))
                .into(holder.ivPhoto)

            holder.ivPhoto.setOnClickListener {
                callDetails(position)
            }
        }

    }

    private fun callDetails(position: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        val json = Gson().toJson(photoList)
        intent.putExtra("photoList", json)
        intent.putExtra("position", position)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}
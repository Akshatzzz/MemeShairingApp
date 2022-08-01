package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currenImageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load()
    }

  @Suppress("RedundantSamConstructor")
  private  fun load(){
      val pg=findViewById<ProgressBar>(R.id.Prog)
      pg.visibility=View.VISIBLE
      val img=findViewById<ImageView>(R.id.memeimageView)
      val url = "https://meme-api.herokuapp.com/gimme"
    val queue=Volley.newRequestQueue(this)
      val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
          Response.Listener { response ->
            val ur=response.getString("url")
              currenImageurl=ur
              Glide.with(this).load(ur).listener(object : RequestListener<Drawable>{
                  override fun onLoadFailed(
                      e: GlideException?,
                      model: Any?,
                      target: Target<Drawable>?,
                      isFirstResource: Boolean
                  ): Boolean {
                      pg.visibility=View.GONE
                      return false
                  }

                  override fun onResourceReady(
                      resource: Drawable?,
                      model: Any?,
                      target: Target<Drawable>?,
                      dataSource: DataSource?,
                      isFirstResource: Boolean
                  ): Boolean {
                      pg.visibility=View.GONE
                      return false
                  }


              }).into(img)

          },
          Response.ErrorListener { error ->

              Toast.makeText(this,"Not Done",Toast.LENGTH_LONG).show()
          }
      )
      queue.add(jsonObjectRequest)


  }
    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this crazy meme $currenImageurl")
        intent.type="text/plain"
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)

    }
    fun nextMeme(view: View) {

        load()
    }
}
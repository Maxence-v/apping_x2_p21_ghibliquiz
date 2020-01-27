package fr.epita.android.ghibliquizzpartielandroid

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import fr.epita.android.ghibliquizzpartielandroid.Interfaces.GhibliApiInterface
import fr.epita.android.ghibliquizzpartielandroid.Models.FilmObject
import kotlinx.android.synthetic.main.activity_characters_details.*
import kotlinx.android.synthetic.main.activity_characters_details.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class PeopleDetails : AppCompatActivity(), View.OnClickListener {

    override fun onClick(clickedView: View?) {
        if (clickedView != null) {
            when (clickedView.id) {
                R.id.button -> {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse("https://google.com/search?q=" + clickedView.titleLabel)
                    startActivity(openURL)
                }
                else -> {
                    Log.d("App failure", "onClick -> clickedView -> else reached")
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_details)

        button.setOnClickListener(this@PeopleDetails)
        // retrieve the intent infos
        val originIntent = intent
        val baseUrl = originIntent.getStringExtra("FILM_BASE_URL")
        val filmId = originIntent.getStringExtra("FILM_ID")
        val correctValue = originIntent.getBooleanExtra("CORRECT", false)
        val peopleName = originIntent.getStringExtra("CHARACTER_NAME")

        if (correctValue) {
            right.text = "RIGHT!"
            right.setTextColor(Color.GREEN)
        } else {
            right.text = "WRONG!"
            right.setTextColor(Color.RED)
        }

        charactersView.text = peopleName

        // set the caller service
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverter)
            .build()

        val service: GhibliApiInterface = retrofit.create(GhibliApiInterface::class.java)

        // set the callback for getting the chosen film
        val callbackFilm = object : Callback<FilmObject> {
            override fun onFailure(call: Call<FilmObject>, t: Throwable) {
                Log.d("APP", "failed to get the film")
                Log.d("APP", t.message)
            }

            override fun onResponse(call: Call<FilmObject>, response: Response<FilmObject>) {
                val rCode = response.code()
                if (rCode == 200) {
                    if (response.body() != null) {
                        val chosenFilm = response.body()!!

                        titleView.text = chosenFilm.title
                        synopsisView.text = chosenFilm.description
                        directorView.text = chosenFilm.director
                        yearView.text = chosenFilm.release_date



                        Log.d("APP", "retrieved the film")
                    } else {
                        Log.d("APP", "empty response")
                    }
                } else {
                    Log.d("APP", "bad response code received: $rCode")
                }
            }
        }

        service.getFilmDetail(filmId).enqueue(callbackFilm)
    }
}
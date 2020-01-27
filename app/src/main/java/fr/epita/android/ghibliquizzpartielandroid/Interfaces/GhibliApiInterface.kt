package fr.epita.android.ghibliquizzpartielandroid.Interfaces

import fr.epita.android.ghibliquizzpartielandroid.Models.FilmObject
import fr.epita.android.ghibliquizzpartielandroid.Models.PeopleObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface GhibliApiInterface {

    @GET("films")
    fun listFilms() : Call<ArrayList<FilmObject>>

    @GET("films/{id}")
    fun getFilmDetail(@Path("id") id: String) : Call<FilmObject>

    @GET("people")
    fun listPeople() : Call<ArrayList<PeopleObject>>
}
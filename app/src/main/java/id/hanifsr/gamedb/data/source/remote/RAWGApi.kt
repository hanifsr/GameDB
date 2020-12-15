package id.hanifsr.gamedb.data.source.remote

import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.model.GamesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RAWGApi {

	@GET("games")
	fun getPopularGames(
		@Query("key") apiKey: String,
		@Query("page") page: Int,
		@Query("page_size") pageSize: Int,
		@Query("dates") dates: String,
		@Query("ordering") ordering: String,
	): Call<GamesResponse>

	@GET("games/{id}")
	fun getGameDetail(
		@Path("id") id: Int,
		@Query("key") apiKey: String
	): Call<Game>

	@GET("games")
	fun searchGames(
		@Query("key") apiKey: String,
		@Query("search") keyword: String,
		@Query("ordering") ordering: String
	): Call<GamesResponse>
}
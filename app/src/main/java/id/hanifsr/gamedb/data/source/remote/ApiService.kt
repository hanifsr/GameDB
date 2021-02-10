package id.hanifsr.gamedb.data.source.remote

import id.hanifsr.gamedb.data.source.remote.response.GameDetailResponse
import id.hanifsr.gamedb.data.source.remote.response.GameListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	@GET("games")
	fun getPopularGames(
		@Query("key") apiKey: String,
		@Query("page") page: Int,
		@Query("page_size") pageSize: Int,
		@Query("dates") dates: String,
		@Query("ordering") ordering: String,
	): Call<GameListResponse>

	@GET("games/{id}")
	fun getGameDetail(
		@Path("id") id: Int,
		@Query("key") apiKey: String
	): Call<GameDetailResponse>

	@GET("games")
	fun searchGames(
		@Query("key") apiKey: String,
		@Query("search") keyword: String,
		@Query("ordering") ordering: String
	): Call<GameListResponse>
}
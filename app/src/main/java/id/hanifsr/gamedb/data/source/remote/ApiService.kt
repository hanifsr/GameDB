package id.hanifsr.gamedb.data.source.remote

import id.hanifsr.gamedb.data.source.remote.response.GameDetailDTO
import id.hanifsr.gamedb.data.source.remote.response.GameListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	@GET("games")
	suspend fun getPopularGames(
		@Query("key") apiKey: String,
		@Query("page") page: Int,
		@Query("page_size") pageSize: Int,
		@Query("dates") dates: String,
		@Query("ordering") ordering: String,
	): GameListDTO

	@GET("games/{id}")
	suspend fun getGameDetail(
		@Path("id") id: Int,
		@Query("key") apiKey: String
	): GameDetailDTO

	@GET("games")
	suspend fun searchGames(
		@Query("key") apiKey: String,
		@Query("search") keyword: String,
		@Query("ordering") ordering: String
	): GameListDTO
}
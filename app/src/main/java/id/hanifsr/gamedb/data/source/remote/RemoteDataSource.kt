package id.hanifsr.gamedb.data.source.remote

import id.hanifsr.gamedb.BuildConfig
import id.hanifsr.gamedb.data.source.remote.response.GameDetailResponse
import id.hanifsr.gamedb.data.source.remote.response.GameListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteDataSource {

	private const val BASE_URL = BuildConfig.RAWG_BASE_URL
	private const val API_KEY = BuildConfig.RAWG_API_KEY
	private val API_SERVICE: ApiService

	init {
		val retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BASE_URL)
			.build()

		API_SERVICE = retrofit.create(ApiService::class.java)
	}

	fun getPopularGames(
		dates: String,
		onSuccess: (gameListResponse: GameListResponse?) -> Unit,
		onError: (message: String) -> Unit
	) {
		API_SERVICE.getPopularGames(API_KEY, 1, 10, dates, "-added")
			.enqueue(object : Callback<GameListResponse> {
				override fun onResponse(
					call: Call<GameListResponse>,
					response: Response<GameListResponse>
				) {
					if (response.isSuccessful) {
						onSuccess.invoke(response.body())
					} else {
						onError.invoke(response.message())
					}
				}

				override fun onFailure(call: Call<GameListResponse>, t: Throwable) {
					onError.invoke(t.message.toString())
				}

			})
	}

	fun getGameDetail(
		id: Int,
		onSuccess: (gameDetailResponse: GameDetailResponse?) -> Unit,
		onError: (message: String) -> Unit
	) {
		API_SERVICE.getGameDetail(id, API_KEY)
			.enqueue(object : Callback<GameDetailResponse> {
				override fun onResponse(
					call: Call<GameDetailResponse>,
					response: Response<GameDetailResponse>
				) {
					if (response.isSuccessful) {
						onSuccess.invoke(response.body())
					} else {
						onError.invoke(response.message())
					}
				}

				override fun onFailure(call: Call<GameDetailResponse>, t: Throwable) {
					onError.invoke(t.message.toString())
				}

			})
	}

	fun searchGames(
		keyword: String,
		onSuccess: (gameListResponse: GameListResponse?) -> Unit,
		onError: (message: String) -> Unit
	) {
		API_SERVICE.searchGames(API_KEY, keyword, "-added")
			.enqueue(object : Callback<GameListResponse> {
				override fun onResponse(
					call: Call<GameListResponse>,
					response: Response<GameListResponse>
				) {
					if (response.isSuccessful) {
						onSuccess.invoke(response.body())
					} else {
						onError.invoke(response.message())
					}
				}

				override fun onFailure(call: Call<GameListResponse>, t: Throwable) {
					onError.invoke(t.message.toString())
				}

			})
	}
}
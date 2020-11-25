package id.hanifsr.gamedb.data.source.remote

import id.hanifsr.gamedb.BuildConfig
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.model.GamesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteRepository {

	private const val BASE_URL = "https://api.rawg.io/api/"
	private const val API_KEY = BuildConfig.RAWG_API_KEY
	private val rawgApi: RAWGApi

	init {
		val retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BASE_URL)
			.build()

		rawgApi = retrofit.create(RAWGApi::class.java)
	}

	fun getPopularGames(
		dates: String,
		onSuccess: (games: List<Game>) -> Unit,
		onError: () -> Unit
	) {
		rawgApi.getPopularGames(API_KEY, 1, 10, dates, "-added")
			.enqueue(object : Callback<GamesResponse> {
				override fun onResponse(
					call: Call<GamesResponse>,
					response: Response<GamesResponse>
				) {
					if (response.isSuccessful) {
						val gamesResponse = response.body()

						if (gamesResponse != null) {
							onSuccess.invoke(gamesResponse.results)
						} else {
							onError.invoke()
						}
					} else {
						onError.invoke()
					}
				}

				override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
					onError.invoke()
				}
			})
	}

	fun getGameDetail(
		id: Int,
		onSuccess: (game: Game) -> Unit,
		onError: () -> Unit
	) {
		rawgApi.getGameDetail(id, API_KEY)
			.enqueue(object : Callback<Game> {
				override fun onResponse(call: Call<Game>, response: Response<Game>) {
					if (response.isSuccessful) {
						val game = response.body()

						if (game != null) {
							onSuccess.invoke(game)
						} else {
							onError.invoke()
						}
					} else {
						onError.invoke()
					}
				}

				override fun onFailure(call: Call<Game>, t: Throwable) {
					onError.invoke()
				}
			})
	}
}
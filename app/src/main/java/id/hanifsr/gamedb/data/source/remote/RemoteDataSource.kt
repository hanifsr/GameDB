package id.hanifsr.gamedb.data.source.remote

import com.squareup.moshi.Moshi
import id.hanifsr.gamedb.BuildConfig
import id.hanifsr.gamedb.data.source.remote.response.GameDetailDTO
import id.hanifsr.gamedb.data.source.remote.response.GameListDTO
import id.hanifsr.gamedb.vo.ErrorCause
import id.hanifsr.gamedb.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.net.UnknownHostException

object RemoteDataSource {

	private const val BASE_URL = BuildConfig.RAWG_BASE_URL
	private const val API_KEY = BuildConfig.RAWG_API_KEY
	private val API_SERVICE: ApiService

	init {
		val retrofit = Retrofit.Builder()
			.addConverterFactory(MoshiConverterFactory.create())
			.baseUrl(BASE_URL)
			.build()

		API_SERVICE = retrofit.create(ApiService::class.java)
	}

	suspend fun getPopularGames(dates: String): Result<GameListDTO> = safeApiCall {
		API_SERVICE.getPopularGames(
			API_KEY, 1, 10, dates, "-added"
		)
	}

	suspend fun getGameDetail(id: Int): Result<GameDetailDTO> =
		safeApiCall { API_SERVICE.getGameDetail(id, API_KEY) }

	suspend fun searchGames(keyword: String): Result<GameListDTO> = safeApiCall {
		API_SERVICE.searchGames(
			API_KEY, keyword, "-added"
		)
	}

	private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
		return withContext(Dispatchers.IO) {
			try {
				Result.Success(apiCall.invoke())
			} catch (throwable: Throwable) {
				when (throwable) {
					is HttpException -> {
						val result = when (throwable.code()) {
							in 400..451 -> parseHttpError(throwable)
							in 500..599 -> error(
								ErrorCause.SERVER_ERROR,
								throwable.code(),
								"Server error"
							)
							else -> error(
								ErrorCause.NOT_DEFINED,
								throwable.code(),
								"Undefined error"
							)
						}
						result
					}
					is UnknownHostException -> error(
						ErrorCause.NO_CONNECTION,
						null,
						"No internet connection"
					)
					is IOException -> error(ErrorCause.BAD_RESPONSE, null, throwable.message)
					else -> error(ErrorCause.NOT_DEFINED, null, throwable.message)
				}
			}
		}
	}

	private fun error(cause: ErrorCause, code: Int?, errorMessage: String?): Result.Error =
		Result.Error(cause, code, errorMessage)

	private fun parseHttpError(httpException: HttpException): Result<Nothing> {
		return try {
			val errorBody =
				httpException.response()?.errorBody()?.string() ?: "Unknown HTTP error body"
			val moshi = Moshi.Builder().build()
			val adapter = moshi.adapter(Object::class.java)
			val errorMessage = adapter.fromJson(errorBody)
			error(ErrorCause.CLIENT_ERROR, httpException.code(), errorMessage.toString())
		} catch (exception: Exception) {
			error(ErrorCause.CLIENT_ERROR, httpException.code(), exception.localizedMessage)
		}
	}
}
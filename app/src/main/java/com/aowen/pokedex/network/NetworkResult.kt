package com.aowen.pokedex.network

import retrofit2.HttpException
import retrofit2.Response

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val body: T) : NetworkResult<T>()
    class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T : Any>(val exception: Throwable) : NetworkResult<T>()
}


/**
 * A safe api call that wraps the response in a [NetworkResult]
 * This allows us to handle the different states of the response
 * in a more custom way.
 * @param apiCall The api call to make
 */
suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = apiCall()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(response.code(), response.message())
        }
    } catch (httpException: HttpException) {
        NetworkResult.Error(
            httpException.code(),
            httpException.message()
        )
    } catch (exception: Throwable) {
        NetworkResult.Exception(exception)
    }
}
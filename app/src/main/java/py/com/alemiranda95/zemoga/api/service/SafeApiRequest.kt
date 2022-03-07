package py.com.alemiranda95.zemoga.api.service

import android.content.res.Resources
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.utils.exception.NetworkException
import retrofit2.Response
import java.net.UnknownHostException

abstract class SafeApiRequest(private val resource: Resources) {
    suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>) : T {
        val response = try {
            call.invoke()
        } catch (e : UnknownHostException) {
            throw NetworkException.NoInternetException(resource.getString(R.string.no_internet_error))
        }

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw NetworkException.ApiError(response.errorBody().toString())
        }
    }
}
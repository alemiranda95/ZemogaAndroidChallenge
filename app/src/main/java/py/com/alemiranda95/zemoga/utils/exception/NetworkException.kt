package py.com.alemiranda95.zemoga.utils.exception

import java.lang.Exception

sealed class NetworkException(message: String) : Exception(message) {
    class NoInternetException(message: String) : NetworkException(message)
    class ApiError(message: String) : NetworkException(message)
}
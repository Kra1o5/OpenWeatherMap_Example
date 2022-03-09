package com.randomdroids.data.common

/**
 * Response.
 *
 * @param T
 * @property status
 * @property data
 * @property message
 */
data class Response<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        /**
         * Success.
         *
         * @param T
         * @param data
         * @return success response
         */
        fun <T> success(data: T?): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        /**
         * Error.
         *
         * @param T
         * @param message
         * @param data
         * @return error response
         */
        fun <T> error(message: String, data: T?): Response<T> {
            return Response(Status.ERROR, data, message)
        }

        /**
         * Loading.
         *
         * @param T
         * @param data
         * @return loading response
         */
        fun <T> loading(data: T?): Response<T> {
            return Response(Status.LOADING, data, null)
        }
    }
}
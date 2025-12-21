package com.kanukim97.data.exception

/**
 * ## QueryLimitException
 *
 * nearbysearch/json response status when status is "OVER_QUERY_LIMIT"
 *
 * @property message
 */
class QueryLimitException(override val message: String = ""): Exception()

/**
 * ## RequestDeniedException
 *
 * nearbysearch/json response status when status is "OVER_QUERY_LIMIT"
 *
 * @property message
 */
class RequestDeniedException(override val message: String = ""): Exception()

/**
 * ## InvalidRequestException
 *
 * nearbysearch/json response status when status is "OVER_QUERY_LIMIT"
 *
 * @property message
 */
class InvalidRequestException(override val message: String = ""): Exception()

/**
 * ## UnknownErrorException
 *
 * nearbysearch/json response status when status is "UNKNOWN_ERROR"
 *
 * @property message
 */
class UnknownErrorException(override val message: String = ""): Exception()

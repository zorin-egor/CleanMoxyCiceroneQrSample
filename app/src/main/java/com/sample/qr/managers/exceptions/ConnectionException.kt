package com.sample.qr.managers.exceptions


import java.io.IOException

class ConnectionException : IOException {

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)

}
package com.himawan.weighbridge.data

object DateFormat {
    const val SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val TIME_FORMAT = "HH:mm"
}

object FirebaseReferences {
    const val TICKETS = "tickets"
}

enum class SortBy {
    ASC, // new
    DESC // old
}
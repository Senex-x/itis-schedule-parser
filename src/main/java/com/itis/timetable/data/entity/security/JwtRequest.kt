package com.itis.timetable.data.entity.security

import java.io.Serializable

data class JwtRequest(
    var username: String,
    var password: String
) : Serializable {
    companion object {
        private const val serialVersionUID = 5926468583005150707L
    }
}
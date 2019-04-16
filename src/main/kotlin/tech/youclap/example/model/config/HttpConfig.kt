package tech.youclap.example.model.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class HttpConfig(
    val port: Int = 0,
    val host: String? = null
)

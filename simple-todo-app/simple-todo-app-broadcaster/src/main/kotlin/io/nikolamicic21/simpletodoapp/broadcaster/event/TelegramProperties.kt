package io.nikolamicic21.simpletodoapp.broadcaster.event

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "telegram")
data class TelegramProperties
    @ConstructorBinding
    constructor(
    val urlFormat: String,
    val botSecret: String,
    val channelName: String
)

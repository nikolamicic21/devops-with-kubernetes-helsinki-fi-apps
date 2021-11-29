package io.nikolamicic21.simpletodoapp.broadcaster.config

import io.nikolamicic21.simpletodoapp.broadcaster.event.TelegramProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TelegramProperties::class)
class PropertiesConfiguration

package io.nikolamicic21.simpletodoapp.broadcaster.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.nats.client.Connection
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class TodoEventListener(
    private val natsConnection: Connection,
    private val objectMapper: ObjectMapper,
    @Value("\${event.todo.subject}") private val subject: String,
    @Value("\${event.todo.queue}") private val queue: String,
    private val telegramProperties: TelegramProperties,
    webClientBuilder: WebClient.Builder
) : InitializingBean {

    private val webClient: WebClient

    companion object {
        private val log = LoggerFactory.getLogger(Companion::class.java)
    }

    init {
        webClient = webClientBuilder
            .baseUrl(String.format(telegramProperties.urlFormat, telegramProperties.botSecret))
            .build()
    }

    override fun afterPropertiesSet() {
        natsConnection
            .createDispatcher { message ->
                val todo = objectMapper.readValue<Todo>(message.data)
                log.info("Sending Todo event to channel: $todo")
                webClient.get()
                    .uri { uriBuilder -> uriBuilder
                        .queryParam("chat_id", "@${telegramProperties.channelName}")
                        .queryParam("text", "title: ${todo.title}, status: ${todo.status}")
                        .build()
                    }
                    .retrieve()
                    .toBodilessEntity()
                    .subscribe()
            }
            .subscribe(subject, queue)
    }
}

package veontomo

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class Item2Handler {
    val items = linkedMapOf("first" to Item("key2", "value2"))

    fun getItems(req: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(BodyInserters.fromObject(items.values))


    fun getItem(req: ServerRequest): Mono<ServerResponse> =
            Mono.just(items[req.pathVariable("id")]).flatMap { v -> ServerResponse.ok().body(BodyInserters.fromObject(v)) }


    fun addItem(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(Item::class.java).flatMap { m ->
            items[m.key] = m
            ServerResponse.status(HttpStatus.CREATED).body(BodyInserters.fromObject(m))
        }
    }

    fun updateMessage(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(Item::class.java).flatMap { m ->
            val id = req.pathVariable("id")
            m.key = id
            items[id] = m
            ServerResponse.status(HttpStatus.ACCEPTED).body(BodyInserters.fromObject(m))
        }
    }

    fun deleteMessage(req: ServerRequest): Mono<ServerResponse> {
        return Mono.just(req.pathVariable("id")).flatMap { id ->
            items.remove(id)
            ServerResponse.status(HttpStatus.ACCEPTED).build()
        }
    }
}
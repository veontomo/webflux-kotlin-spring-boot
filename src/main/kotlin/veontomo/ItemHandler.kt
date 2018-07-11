package veontomo

import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ItemHandler {

    val items = linkedMapOf("first" to Item("key", "value"))

    fun getItems(req: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(fromObject(items.values))


    fun getItem(req: ServerRequest): Mono<ServerResponse> =
            Mono.just(items[req.pathVariable("id")]).flatMap { v -> ServerResponse.ok().body(fromObject(v)) }

    
    fun addItem(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(Item::class.java).flatMap { m ->
            items[m.key] = m
            ServerResponse.status(CREATED).body(fromObject(m))
        }
    }

    fun updateMessage(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(Item::class.java).flatMap { m ->
            val id = req.pathVariable("id")
            m.key = id
            items[id] = m
            ServerResponse.status(ACCEPTED).body(fromObject(m))
        }
    }
    
    fun deleteMessage(req: ServerRequest): Mono<ServerResponse> {
        return Mono.just(req.pathVariable("id")).flatMap { id -> 
            items.remove(id)
            ServerResponse.status(ACCEPTED).build()
        }
    }
}
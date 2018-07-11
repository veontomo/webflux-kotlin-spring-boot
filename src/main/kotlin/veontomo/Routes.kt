package veontomo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes(private val handler: ItemHandler) {

    @Bean
    fun apis() = router {
        (accept(APPLICATION_JSON) and "/items").nest {
            GET("/", handler::getItems)
            POST("/", handler::addItem)
        }
    }

}
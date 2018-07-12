package veontomo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes2(private val handler: Item2Handler) {

    @Bean
    fun apis2() = router {
        (accept(APPLICATION_JSON) and "/items2").nest {
            GET("/", handler::getItems)
            POST("/", handler::addItem)
        }
    }

}

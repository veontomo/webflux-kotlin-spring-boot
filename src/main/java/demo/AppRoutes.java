package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import veontomo.ItemHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AppRoutes {

    @Autowired
    private ItemHandler itemHandler;

    @Bean
    RouterFunction<?> apis() {
        return nest(path("/hotels"), nest(accept(MediaType.APPLICATION_JSON),
                route(
                        GET("/"), itemHandler::getItems)
                        .andRoute(POST("/"), itemHandler::addItem)
                        .andRoute(GET("/{id}"), itemHandler::getItem)
                        .andRoute(PUT("/{id}"), itemHandler::updateMessage)
                        .andRoute(DELETE("/{id}"), itemHandler::deleteMessage)
        ));
    }

}
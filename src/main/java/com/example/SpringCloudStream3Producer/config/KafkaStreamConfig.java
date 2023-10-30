package com.example.SpringCloudStream3Producer.config;

import com.example.SpringCloudStream3Producer.model.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class KafkaStreamConfig {

    //Sink
    @Bean
    public Sinks.Many<Book> sink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    //(flux) Supplier
    @Bean
    public Supplier<Flux<Book>> flux(Sinks.Many<Book> sink) {
        return sink::asFlux;
    }


}

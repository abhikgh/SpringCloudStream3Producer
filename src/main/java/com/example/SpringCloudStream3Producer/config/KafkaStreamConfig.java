package com.example.SpringCloudStream3Producer.config;

import com.example.SpringCloudStream3Producer.model.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class KafkaStreamConfig {

    //Sink
    @Bean
    public Sinks.Many<Book> sink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    //flux (Sink)
    @Bean
    public Supplier<Flux<Book>> producer(Sinks.Many<Book> sink) {
        return sink::asFlux;
    }

    @Bean
    public Consumer<Book> consumer(){
        return book -> System.out.println("Book received .. " + book.getId() + "-" + book.getBookName() + "-" + book.getBookIsbn() + "-" + book.getAuthorId());
    }

}

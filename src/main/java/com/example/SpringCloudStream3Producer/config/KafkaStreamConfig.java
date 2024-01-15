package com.example.SpringCloudStream3Producer.config;

import com.example.SpringCloudStream3Producer.model.Book;
import com.example.SpringCloudStream3Producer.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class KafkaStreamConfig {

    @Autowired
    private BookService bookService;

    //Sink
    @Bean
    public Sinks.Many<Book> sink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    //sink as flux
    @Bean
    public Supplier<Flux<Book>> supplier(Sinks.Many<Book> sink) {
        return sink::asFlux;
    }

    @Bean
    public Function<Flux<Book>, Flux<Book>> function() {
        return bookFlux -> bookFlux.map(bookService::updateBook);
    }

    @Bean
    public Consumer<Book> consumer(){
        return book -> System.out.println("Book received .. " + book.getId() + "-" + book.getBookName() + "-" + book.getBookIsbn() + "-" + book.getAuthorId());
    }

}

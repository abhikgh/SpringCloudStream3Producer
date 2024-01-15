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

    /*

        private Random random = new Random();

        @Bean
        public Supplier<Flux<Integer>> producer(){
            return () -> Flux.interval(Duration.ofSeconds(5)).map(value -> random.nextInt(1000) + 1).log();
        }

        @Bean
        public Function<Flux<Integer>, Flux<String>> processor(){
            return integerFlux -> integerFlux.map(this::evaluateFizzBuzz).log();
        }

        @Bean
        public Consumer<String> consumer(){
            return value -> System.out.println("Consumer received :: " + value);
        }

        private String evaluateFizzBuzz(Integer value) {
            if (value % 15 == 0) {
                return "FizzBuzz";
            } else if (value % 5 == 0) {
                return "Buzz";
            } else if (value % 3 == 0) {
                return "Fizz";
            } else {
                return String.valueOf(value);
            }
        }


     */

}

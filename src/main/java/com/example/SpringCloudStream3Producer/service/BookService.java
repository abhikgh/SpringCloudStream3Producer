package com.example.SpringCloudStream3Producer.service;

import com.example.SpringCloudStream3Producer.model.Book;
import com.example.SpringCloudStream3Producer.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //@Autowired
    //private WebClient webClient;

    public Book updateBook(Book book) {
        book.setBookName(book.getBookName().toUpperCase());
        bookRepository.findById(book.getId()).ifPresent(book1 -> {
            book.setBookIsbn(book1.getBookIsbn());
            book.setAuthorId(book1.getAuthorId());
        });

       /* Mono<Book> bookMono = webClient
                .method(HttpMethod.GET)
                .uri("http://localhost:9898/rx/getBookDetails/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("Exception :: " + response)))
                .bodyToMono(Book.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(error -> Mono.just(new Book(1, "Test", "kdkd",33)));

        bookMono.subscribe(book1 -> book.setAuthorId(book1.getAuthorId()+5678));*/

        System.out.println("Book processed successfully...");
        return book;
}
}

package com.example.SpringCloudStream3Producer.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class ReactiveConfig {

    @Value("${connectionTimeout:10000}")
    private Integer connectionTimeout;

    @Value("${requestTimeout:10000}")
    private Integer requestTimeout;

    @Value("${readTimeout:10000}")
    private Integer readTimeout;

    @Bean
    public WebClient webClient(){
        HttpClient httpClient =
                HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                        .responseTimeout(Duration.ofMillis(10000))
                        .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(10000)));

        return  WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

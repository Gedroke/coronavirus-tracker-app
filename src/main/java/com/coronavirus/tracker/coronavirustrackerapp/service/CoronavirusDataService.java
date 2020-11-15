package com.coronavirus.tracker.coronavirustrackerapp.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronavirusDataService {

    // == constant - URI ==
    private final URI COVID19_CONFIRMED_GLOBAL = URI.create("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv");

    // == public constant ==
    @PostConstruct
    public void fetchData() throws IOException, InterruptedException {

        // Creating HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        // Creating HTTP Request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(COVID19_CONFIRMED_GLOBAL)
                .build();

        // Building an HTTP Response
        HttpResponse<String> httpResponse
                = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Console print out
        System.out.println(httpResponse.body());
    }
}

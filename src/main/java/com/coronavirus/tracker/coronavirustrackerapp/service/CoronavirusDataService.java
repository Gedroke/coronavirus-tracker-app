package com.coronavirus.tracker.coronavirustrackerapp.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronavirusDataService {

    // == constant - URI ==
    private static final URI COVID19_CONFIRMED_GLOBAL = URI.create("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv");

    // == public constant ==
    @PostConstruct
    @Scheduled(cron="0 0 9 * * ?") // This application will run every day at 9 am (local)
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

        // CSV reader - Parsing in the right format
        StringReader csvBodyReader  = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            System.out.println(state +"\n"+ country);
        }
    }
}

package com.coronavirus.tracker.coronavirustrackerapp.service;

import com.coronavirus.tracker.coronavirustrackerapp.model.LocationStats;
import lombok.Getter;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusDataService {

    // == constant - URI of the data source ==
    private static final URI COVID19_CONFIRMED_GLOBAL = URI.create("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv");

    // == LocationStat List Instance ==
    @Getter
    private List<LocationStats> allStats = new ArrayList<>();


    // == public constant ==
    @PostConstruct
    @Scheduled(cron="0 0 9 * * ?") // This application will run every day at 9 am (local)
    public void fetchData() throws IOException, InterruptedException {

        // Creating a temporary list that will populate after deconstruction the final one
        List<LocationStats> tempStats = new ArrayList<>();

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
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));

            // Local variable for current and previous (day) reported cases and parse it to an int
            int currentDay = Integer.parseInt(record.get(record.size() - 1));
            int previousDay = Integer.parseInt(record.get(record.size() - 2));

            // Creating a delta to track the evolution of the virus - From Previous to Current day
            int delta = currentDay - previousDay;

            // Set the data in our object
            locationStat.setLatestTotalCases(currentDay);
            locationStat.setPreviousTotalCases(previousDay);
            locationStat.setDelta(delta);


            // Populate the temporary list with data field
            tempStats.add(locationStat);
        }
        // Populate the final list with the temp - This is a way to avoid some concurrency issues but it's not 100% proof
        this.allStats = tempStats;

    }













}

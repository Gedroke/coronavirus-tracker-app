package com.coronavirus.tracker.coronavirustrackerapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// == This class will store data in memory ==
@Getter
@Setter
@ToString
public class LocationStats {

    // == fields ==
    private String state;
    private String country;

    private int latestTotalCases;
    private int previousTotalCases;

    private int delta;
    private int evolutionRate;

}

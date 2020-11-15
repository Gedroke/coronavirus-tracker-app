package com.coronavirus.tracker.coronavirustrackerapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// == This class will store data in memory ==
@Getter
@Setter
@ToString
public class LocationStat {

    // == fields ==
    private String state;
    private String country;
    private int LatestTotalCases;

}

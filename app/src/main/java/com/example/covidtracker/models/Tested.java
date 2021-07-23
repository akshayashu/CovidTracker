package com.example.covidtracker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tested{
    public String dailyrtpcrsamplescollectedicmrapplication;
    public String firstdoseadministered;
    public String frontlineworkersvaccinated1stdose;
    public String frontlineworkersvaccinated2nddose;
    public String healthcareworkersvaccinated1stdose;
    public String healthcareworkersvaccinated2nddose;
    public String over45years1stdose;
    public String over45years2nddose;
    public String over60years1stdose;
    public String over60years2nddose;
    public String positivecasesfromsamplesreported;
    @JsonProperty("registration18-45years")
    public String registration1845years;
    public String registrationabove45years;
    public String registrationflwhcw;
    public String registrationonline;
    public String registrationonspot;
    public String samplereportedtoday;
    public String seconddoseadministered;
    public String source;
    public String source2;
    public String source3;
    public String source4;
    public String testedasof;
    public String testsconductedbyprivatelabs;
    @JsonProperty("to60yearswithco-morbidities1stdose")
    public String to60yearswithcoMorbidities1stdose;
    @JsonProperty("to60yearswithco-morbidities2nddose")
    public String to60yearswithcoMorbidities2nddose;
    public String totaldosesadministered;
    public String totaldosesavailablewithstates;
    public String totaldosesavailablewithstatesprivatehospitals;
    public String totaldosesinpipeline;
    public String totaldosesprovidedtostatesuts;
    public String totalindividualsregistered;
    public String totalindividualstested;
    public String totalindividualsvaccinated;
    public String totalpositivecases;
    public String totalrtpcrsamplescollectedicmrapplication;
    public String totalsamplestested;
    public String totalsessionsconducted;
    public String totalvaccineconsumptionincludingwastage;
    public String updatetimestamp;
    public String years1stdose;
    public String years2nddose;
}


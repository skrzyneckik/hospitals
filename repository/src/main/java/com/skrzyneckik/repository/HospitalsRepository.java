package com.skrzyneckik.repository;

import com.skrzyneckik.domain.Hospital;

import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import rx.Observable;

public class HospitalsRepository {

    private final OkHttpClient httpClient;

    public HospitalsRepository() {
        httpClient = new OkHttpClient.Builder().build();
    }
    public Observable<List<Hospital>> hospitals() {
        return Observable.just(Arrays.asList(
                new Hospital("Newquay Hospital"),
                new Hospital("Bridgewater Hospital")
        ));
    }
}

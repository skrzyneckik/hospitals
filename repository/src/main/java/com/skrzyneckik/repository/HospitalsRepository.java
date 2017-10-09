package com.skrzyneckik.repository;

import com.skrzyneckik.domain.Hospital;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class HospitalsRepository {

    public Observable<List<Hospital>> hospitals() {
        return Observable.just(Arrays.asList(
                new Hospital("Newquay Hospital"),
                new Hospital("Bridgewater Hospital")
        ));
    }
}

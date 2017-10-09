package com.skrzyneckik.domain;

import java.io.Serializable;

/**
 * {@link Hospital} entity
 */
public final class Hospital implements Serializable {

    public static final int PARAMS_NUMBER = 22;

    private int organisationID;
    private String organisationCode;
    private String organisationType;
    private String subType;
    private String sector;
    private String organisationStatus;
    private boolean isPimsManaged;
    private String organisationName;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String county;
    private String postcode;
    private double latitude;
    private double longitude;
    private String parentODSCode;
    private String parentName;
    private String phone;
    private String email;
    private String website;
    private String fax;

    public Hospital(String[] hospitalParams) {
        organisationID = Integer.valueOf(hospitalParams[0]);
        organisationCode = hospitalParams[1];
        organisationType = hospitalParams[2];
        subType = hospitalParams[3];
        sector = hospitalParams[4];
        organisationStatus = hospitalParams[5];
        isPimsManaged = Boolean.valueOf(hospitalParams[6]);
        organisationName = hospitalParams[7];
        address1 = hospitalParams[8];
        address2 = hospitalParams[9];
        address3 = hospitalParams[10];
        city = hospitalParams[11];
        county = hospitalParams[12];
        postcode = hospitalParams[13];
        latitude = !isEmpty(hospitalParams[14]) ? Float.valueOf(hospitalParams[14]) : 0.f;
        longitude = !isEmpty(hospitalParams[15]) ? Float.valueOf(hospitalParams[15]) : 0.f;
        parentODSCode = hospitalParams[16];
        parentName = hospitalParams[17];
        phone = hospitalParams[18];
        email = hospitalParams[19];
        website = hospitalParams[20];
        fax = hospitalParams[21];

    }

    public Hospital(String organisationName) {
        this.organisationName = organisationName;
    }

    public String organisationName() {
        return organisationName;
    }

    private static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}

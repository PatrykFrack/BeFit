package com.frackowiak.befit.Database;

import com.frackowiak.database.dao.Beacon;
import com.frackowiak.database.dao.Errand;

/**
 * Created by PFRACKOW on 09.12.2015.
 */
public class DummyDatabase {

    public void fillDummyBeacons(){
        Beacon beacon = new Beacon();
        beacon.setId(9999l);
        beacon.setMACAddress("20:FA:BB:01:77:D0");
        beacon.setUUID("F2A74FC4-7625-44DB-9B08-CB7E130B2029");
        beacon.setMajor("65535");
        beacon.setMinor("195");
    }
    public void fillDummyErrands(){
        for(int i=1; i<=10; i++){
            Errand errand = new Errand();
            errand.setName("Name"+i);
            errand.setDescription("Description" + i);
            errand.setPoints(10 + i);
            errand.setBeaconMain(9999l);
        }
    }
}

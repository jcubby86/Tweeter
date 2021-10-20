package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.util.FakeData;

public abstract class Service {
    protected FakeData getFakeData() {
        return new FakeData();
    }


}

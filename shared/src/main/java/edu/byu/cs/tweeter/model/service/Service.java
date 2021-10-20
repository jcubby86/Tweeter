package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.util.FakeData;

public abstract class Service {
    protected FakeData getFakeData() {
        return new FakeData();
    }


}

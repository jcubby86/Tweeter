package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.util.FakeData;

public abstract class Service {
    protected final String INTERNAL_SERVER_ERROR = "[InternalServerError]";
    protected FakeData getFakeData() {
        return new FakeData();
    }


}

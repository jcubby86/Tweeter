package edu.byu.cs.tweeter.server.dao;

public class FactoryManager {
    private static DAOFactory factory = new DynamoDBDAOFactory();

    public static DAOFactory getFactory() {
        return factory;
    }

    public static void setFactory(DAOFactory factory) {
        FactoryManager.factory = factory;
    }
}

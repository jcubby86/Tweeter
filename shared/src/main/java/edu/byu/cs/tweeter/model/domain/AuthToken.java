package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.Random;

public class AuthToken implements Serializable {
    private final static int ID_SIZE = 10;

    private String token;
    private Long timeMillis;
    private String userAlias;

    public AuthToken() {
        this.token = generateUniqueID();
        this.timeMillis = System.currentTimeMillis();
    }

    public AuthToken(String userAlias) {
        this();
        this.userAlias = userAlias;
    }

    public AuthToken(String token, Long timeMillis, String userAlias) {
        this.token = token;
        this.timeMillis = timeMillis;
        this.userAlias = userAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }
    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public void setTimeMillis(Long timeMillis) {
        this.timeMillis = timeMillis;
    }
    public Long getTimeMillis() {
        return timeMillis;
    }

    private static String generateUniqueID(){
        Random r = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < ID_SIZE; i++){
            int randNum = r.nextInt(36);
            if (randNum < 10){
                id.append(randNum);
            } else {
                id.append((char) ('a' - 10 + randNum));
            }
        }
        return id.toString();
    }
}

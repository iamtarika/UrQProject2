package com.example.jnstar.urqproject;



/**
 * Created by MSI on 21/1/2561.
 */

public class ListSearchStore {

    private String name_shop;
    private String q_shop;

    public ListSearchStore(String name_shop, String q_shop){
        this.name_shop = name_shop;
        this.q_shop = q_shop;

    }

    public String getName_shop() {
        return name_shop;
    }

    public String getQ_shop() {
        return q_shop;
    }
}

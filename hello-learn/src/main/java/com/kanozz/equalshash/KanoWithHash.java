package com.kanozz.equalshash;

import java.util.Objects;

public class KanoWithHash {

    private String name;
    private int age;


    public KanoWithHash(String name,int age){
        this.name = name;
        this.age = age;
    }



    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

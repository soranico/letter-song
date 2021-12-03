package com.kanozz.equalshash;

import java.util.Objects;

public class KanoWithEquals {


    private String name;
    private int age;


    public KanoWithEquals(String name,int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KanoWithEquals that = (KanoWithEquals) o;
        return age == that.age && Objects.equals(name, that.name);
    }

}

package com.kanozz.codeblock;

public class Kano {
    static {
        System.out.println("first");
    }
    public Kano(){
        System.out.println("construct");
    }

    private static class Inn{
        public static void main(String[] args) {
            Kano kano = new Kano();
             kano = new Kano();
        }
    }
}


package com.example.jokes;

import java.util.Random;

public class JokesClass {

    private final String[] jokes = {
            "This is a funny joke",
            "This is also a funny joke",
            "Hahahah gotchaa !!"
    };

    public String getJoke() {
        int index = new Random().nextInt(jokes.length);
        return jokes[index];
    }
}
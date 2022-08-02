package com.mhammad.photoapp.api;

import com.mhammad.photoapp.api.Hit;

import java.util.List;

public class Response {
public int total;
public int totalHits;
private List<Hit> hits;

    public List<Hit> getHits() {
        return hits;
    }
}

package com.example.swinburne.accessiblitymap.Model;

import java.util.HashMap;

public class PaginationRequest {
    private int limit = 10;
    private int offset = 0;

    public PaginationRequest() {
        this.limit = 10;
        this.offset = 0;
    }

    public PaginationRequest(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

//    public PaginationRequest nextRequest() {
//        if (limit == 10 && offset == 0) {
//
//        }
//        return new PaginationRequest()
//    }

    public void nextPageRequest() {
        offset = offset + limit;
    }

    public HashMap<String, String> convertToHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put("$limit", String.valueOf(limit));
        data.put("$offset",String.valueOf(offset));
        return data;
    }

}

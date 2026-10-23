package com.smartmedicine.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Validates the per-browser workspace key sent in the X-Workspace-Id header. */
final class Ws {
    private Ws() {}
    static String check(String id) {
        if (id == null || !id.matches("[A-Za-z0-9-]{32,64}"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid X-Workspace-Id header");
        return id;
    }
}

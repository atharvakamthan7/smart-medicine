package com.smartmedicine.backend;

import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pharmacies")
public class PharmacyController {
    private static final String H = "X-Workspace-Id";
    private final PharmacyRepository repo;

    public PharmacyController(PharmacyRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Pharmacy> list(@RequestHeader(value = H, required = false) String ws) {
        return repo.findByWorkspaceIdOrderByNameAsc(Ws.check(ws));
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Pharmacy create(@RequestHeader(value = H, required = false) String ws, @Valid @RequestBody Pharmacy in) {
        String w = Ws.check(ws);
        if (repo.countByWorkspaceId(w) >= 200) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pharmacy limit reached (200)");
        in.id = UUID.randomUUID().toString();
        in.workspaceId = w;
        return repo.save(in);
    }

    @PutMapping("/{id}")
    public Pharmacy update(@RequestHeader(value = H, required = false) String ws, @PathVariable String id, @Valid @RequestBody Pharmacy in) {
        Pharmacy p = repo.findByIdAndWorkspaceId(id, Ws.check(ws))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacy not found"));
        p.name = in.name; p.phone = in.phone; p.area = in.area; p.notes = in.notes;
        return repo.save(p);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader(value = H, required = false) String ws, @PathVariable String id) {
        Pharmacy p = repo.findByIdAndWorkspaceId(id, Ws.check(ws))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacy not found"));
        repo.delete(p);
    }
}

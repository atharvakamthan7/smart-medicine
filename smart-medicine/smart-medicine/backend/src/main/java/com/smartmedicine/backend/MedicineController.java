package com.smartmedicine.backend;

import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {
    private static final String H = "X-Workspace-Id";
    private final MedicineRepository repo;
    private final DoseLogRepository logs;

    public MedicineController(MedicineRepository repo, DoseLogRepository logs) { this.repo = repo; this.logs = logs; }

    @GetMapping
    public List<Medicine> list(@RequestHeader(value = H, required = false) String ws) {
        return repo.findByWorkspaceIdOrderByTimeAsc(Ws.check(ws));
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Medicine create(@RequestHeader(value = H, required = false) String ws, @Valid @RequestBody Medicine in) {
        String w = Ws.check(ws);
        if (repo.countByWorkspaceId(w) >= 500) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medicine limit reached (500)");
        in.id = UUID.randomUUID().toString();
        in.workspaceId = w;
        return repo.save(in);
    }

    @PutMapping("/{id}")
    public Medicine update(@RequestHeader(value = H, required = false) String ws, @PathVariable String id, @Valid @RequestBody Medicine in) {
        Medicine m = repo.findByIdAndWorkspaceId(id, Ws.check(ws))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found"));
        m.name = in.name; m.person = in.person; m.dose = in.dose; m.time = in.time; m.qty = in.qty; m.low = in.low; m.notes = in.notes;
        return repo.save(m);
    }

    @DeleteMapping("/{id}") @Transactional @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader(value = H, required = false) String ws, @PathVariable String id) {
        String w = Ws.check(ws);
        Medicine m = repo.findByIdAndWorkspaceId(id, w)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found"));
        logs.deleteByWorkspaceIdAndMedicineId(w, id);
        repo.delete(m);
    }
}

package com.smartmedicine.backend;

import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AppController {
    private static final String H = "X-Workspace-Id";
    private final DoseLogRepository logs; private final MedicineRepository meds;
    private final PharmacyRepository pharmacies; private final ProfileRepository profiles;
    private final AvailabilityProvider availability;

    public AppController(DoseLogRepository logs, MedicineRepository meds, PharmacyRepository pharmacies,
                         ProfileRepository profiles, AvailabilityProvider availability) {
        this.logs = logs; this.meds = meds; this.pharmacies = pharmacies; this.profiles = profiles; this.availability = availability;
    }

    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "ok"); }

    @GetMapping("/logs")
    public List<DoseLog> logs(@RequestHeader(value = H, required = false) String ws, @RequestParam String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "date must be yyyy-MM-dd");
        return logs.findByWorkspaceIdAndDate(Ws.check(ws), date);
    }

    @PostMapping("/logs") @ResponseStatus(HttpStatus.CREATED)
    public DoseLog markTaken(@RequestHeader(value = H, required = false) String ws, @Valid @RequestBody DoseLog in) {
        String w = Ws.check(ws);
        if (!meds.existsByIdAndWorkspaceId(in.medicineId, w)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found");
        if (!logs.existsByWorkspaceIdAndMedicineIdAndDateAndTime(w, in.medicineId, in.date, in.time)) {
            in.id = UUID.randomUUID().toString();
            in.workspaceId = w;
            logs.save(in);
        }
        return in;
    }

    @GetMapping("/profile")
    public Profile profile(@RequestHeader(value = H, required = false) String ws) {
        return profiles.findById(Ws.check(ws)).orElseGet(Profile::new);
    }

    @PutMapping("/profile")
    public Profile saveProfile(@RequestHeader(value = H, required = false) String ws, @Valid @RequestBody Profile in) {
        in.workspaceId = Ws.check(ws);
        return profiles.save(in);
    }

    @DeleteMapping("/data") @Transactional @ResponseStatus(HttpStatus.NO_CONTENT)
    public void wipe(@RequestHeader(value = H, required = false) String ws) {
        String w = Ws.check(ws);
        logs.deleteByWorkspaceId(w); meds.deleteByWorkspaceId(w); pharmacies.deleteByWorkspaceId(w);
        profiles.findById(w).ifPresent(profiles::delete);
    }

    @GetMapping("/availability")
    public AvailabilityProvider.Result availability(@RequestParam String name) {
        String n = name.trim();
        if (n.isEmpty() || n.length() > 100) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter a medicine name (max 100 characters)");
        return availability.lookup(n);
    }
}

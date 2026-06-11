package fr.loic_delprat.ymmo_api.controller;

import fr.loic_delprat.ymmo_api.dto.request.AgencyRequest;
import fr.loic_delprat.ymmo_api.dto.response.AgencyResponse;
import fr.loic_delprat.ymmo_api.service.AgencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencies")
@RequiredArgsConstructor
public class AgencyController {
    private final AgencyService agencyService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<AgencyResponse> createAgency(@Valid @RequestBody AgencyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agencyService.createAgency(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyResponse> getAgencyById(@PathVariable Long id) {
        return ResponseEntity.ok(agencyService.getAgencyById(id));
    }

    @GetMapping
    public ResponseEntity<List<AgencyResponse>> getAllAgencies() {
        return ResponseEntity.ok(agencyService.getAllAgencies());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<AgencyResponse> updateAgency(@PathVariable Long id, @Valid @RequestBody AgencyRequest request) {
        return ResponseEntity.ok(agencyService.updateAgency(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        agencyService.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }
}


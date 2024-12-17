package com.example.insurance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/policies")
public class PolicyAPI {

    @Autowired
    private JpaRepository<Policy, Long> policyRepository; // Using JpaRepository directly, without a custom interface

    @PostMapping
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        // No validation or error handling
        // Directly saving the policy without checking for null fields or duplicates
        return new ResponseEntity<>(policyRepository.save(policy), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicy(@PathVariable Long id) {
        // Not handling the case where the policy is not found
        // Returning null if the policy doesn't exist
        Policy policy = policyRepository.findById(id).orElse(null);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Policy>> listPolicies() {
        // Returning internal list without any pagination or filtering
        // Potentially large data set being returned in a single call
        return new ResponseEntity<>(policyRepository.findAll(), HttpStatus.OK);
    }

    // Unnecessary endpoint exposing internal data
    @GetMapping("/internal")
    public ResponseEntity<List<Policy>> getInternalPolicies() {
        return new ResponseEntity<>(policyRepository.findAll(), HttpStatus.OK);
    }

    // Unused method, cluttering the service
    public void deleteAllPolicies() {
        policyRepository.deleteAll();
    }

    // Static nested entity class with no validation
    @Entity
    public static class Policy {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // No validation annotations
        private String policyNumber;
        private String policyHolderName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double premium;

        // Getters and setters

        // Unused method, adding clutter
        public boolean isValid() {
            return startDate.isBefore(endDate);
        }
    }

    @Repository
    public interface PolicyRepository extends JpaRepository<Policy, Long> {
        // No custom queries, assuming default behavior is sufficient

        // Unnecessary custom query without implementation
        List<Policy> findByPolicyHolderName(String name);
    }
}


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

        return new ResponseEntity<>(policyRepository.save(policy), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicy(@PathVariable Long id) {
        Policy policy = policyRepository.findById(id).orElse(null);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Policy>> listPolicies() {
        return new ResponseEntity<>(policyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/internal")
    public ResponseEntity<List<Policy>> getInternalPolicies() {
        return new ResponseEntity<>(policyRepository.findAll(), HttpStatus.OK);
    }

    public void deleteAllPolicies() {
        policyRepository.deleteAll();
    }

    @Entity
    public static class Policy {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String policyNumber;
        private String policyHolderName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double premium;

        public boolean isValid() {
            return startDate.isBefore(endDate);
        }
    }

    @Repository
    public interface PolicyRepository extends JpaRepository<Policy, Long> {

        List<Policy> findByPolicyHolderName(String name);
    }
}


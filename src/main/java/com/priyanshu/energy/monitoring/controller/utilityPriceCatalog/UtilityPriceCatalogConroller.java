package com.priyanshu.energy.monitoring.controller.utilityPriceCatalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyanshu.energy.monitoring.Service.utilityPriceCatalog.UtilityPriceCatalogService;
import com.priyanshu.energy.monitoring.dto.utilityPriceCatalogDTO.UtilityPriceCatalogDTO;
import com.priyanshu.energy.monitoring.dto.utilityPriceCatalogDTO.UtilityPriceCatalogUpdateDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/catalog")
@Slf4j
public class UtilityPriceCatalogConroller {
    @Autowired
    private UtilityPriceCatalogService utilityPriceCatalogService;

    @GetMapping("")
    public ResponseEntity<?> getCatalog() {
        try {
            List<UtilityPriceCatalogDTO> catalog = utilityPriceCatalogService.getAllCatalogEntries();

            return ResponseEntity.status(HttpStatus.OK).body(catalog);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @GetMapping("/get-catalog-entry/{id}")
    public ResponseEntity<?> getCatalogEntry(@PathVariable String id) {
        try {
            UtilityPriceCatalogDTO catalogEntry = utilityPriceCatalogService.getIndividualCatalogEntry(id);
            return ResponseEntity.status(HttpStatus.OK).body(catalogEntry);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PostMapping("/create-entry")
    public ResponseEntity<?> createEntry(@Valid @RequestBody UtilityPriceCatalogDTO catalogEntry) {
        try {
            UtilityPriceCatalogDTO createdEntry = utilityPriceCatalogService
                    .createUtilityPriceCatalogEntry(catalogEntry);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @DeleteMapping("/delete-entry/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable String id) {
        try {
            utilityPriceCatalogService.deleteUtilityPriceCatalogEntry(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PutMapping("/update-entry/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable String id,
            @Valid @RequestBody UtilityPriceCatalogUpdateDTO catalogUpdateEntry) {
        try {

            UtilityPriceCatalogDTO catalogEntry = utilityPriceCatalogService.updateUtilityPriceCatalogEntry(id,
                    catalogUpdateEntry);
            return ResponseEntity.status(HttpStatus.OK).body(catalogEntry);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}

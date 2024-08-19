package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.req.SkillDto;
import com.empmarket.employmentmarketplace.service.skill.SkillService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/skills")
    public ResponseEntity<?> createSkill(@RequestBody SkillDto skillDto) {
        if (skillDto.getName() != null && skillService.isNameExist(skillDto.getName())) {
            throw new EntityExistsException("Skill name = " + skillDto.getName() + " already existed");
        }
        return ResponseEntity.ok(skillService.createSkill(skillDto));
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<?> getSkillById(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(skillService.getSkillById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Skill not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot get skill by id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/skills/{pageNumber}")
    public ResponseEntity<?> getAllSkills(@PathVariable int pageNumber) {
        return ResponseEntity.ok(skillService.getAllSkills(pageNumber));
    }

    @GetMapping("/skills/search")
    public ResponseEntity<?> searchSkills(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String createdBy,
                                          @RequestParam(required = false, defaultValue = "AND") String predicateType,
                                          @RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "6") int pageSize,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String sortDir) {
        return ResponseEntity.ok(skillService.searchSkills(name, createdBy, predicateType,
                pageNumber, pageSize, sortBy, sortDir));
    }

    @PutMapping("skill/{id}")
    public ResponseEntity<?> updateSkill(@Valid @PathVariable Long id, @RequestBody SkillDto skillDto) {
        boolean success = skillService.updateSkill(id, skillDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body(skillDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/skill/{id}")
    public ResponseEntity<?> deleteSkill(@Valid @PathVariable Long id) {
        try {
            skillService.deleteSkill(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Skill not found",HttpStatus.NOT_FOUND);
        }
    }
}

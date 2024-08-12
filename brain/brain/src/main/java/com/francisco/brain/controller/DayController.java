package com.francisco.brain.controller;


import com.francisco.brain.dto.ActionDTO;
import com.francisco.brain.dto.DayDTO;
import com.francisco.brain.dto.NewActionDTO;
import com.francisco.brain.service.DayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/day/{publicId}")
public class DayController {

  private final DayService dayService;

  public DayController(DayService dayService) {
    this.dayService = dayService;
  }

  // Get DayEntities within a given range of dates
  @GetMapping()
  public ResponseEntity<List<DayDTO>> getDaysInRange(
          @PathVariable String publicId,
          @RequestParam LocalDate start,
          @RequestParam LocalDate end) {
    List<DayDTO> days = dayService.getDaysInRange(publicId, start, end);
    return new ResponseEntity<>(days, HttpStatus.OK);
  }

  // Add a new action to a specific day
  @PostMapping()
  public ResponseEntity<ActionDTO> addAction(
          @PathVariable String publicId,
          @RequestParam LocalDate date,
          @RequestBody NewActionDTO newActionDTO) {
    ActionDTO createdAction = dayService.addAction(publicId, date, newActionDTO);
    return new ResponseEntity<>(createdAction, HttpStatus.CREATED);
  }

  // Update an action
  @PutMapping("/{dayId}/{actionId}")
  public ResponseEntity<ActionDTO> updateAction(
          @PathVariable String publicId,
          @PathVariable Long dayId,
          @PathVariable Long actionId,
          @RequestBody ActionDTO actionDTO) {
    Optional<ActionDTO> updatedAction = dayService.updateAction(publicId, dayId, actionId, actionDTO);
    return updatedAction.map(action -> new ResponseEntity<>(action, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  // Delete an action
  @DeleteMapping("/{dayId}/{actionId}")
  public ResponseEntity<Void> deleteAction(
          @PathVariable String publicId,
          @PathVariable Long dayId,
          @PathVariable Long actionId) {
    boolean isDeleted = dayService.deleteAction(publicId, dayId, actionId);
    return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}

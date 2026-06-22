package com.amaze.controller;

import com.amaze.dto.AvailabilityReqDto;
import com.amaze.dto.AvailabilityResDto;
import com.amaze.enums.Day;
import com.amaze.service.AvailabilityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/avail")
@CrossOrigin(origins = "http://localhost:5173")
public class AvailabilityController {

    private AvailabilityService availabilityService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody AvailabilityReqDto dto, Principal principal){
        availabilityService.add(dto,principal.getName());

    }

    /* I am not checking the ownership of availability(whether it is that doctor's one of the availabiltiy id)
    because in frontend, I will give the update button against each row of that doctor's availabiltiy list.
     and id will be automatically send, no need of entering the id by doctor.  */

    @PutMapping("/update/{id}")
    public void update(@PathVariable int id, @Valid @RequestBody AvailabilityReqDto dto, Principal principal){
        availabilityService.update(id, dto, principal.getName());
    }

    // similarly no ownership checking as I am going to get the id automatically from list buttons
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        availabilityService.delete(id);
    }

    @GetMapping("/view-availabilities")
    public AvailabilityResDto viewAll(@RequestParam int page, @RequestParam int size, Principal principal){
        return availabilityService.viewAll(page, size, principal.getName());
    }
    @GetMapping("/view-availabilities/{docId}")
    public AvailabilityResDto viewAll(@PathVariable int docId, @RequestParam int page, @RequestParam int size){
        return availabilityService.viewAllByDoctorId(page, size, docId);
    }

    @GetMapping("/view-availabilities-by-day")
    public AvailabilityResDto viewAllByDay(@RequestParam int page, @RequestParam int size, @RequestParam Day day, Principal principal){
        return availabilityService.viewAllByDay(page, size,day, principal.getName());
    }
    @GetMapping("/view-availabilities-by-day/{docId}")
    public AvailabilityResDto viewAllByDay(@PathVariable int docId, @RequestParam int page, @RequestParam int size, @RequestParam Day day){
        return availabilityService.viewAllByDoctorIdAndByDay(page, size,day, docId);
    }

}

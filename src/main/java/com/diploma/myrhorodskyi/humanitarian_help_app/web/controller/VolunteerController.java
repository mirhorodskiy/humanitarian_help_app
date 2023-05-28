package com.diploma.myrhorodskyi.humanitarian_help_app.web.controller;

import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.VolunteerDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        exposedHeaders = "*",
        methods = {GET, POST, PUT, DELETE},
        maxAge = 3600)
@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }


    @GetMapping("/profile")
    public ResponseEntity<VolunteerDto> getVolunteer(HttpServletRequest request) {
        VolunteerDto volunteer = volunteerService.getVolunteerDto(request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<>(volunteer, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerDto> getVolunteerById(@PathVariable Long id) {
        return new ResponseEntity<>(volunteerService.getVolunteerById(id), HttpStatus.OK);
    }

    @PostMapping("/profile/update")
    public ResponseEntity<VolunteerDto> updateVolunteer(@RequestBody VolunteerDto volunteerDto,
                                                        HttpServletRequest request) {
        VolunteerDto volunteer = volunteerService.updateVolunteer(volunteerDto, request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<>(volunteer, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VolunteerDto>> getAllVolunteers() {
        return new ResponseEntity<>(volunteerService.getAllVolunteers(), HttpStatus.OK);
    }

    @GetMapping("/admin/unapproved")
    public ResponseEntity<List<VolunteerDto>> getUnapprovedVolunteers() {
        return new ResponseEntity<>(volunteerService.getUnapprovedVolunteers(), HttpStatus.OK);
    }

    @GetMapping("/admin/approve/{id}")
    public ResponseEntity<?> approveVolunteer(@PathVariable Long id) {
        volunteerService.approveVolunteer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
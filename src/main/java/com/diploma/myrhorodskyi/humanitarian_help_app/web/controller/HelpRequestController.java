package com.diploma.myrhorodskyi.humanitarian_help_app.web.controller;

import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.HelpRequestDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.error.IllegalAccessException;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.HelpRequestService;
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
@RequestMapping("/request")
public class HelpRequestController {

    private final HelpRequestService helpRequestService;

    @Autowired
    public HelpRequestController(HelpRequestService helpRequestService) {
        this.helpRequestService = helpRequestService;
    }

    @PostMapping("/create")
    public ResponseEntity<HelpRequestDto> createRequest(@RequestBody HelpRequestDto helpRequestDto,
                                                        HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return new ResponseEntity<>(helpRequestService.createRequest(helpRequestDto, token), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HelpRequestDto>> getAllRequests() {
        return new ResponseEntity<>(helpRequestService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping("/all/open")
    public ResponseEntity<List<HelpRequestDto>> getAllOpenRequests() {
        return new ResponseEntity<>(helpRequestService.getAllOpenRequests(), HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<List<HelpRequestDto>> getUserRequests(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return new ResponseEntity<>(helpRequestService.getUserRequests(token), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HelpRequestDto> getRequest(@PathVariable Long id) {
        return new ResponseEntity<>(helpRequestService.getHelpRequest(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            helpRequestService.deleteRequest(id, token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PostMapping("/change")
    public ResponseEntity<?> changeRequest(@RequestBody HelpRequestDto helpRequestDto,
                                           HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            return new ResponseEntity<>(helpRequestService.changeRequest(helpRequestDto, token), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveRequestByUser(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            helpRequestService.approveRequestByUser(id, token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/take/{id}") //for volunteers
    public ResponseEntity<?> takeRequestByVolunteer(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            helpRequestService.takeRequestByVolunteer(id, token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/admin/requests")
    public ResponseEntity<List<HelpRequestDto>> getProcessingRequests() {
        return new ResponseEntity<>(helpRequestService.getProcessingRequests(), HttpStatus.OK);
    }

    @PostMapping("/admin/approve/{id}")
    public ResponseEntity<?> approveProcessedRequest(@PathVariable Long id) {
        helpRequestService.approveProcessedRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

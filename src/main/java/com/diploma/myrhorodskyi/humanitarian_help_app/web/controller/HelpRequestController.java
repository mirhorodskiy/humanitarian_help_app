package com.diploma.myrhorodskyi.humanitarian_help_app.web.controller;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.RequestCategory;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.HelpRequestDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.error.IllegalAccessException;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.HelpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllRequests() {
//        Map<String,Object> result = new HashMap<>();
//        result.put("result", helpRequestService.getAllRequests());
//        result.put("status", HttpStatus.OK.toString());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllRequests(@RequestParam(required = false) String location,
                                            @RequestParam(required = false) RequestCategory category,
                                            @RequestParam(required = false) Boolean open) {
        Map<String,Object> result = new HashMap<>();
        result.put("result", helpRequestService.getAllRequests(location, category, open));
        result.put("status", HttpStatus.OK.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all/open")
    public ResponseEntity<?> getAllOpenRequests() {
        Map<String,Object> result = new HashMap<>();
        result.put("result", helpRequestService.getAllOpenRequests());
        result.put("status", HttpStatus.OK.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all/open-by")
    public ResponseEntity<?> getAllOpenRequestsByCategoryAndOrLocation(@RequestParam(required = false) String location,
                                                                       @RequestParam(required = false) RequestCategory category) {
        Map<String, Object> map = new HashMap<>();
        List<HelpRequestDto> requests = helpRequestService.getOpenRequestsByLocationAndOrCategory(location, category);

        map.put("result", requests);
        map.put("status", HttpStatus.OK.toString());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getUserRequests(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Map<String,Object> result = new HashMap<>();
        result.put("result", helpRequestService.getUserRequests(token));
        result.put("status", HttpStatus.OK.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    @GetMapping("/volunteers/my")
    public ResponseEntity<?> getVolunteersRequestsInProgress(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        List<HelpRequestDto> requests = helpRequestService.getVolunteersRequestsInProgress(token);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/admin/requests")
    public ResponseEntity<?> getProcessingRequests() {
        Map<String, Object> result = new HashMap<>();
        result.put("result", helpRequestService.getProcessingRequests());
//        return new ResponseEntity<>(volunteerService.getUnapprovedVolunteers(), HttpStatus.OK);
        return new ResponseEntity<>(result, HttpStatus.OK);
//        return new ResponseEntity<>(helpRequestService.getProcessingRequests(), HttpStatus.OK);
    }

    @PostMapping("/admin/approve/{id}")
    public ResponseEntity<?> approveProcessedRequest(@PathVariable Long id) {
        helpRequestService.approveProcessedRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/deny/{id}")
    public ResponseEntity<?> denyProcessedRequest(@PathVariable Long id) {
        helpRequestService.denyProcessedRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

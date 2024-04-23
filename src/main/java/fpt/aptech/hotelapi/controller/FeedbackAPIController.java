/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.hotelapi.controller;

import fpt.aptech.hotelapi.models.Feedback;
import fpt.aptech.hotelapi.service.FeedbackService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api/feedbackcontroller")
public class FeedbackAPIController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Feedback> findallcomment() {
        return feedbackService.FindAll();
    }

    @PostMapping("/createfeedback")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveFeedback(@RequestBody Feedback newFeedback) {
        feedbackService.save(newFeedback);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedback(@PathVariable int id) {
        feedbackService.deleteFeedback(id);
    }

}

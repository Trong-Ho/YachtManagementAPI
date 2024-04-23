/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.hotelapi.service;

import fpt.aptech.hotelapi.models.Feedback;
import fpt.aptech.hotelapi.repository.FeedbackRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author ASUS
 */
@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedRepo;

    
    public List<Feedback> FindAll(){
        return feedRepo.findAll();
    }
    
    
    public void save(Feedback newcomment){
        feedRepo.save(newcomment);
    }
    
    public void deleteFeedback(int id){
      Feedback feedback = feedRepo.findById(id).get();
      feedRepo.delete(feedback);
    }
}

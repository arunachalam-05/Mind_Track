package com.EEG_Project.Final.Year.Project.Controller;

import com.EEG_Project.Final.Year.Project.Model.Feedback;
import com.EEG_Project.Final.Year.Project.Repository.FeedBackRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private FeedBackRepo feedBackRepo;

    public FeedbackController(FeedBackRepo feedBackRepo){
        this.feedBackRepo = feedBackRepo;
    }

    @PostMapping("/save")
    public Feedback saveFeedback(@RequestBody Feedback feedback){
        return feedBackRepo.save(feedback);
    }
}

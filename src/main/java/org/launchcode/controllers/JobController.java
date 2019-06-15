package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam int id, Model model) {
        Job theJob = jobData.findById(id);
        model.addAttribute("theJob", theJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {


        if (errors.hasErrors()) {
            return "new-job";
        }


        Employer theEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location theLocation = jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency theSkill = jobData.getCoreCompetencies().findById(jobForm.getSkillId());
        PositionType thePosition = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

        Job newJob = new Job(jobForm.getName(), theEmployer, theLocation, thePosition, theSkill);

        jobData.add(newJob);

        model.addAttribute("theJob", newJob);



        return "job-detail";

    }
}

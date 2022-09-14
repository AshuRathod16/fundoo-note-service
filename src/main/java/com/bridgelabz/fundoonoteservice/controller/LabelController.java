package com.bridgelabz.fundoonoteservice.controller;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.service.ILabelService;
import com.bridgelabz.fundoonoteservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 13-09-2022
 * Purpose : controller for the Label Service
 */

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    ILabelService labelService;


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to fundoo notes project";
    }

    @PostMapping("/createLabel")
    public ResponseEntity<Response> createLabel(@Valid @RequestBody LabelDTO labelDTO, @RequestHeader String token) {
        LabelModel labelModel = labelService.createLabel(labelDTO, token);
        Response response = new Response(200, "label created successfully", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("updateLabel/{id}")
    public ResponseEntity<Response> updateLabel(@Valid @RequestBody LabelDTO labelDTO, @RequestHeader String token, @PathVariable Long labelId) {
        LabelModel labelModel = labelService.updateLabel(labelDTO, token, labelId);
        Response response = new Response(200, "Label updated successfully", labelModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/displayAllLabels")
    public ResponseEntity<Response> displayAllLabels(@RequestHeader String token) {
        List<LabelModel> labelModel = labelService.displayAllLabels(token);
        Response response = new Response(200, "all labels fetch successfully", labelModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/deleteLabel/{id}")
    public ResponseEntity<Response> deleteLabel(@RequestHeader String token, @PathVariable Long labelId) {
        Response labelModel = labelService.deleteLabel(token, labelId);
        Response response = new Response(200, "delete label successfully", labelModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.bridgelabz.fundoonoteservice.exception.CustomValidationException;
import com.bridgelabz.fundoonoteservice.exception.NoteException;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.model.NoteModel;
import com.bridgelabz.fundoonoteservice.repository.LabelRepository;
import com.bridgelabz.fundoonoteservice.repository.NoteRepository;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 13-09-2022
 * Purpose: Creating method to send Email
 */
@Service
public class LabelService implements ILabelService {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MailService mailService;

    @Override
    public LabelModel createLabel(LabelDTO labelDTO, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            LabelModel labelModel = new LabelModel(labelDTO);
            labelRepository.save(labelModel);
            String body = "Label added successfully with label name" + labelModel.getLabelName();
            String subject = "Label created successfully";
            return labelModel;
        }
        throw new NoteException(400, "Label not created ");
    }

    @Override
    public LabelModel updateLabel(LabelDTO labelDTO, String token, Long labelId) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
            if (isLabelPresent.isPresent()) {
                labelRepository.save(isLabelPresent.get());
                String body = "Label updated successfully with note id" + isLabelPresent.get().getLabelId();
                String subject = "Label updated successfully";
                mailService.send(isLabelPresent.get().getLabelName(), subject, body);
                return isLabelPresent.get();
            }
            throw new NoteException(400, "Label not found");
        }
        throw new NoteException(400, "Invalid Token");
    }


    @Override
    public List<LabelModel> displayAllLabels(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<LabelModel> readAllLabels = labelRepository.findAll();
            if (readAllLabels.size() > 0) {
                return readAllLabels;
            }
            throw new NoteException(400, "Label not found");
        }
        throw new NoteException(400, "Invalid Token");
    }

    @Override
    public Response deleteLabel(String token, Long labelId) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<LabelModel> isIdPresent = labelRepository.findById(labelId);
            if (isIdPresent.isPresent()) {
                return new Response(200, "Successfully", isIdPresent.get());
            }
            throw new NoteException(400, "Label not found");
        }
        throw new NoteException(400, "Invalid token");
    }
}

package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.util.Response;

import java.util.List;

public interface ILabelService {
    public LabelModel createLabel(LabelDTO labelDTO, String token);

    public List<LabelModel> displayAllLabels(String token);

    public Response deleteLabel(String token, Long labelId);

    public LabelModel updateLabel(LabelDTO labelDTO, String token, Long labelId);
}

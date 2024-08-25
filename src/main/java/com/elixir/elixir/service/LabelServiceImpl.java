package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Label;
import com.elixir.elixir.exceptions.LabelDuplicateException;
import com.elixir.elixir.exceptions.LabelNoSuchElementException;
import com.elixir.elixir.repository.LabelRepository;
import com.elixir.elixir.service.Interface.LabelService;

@Service
public class LabelServiceImpl implements LabelService{

    @Autowired
    private LabelRepository LabelRepository;

    public List<Label> getLabels(){
        return LabelRepository.findAll();
    }

    public Optional<Label> getLabelByName(String label_name) throws LabelNoSuchElementException {
        Optional<Label> label = LabelRepository.findByLabelName(label_name);
        if (label.isPresent()) {
            return label;
        } else {
            throw new LabelNoSuchElementException();
        }
    }

    public Label createLabel(String label_name) throws LabelDuplicateException{
        Optional<Label> labels = LabelRepository.findByLabelName(label_name);
        if(labels.isEmpty())
            return LabelRepository.save(new Label(label_name));
        throw new LabelDuplicateException();
        }


}


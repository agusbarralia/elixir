package com.elixir.elixir.service.Interface;

import java.util.List;
import java.util.Optional; 
import com.elixir.elixir.entity.Label;
import com.elixir.elixir.exceptions.LabelDuplicateException;
import com.elixir.elixir.exceptions.LabelNoSuchElementException;



public interface LabelService {

    public List<Label> getLabels();

    public Optional<Label> getLabelByName(String label_name) throws LabelNoSuchElementException;

    public Label createLabel(String label_name) throws LabelDuplicateException;
}





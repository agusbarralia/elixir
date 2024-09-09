package com.elixir.elixir.service.Interface;

import java.util.List;
import java.util.Optional; 
import com.elixir.elixir.entity.Variety;
import com.elixir.elixir.exceptions.VarietyDuplicateException;
import com.elixir.elixir.exceptions.VarietyNoSuchElementException;



public interface VarietyService {

    public List<Variety> getVarieties();

    public Optional<Variety> getVarietyByName(String variety_name) throws VarietyNoSuchElementException;

    public Variety createVariety(String variety_name) throws VarietyDuplicateException;

    public Variety deleteVariety(Long varietyId) throws VarietyNoSuchElementException;
}





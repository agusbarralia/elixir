package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Variety;
import com.elixir.elixir.exceptions.VarietyDuplicateException;
import com.elixir.elixir.exceptions.VarietyNoSuchElementException;
import com.elixir.elixir.repository.VarietyRepository;
import com.elixir.elixir.service.Interface.ProductService;
import com.elixir.elixir.service.Interface.VarietyService;

@Service
public class VarietyServiceImpl implements VarietyService{

    @Autowired
    private VarietyRepository VarietyRepository;

    @Autowired
    private ProductService productService;

    public List<Variety> getVarieties(){
        return VarietyRepository.findAllWithStateTrue();
    }

    public Optional<Variety> getVarietyByName(String variety_name) throws VarietyNoSuchElementException {
        Optional<Variety> variety = VarietyRepository.findVarietyByName(variety_name);
        if (variety.isPresent()) {
            return variety;
        } else {
            throw new VarietyNoSuchElementException();
        }
    }

    public Variety createVariety(String variety_name) throws VarietyDuplicateException{
        Optional<Variety> varieties = VarietyRepository.findVarietyByName(variety_name);
        if(varieties.isEmpty())
            return VarietyRepository.save(new Variety(variety_name));
        throw new VarietyDuplicateException();
        }

    public Variety deleteVariety(Long varietyId) throws VarietyNoSuchElementException {
        Optional<Variety> variety = VarietyRepository.findById(varietyId);
        if (variety.isPresent()) {
            variety.get().setState(false);
            VarietyRepository.save(variety.get());
            productService.deleteProductByVariety(varietyId); // Asegúrate de tener este método en ProductService
            return variety.get();
        } else {
            throw new VarietyNoSuchElementException();
        }
    }
}


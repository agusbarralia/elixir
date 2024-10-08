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
        Optional<Variety> variety = VarietyRepository.findByNameAndStateTrue(variety_name);
        if (variety.isPresent()) {
            return variety;
        } else {
            throw new VarietyNoSuchElementException();
        }
    }

    public Variety createVariety(String variety_name) throws VarietyDuplicateException {
        Optional<Variety> existingVariety = VarietyRepository.findByNameAndStateTrue(variety_name);
    
        if (existingVariety.isPresent()) {
            if (existingVariety.get().getState()) {
                throw new VarietyDuplicateException();
            } else {
                // Si la variedad existe pero su estado es false, se puede crear una nueva variedad
                return VarietyRepository.save(new Variety(variety_name));
            }
        } else {
            return VarietyRepository.save(new Variety(variety_name));
        }
    }

    public Variety deleteVariety(String variety_name) throws VarietyNoSuchElementException {
        Variety variety = VarietyRepository.findByNameAndStateTrue(variety_name)
                                        .orElseThrow(() -> new IllegalStateException("Subcategoria no encontrada"));
        variety.setState(false);
        VarietyRepository.save(variety);
        productService.deleteProductByVariety(variety.getVariety_id()); // Asegúrate de tener este método en ProductService
        return variety;

    }
}


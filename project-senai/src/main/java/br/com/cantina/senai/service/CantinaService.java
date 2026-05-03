package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.DTODetalhamentoCantina;
import br.com.cantina.senai.model.cantina.CantinaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CantinaService {

    private final CantinaRepository cantinaRepository;

    public CantinaService(CantinaRepository cantinaRepository) {
        this.cantinaRepository = cantinaRepository;
    }
}

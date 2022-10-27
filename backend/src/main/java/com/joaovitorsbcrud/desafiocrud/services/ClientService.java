package com.joaovitorsbcrud.desafiocrud.services;

import com.joaovitorsbcrud.desafiocrud.dtos.ClientDTO;
import com.joaovitorsbcrud.desafiocrud.entities.Client;
import com.joaovitorsbcrud.desafiocrud.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
        Page<Client> page = clientRepository.findAll(pageRequest);
        return page.map(ClientDTO::new);
    }
}

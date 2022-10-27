package com.joaovitorsbcrud.desafiocrud.services;

import com.joaovitorsbcrud.desafiocrud.dtos.ClientDTO;
import com.joaovitorsbcrud.desafiocrud.entities.Client;
import com.joaovitorsbcrud.desafiocrud.repositories.ClientRepository;
import com.joaovitorsbcrud.desafiocrud.services.exceptions.DatabaseException;
import com.joaovitorsbcrud.desafiocrud.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.ReadOnlyBufferException;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> page = clientRepository.findAll(pageRequest);
        return page.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);
        return obj.stream().map(ClientDTO::new).findFirst().orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDTO) {
        Client client = new Client();
        copyEntityToDTO(client, clientDTO);
        return new ClientDTO(clientRepository.save(client));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO){
        try{
            Client client = clientRepository.getReferenceById(id);
            copyEntityToDTO(client, clientDTO);
            return new ClientDTO(clientRepository.save(client));
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    @Transactional
    public void delete(Long id){
        try{
            clientRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id " + id + " not found");
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public void copyEntityToDTO(Client entity, ClientDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}

package lab.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import jakarta.ws.rs.WebApplicationException;
import lab.data.ComplaintRepository;
import lab.dto.ComplaintDTO;
import lab.entities.Complaint;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;
import java.lang.reflect.Type;

@ApplicationScoped
public class ComplaintService {

    @Inject
    private ComplaintRepository repository;

    @Inject
    private ModelMapper mapper;


    @Transactional
    public void create(ComplaintDTO dto) {
        repository.create(mapper.map(dto, Complaint.class));
    }

    @Transactional
    public void edit(ComplaintDTO dto) {
        repository.edit(mapper.map(dto, Complaint.class));
    }

    @Transactional
    public void remove(Long id) {
        Complaint entity = repository.find(id);
        if (entity == null) {
            throw new WebApplicationException("Complaint not found", 404);
        }
        repository.remove(entity);
    }


    @Transactional
    public ComplaintDTO find(Object id) {
        Complaint entity = repository.find(id);
        if (entity == null) {
            throw new WebApplicationException("Complaint not found", 404);
        }
        return mapper.map(entity, ComplaintDTO.class);
    }


    @Transactional
    public List<ComplaintDTO> findAll(String status) {
        List<Complaint> entityList = repository.findAll(status);
        Type listType = new TypeToken<List<ComplaintDTO>>() {}.getType();
        return mapper.map(entityList, listType);
    }

}

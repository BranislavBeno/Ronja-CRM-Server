package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.repository.RepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentativeService implements EntityService<Representative, RepresentativeDto> {

  private final RepresentativeRepository representativeRepository;

  @Autowired
  public RepresentativeService(RepresentativeRepository representativeRepository) {
    this.representativeRepository = representativeRepository;
  }

  @Override
  public List<Representative> findAll() {
    return representativeRepository.findAllByOrderByLastNameAsc();
  }

  @Override
  public Representative findById(int id) {
    return representativeRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Did not find representative id - " + id));
  }

  @Override
  public Representative save(RepresentativeDto dto) {
    return representativeRepository.save(convertToEntity(dto));
  }

  @Override
  public Representative update(RepresentativeDto dto) {
    Representative entity = findById(dto.getId());
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setStatus(dto.getStatus());
    entity.setRegion(dto.getRegion());
    entity.setNotice(dto.getNotice());
    entity.setPosition(dto.getPosition());
    entity.setLastVisit(dto.getLastVisit());
    entity.setScheduledVisit(dto.getScheduledVisit());
    return representativeRepository.save(entity);
  }

  @Override
  public void deleteById(int id) {
    representativeRepository.deleteById(id);
  }

  @Override
  public List<Representative> searchBy(String name) {
    List<Representative> results;

    if (name != null && (name.trim().length() > 0)) {
      results = representativeRepository.findByLastNameContainsAllIgnoreCase(name);
    } else {
      results = findAll();
    }

    return results;
  }

  private Representative convertToEntity(RepresentativeDto dto) {
    var representative = new Representative();
    representative.setId(dto.getId());
    representative.setFirstName(dto.getFirstName());
    representative.setLastName(dto.getLastName());
    representative.setStatus(dto.getStatus());
    representative.setRegion(dto.getRegion());
    representative.setNotice(dto.getNotice());
    representative.setPosition(dto.getPosition());
    representative.setLastVisit(dto.getLastVisit());
    representative.setScheduledVisit(dto.getScheduledVisit());

    return representative;
  }
}

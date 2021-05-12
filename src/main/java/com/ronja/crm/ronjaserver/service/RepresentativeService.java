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
    Representative entity = findById(dto.id());
    setEntity(dto, entity);
    return representativeRepository.save(entity);
  }

  private void setEntity(RepresentativeDto dto, Representative entity) {
    entity.setFirstName(dto.firstName());
    entity.setLastName(dto.lastName());
    entity.setStatus(dto.status());
    entity.setRegion(dto.region());
    entity.setNotice(dto.notice());
    entity.setPosition(dto.position());
    entity.setLastVisit(dto.lastVisit());
    entity.setScheduledVisit(dto.scheduledVisit());
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
    representative.setId(dto.id());
    setEntity(dto, representative);
    return representative;
  }
}

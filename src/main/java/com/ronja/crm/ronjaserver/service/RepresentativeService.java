package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.repository.RepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentativeService implements EntityService<Representative, RepresentativeDto> {

  private final RepresentativeRepository repository;

  @Autowired
  public RepresentativeService(RepresentativeRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Representative> findAll() {
    return repository.findAllByOrderByLastNameAsc();
  }

  @Override
  public Representative findById(int id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Did not find representative id - " + id));
  }

  @Override
  public boolean existsById(int id) {
    return repository.existsById(id);
  }

  @Override
  public Representative save(Representative entity) {
    return null;
  }

  @Override
  public Representative addDto(RepresentativeDto dto) {
    Representative representative = convertToEntity(dto);
    return repository.save(representative);
  }

  @Override
  public Representative updateDto(RepresentativeDto dto) {
    Representative entity = findById(dto.id());
    setEntity(dto, entity);
    return repository.save(entity);
  }

  @Override
  public void deleteById(int id) {
    repository.deleteById(id);
  }

  @Override
  public List<Representative> searchBy(String name) {
    List<Representative> results;

    if (name != null && (name.trim().length() > 0)) {
      results = repository.findByLastNameContainsAllIgnoreCase(name);
    } else {
      results = findAll();
    }

    return results;
  }

  private Representative convertToEntity(RepresentativeDto dto) {
    var representative = new Representative();
    setEntity(dto, representative);
    return representative;
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
    entity.setPhoneNumbers(dto.phoneNumbers());
    entity.setEmails(dto.emails());
    entity.setCustomer(null);
  }
}

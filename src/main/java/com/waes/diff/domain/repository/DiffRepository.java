package com.waes.diff.domain.repository;

import com.waes.diff.domain.model.Diff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends CrudRepository<Diff, String> {

}

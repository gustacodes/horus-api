package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.Firm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmRepository extends JpaRepository<Firm, Long> {

}

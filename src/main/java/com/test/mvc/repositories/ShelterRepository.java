package com.test.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.mvc.models.ShelterModel;

public interface ShelterRepository extends JpaRepository<ShelterModel, String> {

}
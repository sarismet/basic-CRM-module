package com.layermark.layermark_sarismet.repository;


import com.layermark.layermark_sarismet.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);

}
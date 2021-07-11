package com.layermark.layermark_sarismet.repository;


import com.layermark.layermark_sarismet.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

}
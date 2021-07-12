package com.layermark.layermark_sarismet.repository;


import com.layermark.layermark_sarismet.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "delete from CustomUser c where c.userID = ?1")
    void deleteByUserID(String userId);

}
package com.singlestore.singlestore_application.repository;

import com.singlestore.singlestore_application.model.FactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactRepository extends JpaRepository<FactModel, Integer> {

    // Native SQL query to find fact entries where waitover flag = 0 or 1;
    @Query(value = "SELECT * FROM RC_T_FACT where waitover_flag = :flag", nativeQuery = true)
    List<FactModel> findEntriesForWaitoverFlag(@Param("flag") String waitoverFlag);
}

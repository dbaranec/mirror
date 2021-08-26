package com.magic.mirror.repository;

import com.magic.mirror.model.entity.Qoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Qoute, Long> {

    boolean existsQouteByQouteText(String qouteText);
}

package com.oct.tools.wo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oct.tools.wo.entity.Currency;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long>{

	Optional<Currency> findByCode(String code);
	
}

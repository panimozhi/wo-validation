package com.oct.tools.wo.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oct.tools.wo.entity.WOHistory;

@Repository
public interface WOHistoryRepo extends PagingAndSortingRepository<WOHistory, Long>{


}

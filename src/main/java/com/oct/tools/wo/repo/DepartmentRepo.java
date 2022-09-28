package com.oct.tools.wo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oct.tools.wo.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long>{

	Optional<Department> findByDepartmentName(String departmentName);
}

package com.rdc.weflow_server.repository.project;

import com.rdc.weflow_server.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}

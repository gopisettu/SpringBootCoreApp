package com.springboot.ecom.repository;

import com.springboot.ecom.dto.response.ExecutiveResDtoByJobTitle;
import com.springboot.ecom.enums.JobTitle;
import com.springboot.ecom.model.Executive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutiveRepository extends JpaRepository<Executive,Long> {
    // select e from Executive e where e.jobTitle=?1
    public List<ExecutiveResDtoByJobTitle> getByJobTitle(JobTitle jobTitle);
}

package com.test.repository;

import com.test.entity.ClassOne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassOneRepository extends JpaRepository<ClassOne,Integer> {
}

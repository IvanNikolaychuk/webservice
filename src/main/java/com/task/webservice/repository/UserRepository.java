package com.task.webservice.repository;

import com.task.webservice.model.Employee;
import com.task.webservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}

package com.musala.ahmedTest.practicalTask.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musala.ahmedTest.practicalTask.models.Gateway;

//@Repository
public interface GatewayRepo extends JpaRepository<Gateway , String >  {

}

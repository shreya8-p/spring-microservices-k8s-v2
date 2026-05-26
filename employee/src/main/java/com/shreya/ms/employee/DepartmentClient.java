package com.shreya.ms.employee;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "http://department:8082")
public interface DepartmentClient {

    @GetExchange("/department/{id}")
    Department getDepartmentById(@org.springframework.web.bind.annotation.PathVariable String id);
}

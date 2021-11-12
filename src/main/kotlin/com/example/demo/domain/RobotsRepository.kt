package com.example.demo.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RobotsRepository : PagingAndSortingRepository<Robot, Long>

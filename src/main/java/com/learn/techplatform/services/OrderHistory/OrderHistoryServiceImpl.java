package com.learn.techplatform.services.OrderHistory;

import com.learn.techplatform.entities.OrderHistory;
import com.learn.techplatform.repositories.OrderHistoryRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderHistoryServiceImpl extends AbstractBaseService<OrderHistory, String> implements OrderHistoryService{
    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryServiceImpl(JpaRepository<OrderHistory, String> genericRepository) {
        super(genericRepository);
    }
}

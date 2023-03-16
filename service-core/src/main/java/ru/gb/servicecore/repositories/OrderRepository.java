package ru.gb.servicecore.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.servicecore.entities.Order;

import java.util.Collection;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Collection<Order> findByUsername(String username);
}

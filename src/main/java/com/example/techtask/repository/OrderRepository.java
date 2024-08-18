package com.example.techtask.repository;

import com.example.techtask.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = """
            SELECT * FROM orders o 
            WHERE o.quantity > 1 
            ORDER BY o.created_at DESC 
            LIMIT 1
            """,
            nativeQuery = true)
    Order findOrder();
   @Query(value = """
            SELECT o.id, o.product_name, o.price, o.quantity, o.user_id, o.created_at, o.order_status FROM orders o
            JOIN techtask.users u on u.id = o.user_id
            WHERE u.user_status = 'ACTIVE'
            ORDER BY o.created_at DESC
""",
           nativeQuery = true)
    List<Order> findOrders();
}

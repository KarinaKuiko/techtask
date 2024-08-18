package com.example.techtask.repository;

import com.example.techtask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = """
        SELECT u.id, u.email, u.user_status FROM users u
        JOIN (
            SELECT  SUM(price) AS total_price, o.user_id FROM orders o
            WHERE o.created_at BETWEEN '2003-01-01' AND '2003-12-31'
            GROUP BY user_id
            ORDER BY total_price DESC 
            LIMIT 1
        ) o on u.id = o.user_id
""",
    nativeQuery = true)
    User findUser(); //if can say price is total price and we don't need to multiply price on quantity

    @Query(value = """
        SELECT u.id, u.email, u.user_status FROM users u
        JOIN techtask.orders o on u.id = o.user_id
        WHERE o.order_status='PAID' AND o.created_at BETWEEN '2010-01-01' AND '2010-12-31'
""",
            nativeQuery = true)
    List<User> findUsers(); //if "paid" orders are orders with status "PAID" and only this status. Maybe status "DELIVERED" also counts and it needs to consider in condition
}

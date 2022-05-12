package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    List<Order> getOrdersByDate(String date, List<Order> ordersToSearchList);
}

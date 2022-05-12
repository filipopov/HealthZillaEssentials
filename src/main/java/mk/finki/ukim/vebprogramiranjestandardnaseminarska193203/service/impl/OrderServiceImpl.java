package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Order;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.ShoppingCart;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.OrderRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.ProductRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order save(Order order) {
        List<Product> products = order.getShoppingCart().getProducts();
        for (Product product: products){
            product.setAvailablePieces(product.getAvailablePieces()-1);
            this.productRepository.save(product);
        }
        return this.orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByDate(String date, List<Order> ordersToSearchList){
        List<Order> resultList = new ArrayList<>();
        String[] parts = date.split("-");
        for(Order order: ordersToSearchList) {
            LocalDateTime orderDate = order.getShoppingCart().getDateCreated();
            int orderDay = orderDate.getDayOfMonth();
            int orderMonth = orderDate.getMonth().getValue();
            int orderYear = orderDate.getYear();

            int orderDayFromDate = Integer.parseInt(parts[2]);
            int orderMonthFromDate = Integer.parseInt(parts[1]);
            int orderYearFromDate = Integer.parseInt(parts[0]);

            if (orderYear >= orderYearFromDate)
                if (orderMonth >= orderMonthFromDate)
                    if (orderDay >= orderDayFromDate)
                        resultList.add(order);
        }
        return resultList;
    }
}

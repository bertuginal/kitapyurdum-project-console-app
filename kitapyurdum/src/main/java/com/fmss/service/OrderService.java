package com.fmss.service;

import com.fmss.model.Customer;
import com.fmss.model.Order;
import com.fmss.model.Product;
import com.fmss.model.Publisher;
import com.fmss.repository.CustomerRepository;
import com.fmss.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.*;

public class OrderService {

    private static final Integer ORDER_CODE_LENGTH = 10;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Set<String> generatedCodes = new HashSet<>();
    private static final Random random = new Random();



    private OrderRepository orderRepository = new OrderRepository();
    private CustomerRepository customerRepository= new CustomerRepository();

    public Order add(List<Product> products, String orderCode, Customer customer) {
        Order order = new Order(products, orderCode, customer);
        System.out.println("Random order code generated -> " + orderCode);
        orderRepository.add(order);
        return order;
    }

    public List<Order> getAll() { return orderRepository.getAll(); }


    //--generate order code
    public static String generateOrderCode() {
        String orderCode;
        do {
            orderCode = generateRandomCode();
        } while (generatedCodes.contains(orderCode));
        generatedCodes.add(orderCode);
        return orderCode;
    }

    private static String generateRandomCode() {
        StringBuilder sb = new StringBuilder(ORDER_CODE_LENGTH);
        for (int i = 0; i < ORDER_CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
    //--end generate order code

    public List<Order> getOrdersByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(Customer::getOrderList)
                .orElse(Collections.emptyList());
    }

}

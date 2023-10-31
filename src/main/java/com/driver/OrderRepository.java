package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class OrderRepository {
    private final Map<String, Order> allOrders;
    private final Map<String, String> ordersPartner;
    private final Map<String, DeliveryPartner> allPartner;
    private final Map<String, List<String>> partnersOrders;

    public OrderRepository() {

        allOrders = new HashMap<>();
        ordersPartner = new HashMap<>();
        allPartner = new HashMap<>();
        partnersOrders = new HashMap<>();

    }

    public void addOrder(Order order) {
        allOrders.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        allPartner.put(partnerId, new DeliveryPartner(partnerId));
        partnersOrders.put(partnerId, new ArrayList<>());
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        ordersPartner.put(orderId, partnerId);
        partnersOrders.get(partnerId).add(orderId);
    }

    public Order getOrderById(String orderId) {
        return allOrders.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return allPartner.get(partnerId);
    }

    public Integer  getOrderCountByPartnerId(String partnerId) {
        if(allPartner.get(partnerId) == null) return null;
        return allPartner.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnersOrders.get(partnerId);
    }

    public Map<String, Order> getAllOrders() {
        return allOrders;
    }

    public Map<String, String> getOrdersPartner() {
        return ordersPartner;
    }

    public Map<String, DeliveryPartner> getAllPartner() {
        return allPartner;
    }
}

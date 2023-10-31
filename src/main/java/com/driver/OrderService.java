package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {

//    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId, partnerId);
        DeliveryPartner deliveryPartner = orderRepository.getPartnerById(partnerId);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        Map<String, Order> allOrders = orderRepository.getAllOrders();
        return new ArrayList<>(allOrders.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        Map<String, Order> allOrders = orderRepository.getAllOrders();
        Map<String, String> ordersPartner = orderRepository.getOrdersPartner();
        return allOrders.size() - ordersPartner.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] hourMinute = time.split(":");
        int currTime = Integer.parseInt(hourMinute[0])*60 + Integer.parseInt(hourMinute[1]);

        int count = 0;
        List<String> partnerOrders = orderRepository.getOrdersByPartnerId(partnerId);
        for(String orderId: partnerOrders){
            Order order = orderRepository.getOrderById(orderId);
            if(order.getDeliveryTime() > currTime) count++;
        }

        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> partnerOrders = orderRepository.getOrdersByPartnerId(partnerId);

        int lastTime = 0;
        for(String orderId: partnerOrders){
            Order order = orderRepository.getOrderById(orderId);
            lastTime = Math.max(order.getDeliveryTime(), lastTime);
        }

        int hour = lastTime/60;
        int minute = lastTime%60;

        return hour+":"+minute;

    }

    public void deletePartnerById(String partnerId) {
        List<String> partnerOrders = orderRepository.getOrdersByPartnerId(partnerId);
        orderRepository.getAllPartner().remove(partnerId);

        for(String orderId: partnerOrders){
            orderRepository.getOrdersPartner().remove(orderId);
        }

    }

    public void deleteOrderById(String orderId) {
        orderRepository.getAllOrders().remove(orderId);
        orderRepository.getOrdersPartner().remove(orderId);
    }
}

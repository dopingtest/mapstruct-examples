package com.baek.mapstruct.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class OrderInfo {

    @Getter
    @Builder
    public static class Main {
        private final Long orderId;
        private final String orderTokens;
        private final Long userId;
        private final String payMethod;
        private final Long totalAmount;
        private final DeliveryInfo deliveryInfo;
        private final LocalDateTime orderedAt;
        private final String status;
        private final String statusDescription;
        private final List<String> address;
    }

    @Getter
    @Builder
    public static class DeliveryInfo {
        private final String receiverName;
        private final String receiverPhone;
        private final String receiverZipcode;
        private final String receiverAddress1;
        private final String receiverAddress2;
        private final String etcMessage;
    }
}
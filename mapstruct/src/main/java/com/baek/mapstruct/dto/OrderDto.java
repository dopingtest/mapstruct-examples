package com.baek.mapstruct.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class OrderDto {

    @Getter
    @Builder
    public static class Main {
        private final String orderToken;
        private final Long userId;
        private final String payMethod;
        private final Long totalAmount;
        private final DeliveryInfo deliveryInfo;
        private final String orderedAt;
        private final String status;
        private final String statusDescription;
        private final List<String> address;
        private final String orderNumber;
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

    @Getter
    @Builder
    public static class OrderResponse {
        private String orderName;
        private String createdAt;
    }
}

package com.baek.mapstruct;

import com.baek.mapstruct.dto.OrderDto;
import com.baek.mapstruct.entity.OrderEntity;
import com.baek.mapstruct.entity.OrderInfo;
import com.baek.mapstruct.mapper.OrderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest
class MappingTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Case1. ReportingPolicy.ERROR 이면서 Target 에 있는 속성이 Source 에 모두 다 있는 경우 Error 발생 안함
     *    (Target 에 없는 속성이 Source 에 있어도 상관 없음)
     *
     * Case2. ReportingPolicy.ERROR 이면서
     *    1. Target 에 있는 속성이 Source 에 일부 없는 경우
     *    2. Target 에 있는 속성 일부와 Source 에 있는 속성 일부가 이름이 다른 경우
     *    - [No error] 전에 컴파일되어있던 소스를 사용하는 경우 "orderNumber":null 이런식으로 null 로 할당되어 변환됨
     *    - [Compile error] 다시 컴파일 할 경우 Unmapped target properties 라고 compile error 발생
     *
     * Case3. ReportingPolicy.IGNORE 이면서
     *    1. Target 에 있는 속성이 Source 에 일부 없는 경우
     *    2. Target 에 있는 속성 일부와 Source 에 있는 속성 일부가 이름이 다른 경우
     *    - Error 발생 안하고 null 로 바인딩 됨
     *    - {"orderToken":null,"userId":2,"payMethod":"Card","totalAmount":10000,"deliveryInfo":{"receiverName":"baek","receiverPhone":"01012341234","receiverZipcode":"123","receiverAddress1":"신림동","receiverAddress2":"202호","etcMessage":"배송 전 미리 연락 바랍니다."},"orderedAt":"2022-12-04 17:41:21","status":"status","statusDescription":"status123","address":["ABC","EFG"],"orderNumber":null}
     *
     * 변환 대상인 Entity, DTO 의 속성이 추가/변경/삭제가 일어나는 경우 build 를 다시해서 Mapper 를 생성해줘야 함
     * - Converting Test Code 가 있으면 좋을 듯
     * - 실무에서는 IGNORE 를 사용하면서, 원하는 바인딩 규칙을 테스트 코드로 문서화
     *   - IGNORE 쓰면서 target 과 source 의 속성이 다르거나, 혹은 source 에는 없는데 target 에 있는 경우 코드로 문서화 변환 규칙을 문서화 하지 않으면,
     *   이 속성은 뭐지(?) 이럴 것 같음
     * - 테스트 코드로 문서화하는게 싫으면 Mapping 클래스에서 일일이 규칙을 지정
     */
    @Test
    void test() throws JsonProcessingException {
        List<String> address = new ArrayList<>();
        address.add("ABC");
        address.add("EFG");

        OrderInfo.DeliveryInfo deliveryInfo = OrderInfo.DeliveryInfo.builder()
                .receiverName("baek")
                .receiverPhone("01012341234")
                .receiverZipcode("123")
                .receiverAddress1("신림동")
                .receiverAddress2("202호")
                .etcMessage("배송 전 미리 연락 바랍니다.")
                .build();

        OrderInfo.Main info = OrderInfo.Main.builder()
                .orderId(1L)
                .orderTokens("token")
                .userId(2L)
                .payMethod("Card")
                .totalAmount(10000L)
                .orderedAt(LocalDateTime.now())
                .status("status")
                .statusDescription("status123")
                .address(address)
                .deliveryInfo(deliveryInfo)
                .build();

        OrderDto.Main dto = orderMapper.of(info);
        log.info("Parsed Dto: {}", objectMapper.writeValueAsString(dto));
    }

    /*
         {
          "orderToken": "token",
          "userId": 2,
          "payMethod": "Card",
          "totalAmount": 10000,
          "deliveryInfo": {
            "receiverName": "baek",
            "receiverPhone": "01012341234",
            "receiverZipcode": "123",
            "receiverAddress1": "신림동",
            "receiverAddress2": "202호",
            "etcMessage": "배송 전 미리 연락 바랍니다."
          },
          "orderedAt": "2022-12-04 17:29:17",
          "status": "status",
          "statusDescription": "status123",
          "address": [
            "ABC",
            "EFG"
          ]
        }
     */

    /**
     * 상속 관게에서는 부모, 자식 클래스에서 @Builder 대신 @SuperBuilder 를 사용해야 함
     */
    @Test
    void 상속_테스트() throws JsonProcessingException {
        OrderEntity entity = OrderEntity.builder().
                orderName("abc")
                .createdAt(LocalDateTime.now())
                .build();

        OrderDto.OrderResponse response = orderMapper.of(entity);
        log.info("Parsed Dto: {}", objectMapper.writeValueAsString(response));
    }

    /*
      {"orderName":"abc","createdAt":"2022-12-04T18:06:18.853126"}
     */

    @Test
    void convertToList() throws JsonProcessingException {
        List<String> address = new ArrayList<>();
        address.add("ABC");
        address.add("EFG");

        List<String> address2 = new ArrayList<>();
        address.add("abc");
        address.add("efg");

        OrderInfo.DeliveryInfo deliveryInfo = OrderInfo.DeliveryInfo.builder()
                .receiverName("baek")
                .receiverPhone("01012341234")
                .receiverZipcode("123")
                .receiverAddress1("신림동")
                .receiverAddress2("202호")
                .etcMessage("배송 전 미리 연락 바랍니다.")
                .build();

        OrderInfo.Main info = OrderInfo.Main.builder()
                .orderId(1L)
                .orderTokens("token")
                .userId(2L)
                .payMethod("Card")
                .totalAmount(10000L)
                .orderedAt(LocalDateTime.now())
                .status("status")
                .statusDescription("status123")
                .address(address)
                .deliveryInfo(deliveryInfo)
                .build();

        OrderInfo.Main info2 = OrderInfo.Main.builder()
                .orderId(1L)
                .orderTokens("token-2")
                .userId(2L)
                .payMethod("Card-2")
                .totalAmount(20000L)
                .orderedAt(LocalDateTime.now())
                .status("status-2")
                .statusDescription("status456")
                .address(address2)
                .deliveryInfo(deliveryInfo)
                .build();

        List<OrderInfo.Main> list = new ArrayList<>();
        list.add(info);
        list.add(info2);

        List<OrderDto.Main> result = orderMapper.of(list);
        log.info("Parsed: {}", objectMapper.writeValueAsString(result));
    }
}

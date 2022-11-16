package uk.ac.newcastle.enterprisemiddleware.contact;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import uk.ac.newcastle.enterprisemiddleware.conroller.HotelController;
import uk.ac.newcastle.enterprisemiddleware.vobject.HotelVO;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestHTTPEndpoint(HotelController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HotelTest {

    private static HotelVO hotelVO;

    @BeforeAll
    static void setup() {
        hotelVO = new HotelVO(0L, "Hotel", "13212148985", "747200");
    }

    @org.junit.jupiter.api.Test
    @Order(1)
    public void queryHotels() {
        Response response = when().
                get().
                then().
                statusCode(200).
                extract().response();

        HotelVO[] hotelVOS =  response.body().as(HotelVO[].class);
        System.out.println(hotelVOS[0]);
    }
}

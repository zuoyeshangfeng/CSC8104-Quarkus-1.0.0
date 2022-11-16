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
import uk.ac.newcastle.enterprisemiddleware.conroller.TaxiController;
import uk.ac.newcastle.enterprisemiddleware.vobject.HotelVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.TaxiVO;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestHTTPEndpoint(TaxiController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaxiTest {
    private static TaxiVO taxiVO;


    @BeforeAll
    static void setup() {
        taxiVO = new TaxiVO(0L, "TS123455", 20L);
    }

    @org.junit.jupiter.api.Test
    @Order(1)
    public void queryTaxis() {
        Response response = when().
                get().
                then().
                statusCode(200).
                extract().response();

        TaxiVO[] taxiVOS =  response.body().as(TaxiVO[].class);
        System.out.println(taxiVOS[0]);
    }
}

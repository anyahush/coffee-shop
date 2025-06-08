package com.mcdonald.coffeeshop;

import com.mcdonald.coffeeshop.model.SupplyItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @Autowired TestRestTemplate rest;

    @Test
    void createAndFetch() {
        var item = new SupplyItem(null, "Beans","Coffee",15,5,null);
        ResponseEntity<SupplyItem> post = rest.postForEntity(
                "/api/supplies", item, SupplyItem.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertNotNull(post.getBody());
        Long id = post.getBody().getId();

        ResponseEntity<SupplyItem> get = rest.getForEntity(
                "/api/supplies/" + id, SupplyItem.class);
        assertThat(get.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(get.getBody());
        assertThat(get.getBody().getName()).isEqualTo("Beans");
    }
}

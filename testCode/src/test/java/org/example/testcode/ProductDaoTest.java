package org.example.testcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(AppConfig.class)
public class ProductDaoTest {
    @Autowired
    private ProductDao dao;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
    }

    @Test
    void add() {
        // given
        assertEquals(0, dao.getCount());

        // when
        dao.add(newProduct("a", "b", 100));

        // then
        assertEquals(1, dao.getCount());
    }

    @Test
    void get() {
        // given
        Product g = newProduct("a", "b", 100);
        dao.add(g);

        // when
        Product w = dao.get("a");

        // then
        assertEquals(g.getName(), w.getName());
        assertEquals(g.getPrice(), w.getPrice());
    }

    @Test
    void add_중복_id_예외() {
        Product p = newProduct("a", "b", 100);
        dao.add(p);

        Executable action = new Executable() {
            @Override
            public void execute() throws Throwable {
                dao.add(p);
            }
        };

        assertThrows(IllegalStateException.class, action);
    }

    @Test
    void get_없는_id_예외() {
        Executable action = new Executable() {
            @Override
            public void execute() throws Throwable {
                dao.get("c");
            }
        };

        assertThrows(NoSuchElementException.class, action);
    }

    @Disabled
    @Test
    void 일부러_실패하는_테스트() {
        dao.add(newProduct("a", "b", 100));
        assertEquals(2, dao.getCount());
    }

    private Product newProduct(String id, String name, int price) {
        Product product = new Product();

        product.setId(id);
        product.setName(name);
        product.setPrice(price);

        return product;
    }


}

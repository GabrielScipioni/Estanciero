package ar.edu.utn.frc.tup.lciii.Entities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProvinceEntityTest {

    private ProvinceEntity province;

    @BeforeEach
    public void setUp() {
        province = new ProvinceEntity();
    }

    @Test
    public void testProvinceEntityFields() {
        assertNull(province.getProvinceId(), "ProvinceId should be null initially");
        assertNull(province.getDescription(), "Description should be null initially");

        Long id = 1L;
        String description = "Buenos Aires";

        province.setProvinceId(id);
        province.setDescription(description);

        assertEquals(id, province.getProvinceId(), "ProvinceId getter/setter test failed");
        assertEquals(description, province.getDescription(), "Description getter/setter test failed");
    }

    @Test
    public void testProvinceEntityConstructor() {
        Long id = 1L;
        String description = "Cordoba";

        ProvinceEntity province = new ProvinceEntity(id, description);

        assertEquals(id, province.getProvinceId(), "Constructor ProvinceId initialization failed");
        assertEquals(description, province.getDescription(), "Constructor Description initialization failed");
    }

}

package ar.edu.utn.frc.tup.lciii.Services.DB.Or;

import ar.edu.utn.frc.tup.lciii.Entities.ProvinceEntity;
import ar.edu.utn.frc.tup.lciii.Entities.SquareEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.ProvinceORService;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.SquareORService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProvinceORServiceTest {

    private static ProvinceORService provinceORService;

    @BeforeAll
    static void setUp() {
        provinceORService = new ProvinceORService();
    }

    @Test
    void testGetById() {
        ProvinceEntity retrievedprovince = provinceORService.getById(1L);

        assertNotNull(retrievedprovince);
        assertEquals(1L, retrievedprovince.getProvinceId());
        assertEquals("BUENOS_AIRES", retrievedprovince.getDescription());

    }

    @Test
    void testGetAll() {
        List<ProvinceEntity> provinceEntityList = provinceORService.getAll();

        assertNotNull(provinceEntityList);
        assertFalse(provinceEntityList.isEmpty());
        assertEquals(8, provinceEntityList.size());

        ProvinceEntity Province1 = provinceEntityList.get(0);
        assertEquals(1L, Province1.getProvinceId());
        assertEquals("BUENOS_AIRES", Province1.getDescription());

        ProvinceEntity province8 = provinceEntityList.get(7);
        assertEquals(8L, province8.getProvinceId());
        assertEquals("RIO_NEGRO", province8.getDescription());

    }
}

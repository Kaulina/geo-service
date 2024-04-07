package ru.netology.geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    @BeforeAll
    static void setUp() {
        System.out.println("Начало теста");
    }

    @AfterEach
    void tearDown() {
        System.out.println("\nОкончание теста");
    }


    @DisplayName("Тест должен выдать сша")
    @Test
    void byIpTest() {
        final String ip = GeoServiceImpl.NEW_YORK_IP;
        final Country country = Country.USA;
        final String street = " 10th Avenue";
        final String city = "New York";


        GeoServiceImpl geoService = new GeoServiceImpl();
        final Location actual = geoService.byIp(ip);

        assertEquals(country, actual.getCountry());
        assertEquals(street, actual.getStreet());
        assertEquals(city, actual.getCity());
    }

    @DisplayName("Тест на неизвестные координаты")
    @Test
    void byIpTestNull() {
        final String ip = "111.";

        Location expected = null;

        GeoServiceImpl geoService = new GeoServiceImpl();
        final Location actual = geoService.byIp(ip);

        assertEquals(expected, actual);
    }


    @DisplayName("Тест на разные - ip России")
    @ValueSource(strings = {GeoServiceImpl.MOSCOW_IP, "172."})
    @ParameterizedTest
    void byIpTestParams(String ip) {
        final Country country = Country.RUSSIA;
        final String street = "Lenina";

        GeoServiceImpl geoService = new GeoServiceImpl();
        final Location actual = geoService.byIp(ip);

        assertEquals(country, actual.getCountry());
    }

    @DisplayName("Тест на исключение")
    @Test
    void byCoordinatesException() {
        final int latitude = 0;
        final int longitude = 0;

        GeoServiceImpl geoService = new GeoServiceImpl();

        assertThrows(RuntimeException.class, () -> geoService.byCoordinates(latitude, longitude));
    }


    @DisplayName("Тест на исключение-текстОшибки")
    @Test
    void byCoordinatesExceptionText() {

        final int latitude = 0;
        final int longitude = 0;

        GeoServiceImpl geoService = new GeoServiceImpl();

        RuntimeException actual = assertThrows(RuntimeException.class,
                () -> {
                    geoService.byCoordinates(latitude, longitude);
                });

        assertEquals("Not implemented", actual.getMessage());
    }
}
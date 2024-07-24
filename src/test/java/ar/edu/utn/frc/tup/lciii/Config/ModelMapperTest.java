package ar.edu.utn.frc.tup.lciii.Config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import static org.junit.jupiter.api.Assertions.*;

class ModelMapperTest {

    @Test
    void testModelMapperConfiguration() {
        ModelMapper modelMapper = ModelMapperConfig.getModelMapper();
        Configuration configuration = modelMapper.getConfiguration();

        // Verifica que la estrategia de coincidencia sea ESTRICTA
        assertEquals(MatchingStrategies.STRICT, configuration.getMatchingStrategy());

        // Verifica que el modelMapper no sea nulo
        assertNotNull(modelMapper);
    }

    @Test
    void testChangeMatchingStrategy() {
        ModelMapper modelMapper = ModelMapperConfig.getModelMapper();
        Configuration configuration = modelMapper.getConfiguration();

        // Cambia la estrategia de coincidencia a STANDARD
        configuration.setMatchingStrategy(MatchingStrategies.STANDARD);
        assertEquals(MatchingStrategies.STANDARD, configuration.getMatchingStrategy());

        // Cambia la estrategia de coincidencia a LOOSE
        configuration.setMatchingStrategy(MatchingStrategies.LOOSE);
        assertEquals(MatchingStrategies.LOOSE, configuration.getMatchingStrategy());

        // Restablece la estrategia de coincidencia a STRICT
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);
        assertEquals(MatchingStrategies.STRICT, configuration.getMatchingStrategy());
    }

    @Test
    void testSingletonModelMapper() {
        ModelMapper modelMapper1 = ModelMapperConfig.getModelMapper();
        ModelMapper modelMapper2 = ModelMapperConfig.getModelMapper();

        // Verifica que ambas referencias sean iguales, asegurando que es un singleton
        assertSame(modelMapper1, modelMapper2);
    }

    @Test
    void testDefaultConfiguration() {
        ModelMapper modelMapper = ModelMapperConfig.getModelMapper();
        Configuration configuration = modelMapper.getConfiguration();

        // Verifica la configuración predeterminada
        assertEquals(MatchingStrategies.STRICT, configuration.getMatchingStrategy());
        assertFalse(configuration.isFieldMatchingEnabled());
        assertFalse(configuration.isAmbiguityIgnored());
        assertTrue(configuration.isImplicitMappingEnabled());
    }
}

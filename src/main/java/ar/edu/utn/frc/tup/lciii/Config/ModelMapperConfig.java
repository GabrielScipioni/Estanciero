package ar.edu.utn.frc.tup.lciii.Config;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperConfig {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}

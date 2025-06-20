package lab.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.modelmapper.ModelMapper;

public class ModelMapperProducer {

    @Produces
    @ApplicationScoped
    public ModelMapper produceModelMapper() {
        return new ModelMapper();
    }
}

package de.atruvia.myweb.mywebapp.domain.mapper;


import de.atruvia.myweb.mywebapp.domain.model.Schwein;
import de.atruvia.myweb.mywebapp.repositories.entities.SchweinEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweinMapper {

    Schwein convert(SchweinEntity entity);
    SchweinEntity convert(Schwein schwein);
    Iterable<Schwein> convert(Iterable<SchweinEntity> entities);
}

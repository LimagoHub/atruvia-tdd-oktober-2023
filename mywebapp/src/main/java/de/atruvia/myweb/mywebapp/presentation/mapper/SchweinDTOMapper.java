package de.atruvia.myweb.mywebapp.presentation.mapper;

import de.atruvia.myweb.mywebapp.domain.model.Schwein;
import de.atruvia.myweb.mywebapp.presentation.dto.SchweinDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweinDTOMapper {

    SchweinDTO convert(Schwein schwein);
    Schwein convert(SchweinDTO schwein);
    Iterable<SchweinDTO> convert(Iterable<Schwein> schweine);
}

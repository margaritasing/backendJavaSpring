package alkemy.challenge.Challenge.Alkemy.converter;

import alkemy.challenge.Challenge.Alkemy.dto.OrganizationDto;
import alkemy.challenge.Challenge.Alkemy.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationDtoConverter {

    public OrganizationDto convertEntityToGetOrganizationDto(Organization orgDto) {
        return OrganizationDto.builder()
                .id(orgDto.getId())
                .name(orgDto.getName())
                .phone(orgDto.getPhone())
                .adress(orgDto.getAddress())
                .image(orgDto.getImage())
                .linkdnUrl(orgDto.getLinkdnUrl())
                .facebookUrl(orgDto.getFacebookUrl())
                .instagramUrl(orgDto.getInstagramUrl())
                .build();
    }

}

package alkemy.challenge.Challenge.Alkemy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDto {

    @JsonIgnore
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String image;

    private int phone;

    private String adress;

    private String linkdnUrl;

    private String facebookUrl;

    private String instagramUrl;

}

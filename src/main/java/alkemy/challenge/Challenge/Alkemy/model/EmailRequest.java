package alkemy.challenge.Challenge.Alkemy.model;

import lombok.Data;

@Data
public class EmailRequest {

    //parámetros para el método de envio de mails


    private String to;
    private String subject;
    private String body;

}

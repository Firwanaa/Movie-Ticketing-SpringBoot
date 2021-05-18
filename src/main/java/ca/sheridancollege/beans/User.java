package ca.sheridancollege.beans;

import java.io.Serializable;
import lombok.*;

//Beans low: No Args constructor
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable { //Beans low: Implement Serializable
    private static final long serialVersionUID = -4820059646599452091L; //Beans low: All data are private
    private Long userId;
    private String userName;
    private String encryptedPassword;
}

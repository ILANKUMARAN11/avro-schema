package pojo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @ApiModelProperty(position = 1, required = true, value = "Employee ID", example = "111189")
    Long id;

    @ApiModelProperty(position = 2, required = true, value = "Full name", example = "ILAN KUMARAN")
    String fullName;

    @ApiModelProperty(position = 3, required = true, value = "e-mail ID", example = "ilankumarani@gmail.com")
    String emailId;

    @ApiModelProperty(position = 4, required = true, value = "phone no", example = "7539913146")
    String phoneNo;

    @ApiModelProperty(position = 5, required = true, value = "Address", example = "example to show")
    Address address;

}

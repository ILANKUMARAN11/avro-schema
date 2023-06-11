package pojo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
public class Address {

    @ApiModelProperty(position = 1, required = true, value = "Door no", example = "112/P6")
    String doorNo;

    @ApiModelProperty(position = 2, required = true, value = "Street name", example = "Durgai Nagar 4th street")
    String street;

    @ApiModelProperty(position = 3, required = true, value = "Pin Code", example = "606 001")
    String pinCode;
}

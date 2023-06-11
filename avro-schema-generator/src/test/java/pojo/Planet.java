package pojo;

import lombok.Getter;
import pojo.common.Details;

@Getter
public abstract class Planet {

    private String planetName;
    private String planetSize;
    private Long distanceFromSun;
    private Integer numberOfMoons;
    private Details details;
}

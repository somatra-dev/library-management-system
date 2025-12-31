package domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Member {

    private Integer id;

    private String memberName;

    private String email;

    private String phoneNumber;


}

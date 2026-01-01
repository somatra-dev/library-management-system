package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Integer id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    private LocalDate membershipDate;

    private Boolean isActive;

    private Boolean isMember;

    private List<Loan> loans = new ArrayList<>();

}

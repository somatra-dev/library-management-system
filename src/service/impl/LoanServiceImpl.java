package service.impl;

import domain.Loan;
import service.LoanService;

import java.util.ArrayList;
import java.util.List;

public class LoanServiceImpl {

    private final List<Loan> loans = new ArrayList<>();
    private Integer nextId = 1;

    private static final int LOAN_PERIOD_DAYS = 14;

}
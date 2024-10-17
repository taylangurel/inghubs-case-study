package com.inghubs.casestudy.controller;

import com.inghubs.casestudy.service.DepositWithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/money")
public class DepositWithdrawController {

    private final DepositWithdrawService depositWithdrawService;

    @PostMapping("/deposit")
    public String depositMoney(@RequestParam Double amount) {
        return depositWithdrawService.depositMoney(amount);
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@RequestParam Double amount) {
        return depositWithdrawService.withdrawMoney(amount);
    }
}


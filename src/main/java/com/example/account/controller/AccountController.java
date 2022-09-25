package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.AccountDto;
import com.example.account.dto.AccountInfo;
import com.example.account.dto.CreateAccount;
import com.example.account.dto.DeleteAccount;
import com.example.account.service.AccountService;
//import com.example.account.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    //private final RedisTestService redisTestService;

    @PostMapping("/account") // 생성
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request) {

        return CreateAccount.Response.from(
                accountService.createAccount(
                        request.getUserId(),
                        request.getInitialBalance())
        );
    }

    @DeleteMapping("/account") // 삭제
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request) {

        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber())
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountByUserId(
            @RequestParam("user_id") Long userId
    ) {
        return accountService.getAccountByUserId(userId)
                .stream().map(accountDto ->
                        AccountInfo.builder()
                                .accountNumber(accountDto.getAccountNumber())
                                .balance(accountDto.getBalance())
                                .build())
                .collect(Collectors.toList());
    }

/*    @GetMapping("/get-lock")
    public String getLock() {
        return redisTestService.getLock();

    }*/


    @GetMapping("/account/{id}") // 생성한 걸 가져오기
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
package com.mindhub.homebanking.dtos;

public class loanApplicationDTO {
    private long id;
    private String name;
    private Double amount;
    private int payments;
    private String accountDestiny;

    public loanApplicationDTO() {
    }

    public loanApplicationDTO(long id, String name, Double amount, int payments, String accountDestiny) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.payments = payments;
        this.accountDestiny = accountDestiny;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(String accountDestiny) {
        this.accountDestiny = accountDestiny;
    }
}

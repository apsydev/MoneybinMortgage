package com.moneybin.mortgage.controller;

import com.moneybin.mortgage.dto.MortgageDto;
import com.moneybin.mortgage.service.MortgageCalculationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class MortgageBinWebController {

    @Autowired
    private MortgageCalculationFacade mortgageCalculationFacade;

    @GetMapping("/loans")
    public String showAllLoans(Model model) {
        model.addAttribute("loans", mortgageCalculationFacade.retrieveAllLoans());
        return "loan/find-all";
    }

    @GetMapping("/")
    public String startPage(Model model) {
        return "redirect:/loans";
    }

    @GetMapping("/add-loan")
    public String addNewLoan(Model model) {
        model.addAttribute("customers", mortgageCalculationFacade.retrieveAllCustomers());
        model.addAttribute("mortgageLoan", new MortgageDto());
        return "loan/add-new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        MortgageDto loanDto = mortgageCalculationFacade.getMortgageLoanById(id);

        model.addAttribute("loan", loanDto);
        return "loan/update";
    }

    @PostMapping("/update/{id}")
    public String updateLoan(@PathVariable("id") long id, MortgageDto loan,
                             BindingResult result, Model model) {
        mortgageCalculationFacade.updateLoan(id, loan);
        return "redirect:/";
    }

    @PostMapping("/save-loan")
    public String saveNewLoan(MortgageDto loan, BindingResult result, Model model) {
        mortgageCalculationFacade.saveNewLoan(loan);
        return "redirect:/loans";
    }

    @PostMapping("/calculate-instalment")
    public String calculateInstalment(MortgageDto loan, BindingResult result, Model model) {
        List<MortgageDto> mortgageDtos = mortgageCalculationFacade.calculateMonthlyInstalment(mortgageCalculationFacade.retrieveAllLoans());
        mortgageCalculationFacade.saveMortgageLoans(mortgageDtos);
        return "redirect:/loans";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable("id") long id, Model model) {
        mortgageCalculationFacade.deleteMortgageLoan(id);
        return "redirect:/";
    }

}
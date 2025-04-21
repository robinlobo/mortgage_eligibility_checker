package com.robinlobo.mortgage_eligibility_checker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robinlobo.mortgage_eligibility_checker.model.MortgageConsultation;
import com.robinlobo.mortgage_eligibility_checker.model.MortgageRate;
import com.robinlobo.mortgage_eligibility_checker.repo.MortgageRateRepo;
import com.robinlobo.mortgage_eligibility_checker.service.MortgageRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(MortgageRateController.class)
public class MortgageRateControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @MockitoBean
    private MortgageRateService mortgageRateService;

    @MockitoBean
    private MortgageRateRepo mortgageRateRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideMortgageConsulatationStream")
    void testAddAuthorWithDifferentRoles(MortgageConsultation mortgageConsultation, HttpStatus expectedStatus) throws Exception {

        MortgageRate mortgageRate = MortgageRate.builder()
                .id(1L)
                .maturityPeriod(10)
                .interestRate(6.5f)
                .build();

        Mockito.when(mortgageRateRepo.findByMaturityPeriod(Mockito.any(Integer.class))).thenReturn(List.of(mortgageRate));
    }

    private static Stream<Arguments> provideMortgageConsulatationStream() {
        return Stream.of(
                Arguments.of(new MortgageConsultation(1000L, 10, 8000L, 10000L), HttpStatus.OK)
        );
    }

}

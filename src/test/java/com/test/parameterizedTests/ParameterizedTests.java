package com.test.parameterizedTests;

import com.test.service.Calculator;
import com.test.service.RomanNumeral;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

public class ParameterizedTests {

    private static Calculator calculator = null;

    @BeforeAll
    public static void init(){
        calculator = new Calculator();
    }


    @ValueSource(strings = {"I", "X" , "C", "IX"})
    @ParameterizedTest
    void testRomanNumberExists(String romanNum){
        Assertions.assertTrue(RomanNumeral.contains(romanNum));
    }

    @NullAndEmptySource
    @ValueSource(strings = {" ", "", "\t", "\n", "  "})
    @ParameterizedTest
    void nullEmptyAndBlankStringTest(String valu){
        Assertions.assertTrue(valu == null || valu.trim().isEmpty());
    }

    @EnumSource(value = RomanNumeral.class, names = {"I", "V"})
    @ParameterizedTest
    void checkRomanWithEnum(RomanNumeral numeral){
        Assertions.assertTrue(RomanNumeral.contains(numeral.name()));
    }

    public static Stream<Arguments> multiplicationProvider(){
        return Stream.of(Arguments.of(5,6,30));
    }


    // csv source test
    @CsvSource({"10,5,2","101,5,20"})
    @ParameterizedTest
    void divisionTestWithCSV(int a , int b, int result){
        Assertions.assertEquals(result, calculator.division(a,b));
    }

    @CsvFileSource(resources = "/input.csv", delimiter = ';')
    @ParameterizedTest
    void multiplicationTestWithCSVfile(int a, int b, int result){
        Assertions.assertEquals(result, calculator.multiplication(a,b));
    }

    @ArgumentsSource(MyArgumentsProvider.class)
    @ParameterizedTest
    void romanNumberTestWithArgumentProvider(String input){
        Assertions.assertTrue(RomanNumeral.contains(input));
    }
}

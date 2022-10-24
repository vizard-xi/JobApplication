package com.example.application.unit.generator;

import com.example.application.generator.JobGenerator;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.is;


@SpringBootTest
public class JobGeneratorTest {

    private JobGenerator jobGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jobGenerator = Mockito.spy(new JobGenerator(new StringBuilder()));
    }

    @AfterEach
    public void tearDown() {
        jobGenerator = null;
    }

    @Test
    public void testGeneratePossibleCharacterCombinationWithActualValues() {
        String expected = "abc\n" +
                "acb\n" +
                "bac\n" +
                "bca\n" +
                "cab\n" +
                "cba\n";

        jobGenerator.generatePossibleCharacterCombination("abc");
        String actual = jobGenerator.generatedCharacters().toString();

        MatcherAssert.assertThat(expected, is(actual));
    }

    @Test
    public void testGeneratePossibleCharacterCombinationWithEmptyValues() {
        String expected = "\n";

        jobGenerator.generatePossibleCharacterCombination("");
        String actual = jobGenerator.generatedCharacters().toString();

        MatcherAssert.assertThat(expected, is(actual));
    }

}

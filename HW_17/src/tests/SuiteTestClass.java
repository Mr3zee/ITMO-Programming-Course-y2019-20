package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.BigIntegerParserTest;
import tests.DoubleParserTest;
import tests.GeneralParserTest;
import tests.IntegerParserTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({GeneralParserTest.class, IntegerParserTest.class, DoubleParserTest.class, BigIntegerParserTest.class})
public class SuiteTestClass {

}


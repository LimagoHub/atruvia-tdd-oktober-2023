package de.atruvia;

import de.atruvia.client.CalcClient;
import de.atruvia.math.Calculator;
import de.atruvia.math.CalculatorImpl;
import de.atruvia.math.CalculatorLogger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Calculator calculator = new CalculatorImpl();
        calculator = new CalculatorLogger(calculator);
        CalcClient client = new CalcClient(calculator);

        client.go();
    }
}

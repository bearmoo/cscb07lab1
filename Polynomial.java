import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
	private double[] coefficients;
	private int[] exponents;
	public Polynomial() {
		coefficients = new double[]{0};
		exponents = new int[] {0};
	}
	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = coefficients;
		this.exponents = exponents;
	}
	public Polynomial(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            parsePolynomial(line);
        }
        scanner.close();
    }

    private void parsePolynomial(String line) {
        String[] terms = line.split("(?=[-+])");

        coefficients = new double[terms.length];
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i].trim();
            double coefficient;
            int exponent;

            if (term.contains("x")) {
                String[] parts = term.split("x");
                coefficient = (parts[0].isEmpty()) ? 1.0 : Double.parseDouble(parts[0]);
                exponent = (parts.length == 1 || parts[1].isEmpty()) ? 1 : Integer.parseInt(parts[1]);
            } else {
                coefficient = Double.parseDouble(term);
                exponent = 0;
            }

            coefficients[i] = coefficient;
            exponents[i] = exponent;
        }
    }
	public boolean hasRoot(double value) {
		double result = evaluate(value);
	    return result == 0.0;
	}
	public Polynomial add(Polynomial polynomial) {
	    int length1 = coefficients.length;
	    int length2 = polynomial.coefficients.length;
	    int maxLength = Math.max(length1, length2);

	    double[] resultCoefficients = new double[maxLength];
	    int[] resultExponents = new int[maxLength];

	    for (int i = 0; i < maxLength; i++) {
	        double value1 = (i < length1) ? coefficients[i] : 0.0;
	        int exponent1 = (i < length1) ? exponents[i] : 0;

	        double value2 = (i < length2) ? polynomial.coefficients[i] : 0.0;
	        int exponent2 = (i < length2) ? polynomial.exponents[i] : 0;

	        resultCoefficients[i] = value1 + value2;
	        resultExponents[i] = Math.max(exponent1, exponent2);
	    }

	    return new Polynomial(resultCoefficients, resultExponents);
	}
	public Polynomial multiply(Polynomial polynomial) {
        int length1 = coefficients.length;
        int length2 = polynomial.coefficients.length;
        int resultLength = length1 + length2 - 1;

        double[] resultCoefficients = new double[resultLength];
        int[] resultExponents = new int[resultLength];

        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                double value1 = coefficients[i];
                int exponent1 = exponents[i];

                double value2 = polynomial.coefficients[j];
                int exponent2 = polynomial.exponents[j];

                double product = value1 * value2;
                int exponent = exponent1 + exponent2;

                int index = exponent;
                resultCoefficients[index] += product;
                resultExponents[index] = exponent;
            }
        }

        return new Polynomial(resultCoefficients, resultExponents);
    }	
	public double evaluate(double x) {
        double result = 0;
        int length = coefficients.length;

        for (int i = length; i >= 0; i--) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }

        return result;
    }
	public void saveToFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String polynomialString = toString();
            bufferedWriter.write(polynomialString);

            bufferedWriter.close();
            System.out.println("Polynomial saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the polynomial to file: " + e.getMessage());
        }
    }
}


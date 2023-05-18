
public class Polynomial {
	private double[] coefficients;
	public Polynomial() {
		coefficients = new double[]{0};
	}
	public Polynomial(double[] coefficients) {
		this.coefficients = coefficients;
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

	    for (int i = 0; i < maxLength; i++) {
	        double value1 = (i < length1) ? coefficients[i] : 0.0;
	        double value2 = (i < length2) ? polynomial.coefficients[i] : 0.0;
	        resultCoefficients[i] = value1 + value2;
	    }

	    return new Polynomial(resultCoefficients);
	}
		
	public double evaluate(double x) {
        double result = 0;
        int degree = coefficients.length - 1;

        for (int i = degree; i >= 0; i--) {
            result += coefficients[i] * Math.pow(x, i);
        }

        return result;
    }
}

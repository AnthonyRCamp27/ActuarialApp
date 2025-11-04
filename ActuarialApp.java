import java.util.Scanner;

// -------- Domain --------
abstract class Policy {
    protected final String policyType;
    protected final double basePremium;
    protected final double coverageAmount;
    protected final int termYears;

    protected Policy(String policyType, double basePremium, double coverageAmount, int termYears) {
        this.policyType = policyType;
        this.basePremium = basePremium;
        this.coverageAmount = coverageAmount;
        this.termYears = termYears;
    }

    public abstract double calculatePremium(AssumptionSet assumptions);

    // Getters (read-only)
    public String getPolicyType() { return policyType; }
    public double getBasePremium() { return basePremium; }
    public double getCoverageAmount() { return coverageAmount; }
    public int getTermYears() { return termYears; }
}

class LifePolicy extends Policy {
    public LifePolicy(double basePremium, double coverageAmount, int termYears) {
        super("Life", basePremium, coverageAmount, termYears);
    }

    @Override
    public double calculatePremium(AssumptionSet assumptions) {
        double expectedClaim = coverageAmount * assumptions.getMortalityRate();
        double grossPremium = basePremium * (1.0 + assumptions.getExpenseLoad());
        return grossPremium + (basePremium * assumptions.getMortalityRate());
    }
}

final class AssumptionSet {
    private final double mortalityRate; // qx
    private final double expenseLoad;   // load
    private final double interestRate;  // i

    public AssumptionSet(double mortalityRate, double expenseLoad, double interestRate) {
        if (mortalityRate < 0 || expenseLoad < 0 || interestRate <= 0) {
            throw new IllegalArgumentException("Invalid assumption values.");
        }
        this.mortalityRate = mortalityRate;
        this.expenseLoad = expenseLoad;
        this.interestRate = interestRate;
    }

    public double getMortalityRate() { return mortalityRate; }
    public double getExpenseLoad()   { return expenseLoad; }
    public double getInterestRate()  { return interestRate; }
}

// -------- Service (Math Engine) --------
class ActuarialCalculator {
    public double expectedClaim(Policy policy, AssumptionSet a) {
        return policy.getCoverageAmount() * a.getMortalityRate();
    }

    public double presentValueOfBenefits(Policy policy, AssumptionSet a) {
        double i = a.getInterestRate();
        double claim = expectedClaim(policy, a);
        double annuityFactor = (1.0 - Math.pow(1.0 + i, -policy.getTermYears())) / i;
        return claim * annuityFactor;
    }

    public double netPremiumPerYear(Policy policy, AssumptionSet a) {
        return presentValueOfBenefits(policy, a) / policy.getTermYears();
    }
}

// -------- Security --------
class SSOService {
    public boolean authenticate(String token) {
        return "ACTUARY123".equals(token); // mock
    }
}

// -------- Application (CLI) --------
public class ActuarialApp {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        SSOService sso = new SSOService();
        ActuarialCalculator calc = new ActuarialCalculator();

        System.out.println("=== Actuarial Projection (Life) ===");
        System.out.print("Enter SSO token: ");
        String token = in.nextLine();
        if (!sso.authenticate(token)) {
            System.out.println("Access denied.");
            return;
        }

        try {
            System.out.print("Base premium: ");
            double base = in.nextDouble();

            System.out.print("Coverage amount: ");
            double coverage = in.nextDouble();

            System.out.print("Term (years): ");
            int term = in.nextInt();

            System.out.print("Mortality rate (e.g., 0.002): ");
            double qx = in.nextDouble();

            System.out.print("Expense load (e.g., 0.10): ");
            double load = in.nextDouble();

            System.out.print("Interest rate (e.g., 0.04): ");
            double i = in.nextDouble();

            AssumptionSet assumptions = new AssumptionSet(qx, load, i);
            Policy policy = new LifePolicy(base, coverage, term);

            double premium = policy.calculatePremium(assumptions);
            double expClaim = calc.expectedClaim(policy, assumptions);
            double pv = calc.presentValueOfBenefits(policy, assumptions);
            double net = calc.netPremiumPerYear(policy, assumptions);

            System.out.println("\n--- Results ---");
            System.out.printf("Policy Type: %s%n", policy.getPolicyType());
            System.out.printf("Expected Claim: %.2f%n", expClaim);
            System.out.printf("Gross/Loaded Premium: %.2f%n", premium);
            System.out.printf("PV of Benefits: %.2f%n", pv);
            System.out.printf("Net Premium / Year: %.2f%n", net);
        } catch (Exception ex) {
            System.out.println("Input error: " + ex.getMessage());
        } finally {
            in.close();
        }
    }
}

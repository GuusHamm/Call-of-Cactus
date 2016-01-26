package callofcactus.role;

/**
 * Created by Nekkyou on 2-11-2015.
 */
public class AI extends Role {
    /**
     * Makes a new instance of the class AI
     */
    public AI() {
        // Role(double healthMultiplier, double damageMultiplier, double speedMultiplier, double fireRateMultiplier)
        super("AI", 0.1, 1, 1, 1, 90);
    }
}

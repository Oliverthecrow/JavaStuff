import java.util.ArrayList;

/*
 * This is the largest horror ever created, it will eat all of your cpu cores for breakfast
 * 
 * nevermind I simply removed the array that just kept growing since I never actually
 * needed to go through everysingle combo, but just remember I could eat all your cores
 */

public class BalatroDeckBattle {
    public static void main(String[] args) {
        double chips = 0;
        double multiplier = 0;

        int chipRange = 200;
        int multRange = 500;

        double normalScore = 0;
        double plasmaScore = 0;

        WinCounter Counter = new WinCounter();

        ArrayList<Double> checkerArray = new ArrayList<>(2);
        checkerArray.add(1.0);
        checkerArray.add(1.0);

        for (int i = 0; i < (chipRange * multRange); i++) {
            chips = chip(chips, chipRange);
            multiplier = mult(multiplier, multRange);

            changeAlreadyDoneCombos(checkerArray, chips, multiplier, chipRange, multRange);

            winnerInjector(Counter, normalDeckMath(chips, multiplier, normalScore),
                    plasmaDeckMath(chips, multiplier, plasmaScore));

            System.out.println(
                    "NormalWinCounter: " + Counter.NormalWinCounter + " PlasmaWinCounter: " + Counter.PlasmaWinCounter
                            + " Total Count: " + Counter.TotalCount);
        }
        System.out.println("NormalPercentage: "
                + (double) Math.round(((double) Counter.NormalWinCounter / Counter.TotalCount) * 100000) / 1000 + "%");
        System.out.println("PlasmaPercentage: "
                + (double) Math.round(((double) Counter.PlasmaWinCounter / Counter.TotalCount) * 100000) / 1000 + "%");
    }

    public static void changeAlreadyDoneCombos(ArrayList<Double> checkerArray, double chips, double multiplier,
            int chipRange, int multRange) {
        for (int i = 0; i < checkerArray.size(); i += 2) {
            if (checkerArray.get(i) == chips & checkerArray.get(i + 1) == multiplier) {
                chips = chip(chips, chipRange);
                multiplier = mult(multiplier, multRange);
                i = 0;
            }
        }
        checkerArray.set(0, chips);
        checkerArray.set(1, multiplier);
    }

    public static double normalDeckMath(double chips, double multiplier, double normalScore) {
        normalScore = chips * multiplier;
        return normalScore;
    }

    public static double plasmaDeckMath(double chips, double multiplier, double plasmaScore) {
        plasmaScore = Math.floor(Math.pow(((chips + multiplier) / 2), 2) / 2);
        return plasmaScore;
    }

    public static WinCounter winnerInjector(WinCounter Counter, double normalScore, double plasmaScore) {
        if (normalScore > plasmaScore) {
            Counter.NormalWinCounter += 1;
            Counter.TotalCount += 1;
            return Counter;
        } else {
            Counter.PlasmaWinCounter += 1;
            Counter.TotalCount += 1;
            return Counter;
        }
    }

    public static Double chip(double chips, int chipRange) {
        // makes lowest value 0 and highest value ↓ chips
        chips = Math.round(Math.random() * chipRange);
        return chips;
    }

    public static Double mult(double multiplier, int multRange) {
        // makes lowest value 0 and highest value ↓ Mult
        multiplier = Math.round(Math.random() * multRange);
        return multiplier;
    }
}

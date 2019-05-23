package cupRiddle;

import java.util.HashSet;
import java.util.Set;

class Pair {
    int base;
    int transfer;
    public Pair(int x, int y) {
        this.base = x;
        this.transfer = y;
    }

}

public class CupRiddleSolver {

    public static void main(String[] args) {
        assert(cupSolverFindPathMethod(3, 5, 2));
        assert(cupSolverFindPathMethod(3, 5, 4));
        assert(cupSolverFindPathMethod(4, 7, 1));
        assert(cupSolverFindPathMethod(4, 7, 9));
        assert(!cupSolverFindPathMethod(4, 6, 5));
        assert(!cupSolverFindPathMethod(6, 9, 4));
        assert(cupSolverFindPathMethod(6, 9, 3));
        assert(!cupSolverFindPathMethod(6, 6, 13));
        assert(!cupSolverFindPathMethod(4, 12, 13));
        assert(cupSolverFindPathMethod (8, 22, 4));
        
        assert(cupSolverGCDMethod (8, 22, 4));
        assert(!cupSolverGCDMethod(4, 12, 13));
        assert(!cupSolverGCDMethod(6, 6, 13));
        assert(cupSolverGCDMethod(6, 9, 3));
        assert(cupSolverGCDMethod(3, 5, 2));
        assert(cupSolverGCDMethod(3, 5, 4));
        assert(cupSolverGCDMethod(4, 7, 1));
        assert(cupSolverGCDMethod(4, 7, 9));
        assert(!cupSolverGCDMethod(4, 6, 5));
        assert(!cupSolverGCDMethod(6, 9, 4));

    }



    static boolean cupSolverGCDMethod(int small, int big, int target) {
        boolean targetIsInRange = target >= 0 && target <= (small + big);
        int gcdResult = gcd(big, small);
        return (gcdResult == 1 && targetIsInRange) ||
            (target % (gcdResult) == 0 && targetIsInRange);
    }

    public static int gcd(int a, int b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
    }

    static boolean cupSolverFindPathMethod(int small, int big, int target) {
        Set < String > cupStates = new HashSet < String > ();
        return doesPathExist(small, big, 0, 0, target, cupStates);
    }

    static boolean doesPathExist(int small, int big, int smallCupAmount, int bigCupAmount, int target, Set < String > cupStates) {
        String cupState = String.format("%d%d", smallCupAmount, bigCupAmount);
        if (cupStates.contains(cupState)) {
            return false;
        }
        cupStates.add(cupState);
        if (smallCupAmount + bigCupAmount == target) {
            return true;
        } else {
            //Fill Left Cup
            if (doesPathExist(small, big, small, bigCupAmount, target, cupStates)) {
                return true;
            }
            //Fill Right Cup
            if (doesPathExist(small, big, smallCupAmount, big, target, cupStates)) {
                return true;
            }
            //Empty Left Cup
            if (doesPathExist(small, big, 0, bigCupAmount, target, cupStates)) {
                return true;
            }
            // Empty Right Cup
            if (doesPathExist(small, big, smallCupAmount, 0, target, cupStates)) {
                return true;
            }
            // Transfer small cup into big cup
            Pair cupAmounts = pour(big, small, bigCupAmount, smallCupAmount);
            int newBigCupAmount = cupAmounts.base;
            int newSmallCupAmount = cupAmounts.transfer;
            if (doesPathExist(small, big, newSmallCupAmount, newBigCupAmount, target, cupStates)) {

                return true;
            }
            //Transfer big cup into small cup
            cupAmounts = pour(small, big, smallCupAmount, bigCupAmount);
            newSmallCupAmount = cupAmounts.base;
            newBigCupAmount = cupAmounts.transfer;
            if (doesPathExist(small, big, newSmallCupAmount, newBigCupAmount, target, cupStates)) {
                return true;
            }
        }
        return false;
    }


    static Pair pour(int baseCupSize, int transferCupSize, int baseCupAmount, int transferCupAmount) {
        Pair result;
        if ((baseCupAmount + transferCupAmount) >= baseCupSize) {
            result = new Pair(baseCupSize, transferCupAmount - (baseCupSize - baseCupAmount));
        } else {
            result = new Pair(baseCupAmount + transferCupAmount, 0);
        }
        return result;
    }
}
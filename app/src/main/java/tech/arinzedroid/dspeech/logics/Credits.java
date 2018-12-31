package tech.arinzedroid.dspeech.logics;

public class Credits {

    public static boolean isEnoughCredit(double amt){
        if(amt <= 0)
            return false;
        else
            return true;
    }
}

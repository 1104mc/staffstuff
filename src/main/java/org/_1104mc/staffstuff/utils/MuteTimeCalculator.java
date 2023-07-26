package org._1104mc.staffstuff.utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MuteTimeCalculator {

    private static final HashMap<String, Long> TM_MULTIPLIERS = new HashMap<>(
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>("s", 1L),
                    new AbstractMap.SimpleEntry<>("m", 60L),
                    new AbstractMap.SimpleEntry<>("h", 3600L)
            )
    );
    private static final String[] TIME_FIELDS = new String[]{"másodpercre", "percre", "órára"};

    public static long timeoutToSeconds(String timeout) {
        StringBuilder part = new StringBuilder();
        long value = 0;
        char[] timeoutChars = timeout.toCharArray();
        for (char c : timeoutChars) {
            if (!TM_MULTIPLIERS.containsKey(String.valueOf(c))) {
                part.append(c);
                continue;
            }
            value += Long.parseLong(part.toString()) * TM_MULTIPLIERS.get(String.valueOf(c));
            part.delete(0, part.length());
        }
        return value;
    }

    public static String timeoutToTimeText(String tmInput){
        long tmValue = timeoutToSeconds(tmInput);
        List<Long> dividers = TM_MULTIPLIERS.values().stream().sorted().toList();
        StringBuilder outTime = new StringBuilder();
        for (int i = TM_MULTIPLIERS.size()-1; i >= 0; --i) {
            long divider = dividers.get(i);
            long whole_count = tmValue / divider;
            if (whole_count > 0){
                tmValue -= whole_count * divider;
                outTime.append(whole_count).append(" ").append(TIME_FIELDS[i]).append(", ");
            }
        }
        String outTimeStr = outTime.toString();
        return outTimeStr.substring(0, outTimeStr.length()-2);
    }

    private static boolean containsMultiplier(String str){
        AtomicBoolean result = new AtomicBoolean(false);
        TM_MULTIPLIERS.keySet().forEach(key -> {
            if(str.contains(key)) result.set(true);
        });
        return result.get();
    }

    public static List<String> offerTimeouts(String tmPrompt){
        if(!containsMultiplier(tmPrompt)) {
            List<String> singleItemOffers = new ArrayList<>();
            TM_MULTIPLIERS.keySet().stream().sorted()
                    .forEach(multiplier -> singleItemOffers.add(tmPrompt+multiplier));
            try{
                if(Long.parseLong(tmPrompt) > 1) singleItemOffers.removeIf(offer -> offer.contains("h"));
            }catch (NumberFormatException ignored){}
            return singleItemOffers;
        }else{
            if(containsMultiplier(tmPrompt.substring(tmPrompt.length()-1))) return null;
            if(tmPrompt.matches("[2-9]h")) return null;
            List<String> multipliers = new ArrayList<>(TM_MULTIPLIERS.keySet().stream().sorted().toList());
            multipliers.removeIf(tmPrompt::contains);
            List<String> offers = new ArrayList<>();
            multipliers.forEach(multiplier -> offers.add(tmPrompt+multiplier));
            return offers;
        }
    }
}

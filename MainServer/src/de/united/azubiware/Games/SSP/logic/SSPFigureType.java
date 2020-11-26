package de.united.azubiware.Games.SSP.logic;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum SSPFigureType {

    SCISSORS,
    ROCK,
    PAPER;

    public static SSPFigureType findByOrdinal(int pickType){
        List<SSPFigureType> filteredList = stream(values()).filter(new Predicate<SSPFigureType>() {
            @Override
            public boolean test(SSPFigureType type) {
                return type.ordinal() == pickType;
            }
        }).collect(Collectors.toList());

        if(filteredList.size() > 0)
            return filteredList.get(0);

        return null;
    }

}

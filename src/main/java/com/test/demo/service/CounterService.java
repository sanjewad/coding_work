package com.test.demo.service;

import com.test.demo.model.TextInfo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CounterService {

    public List<String> getCompleteTextList() {

        return Arrays.asList("Duis", "Duis", "Duis", "Duis", "Duis", "Duis", "Duis", "Duis", "Duis", "Duis",
                "Duis", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed", "Sed",
                "Sed", "Sed", "Donec", "Donec", "Donec", "Donec", "Donec", "Donec", "Donec", "Donec",
                "Augue", "Augue", "Augue", "Augue", "Augue", "Augue", "Augue",
                "Pellentesque", "Pellentesque", "Pellentesque", "Pellentesque", "Pellentesque", "Pellentesque");
    }

    public List<TextInfo> getTextCountList() {

        return Arrays.asList(
                new TextInfo("text1", 100),
                new TextInfo("text5", 60),
                new TextInfo("text3", 80),
                new TextInfo("text2", 91),
                new TextInfo("text4", 70),
                new TextInfo("text6", 58),
                new TextInfo("text7", 56),
                new TextInfo("text8", 54),
                new TextInfo("text9", 51),
                new TextInfo("text10", 48),
                new TextInfo("text11", 45),
                new TextInfo("text12", 42),
                new TextInfo("text13", 40),
                new TextInfo("text14", 39),
                new TextInfo("text15", 37),
                new TextInfo("text16", 34),
                new TextInfo("text17", 32),
                new TextInfo("text18", 31),
                new TextInfo("text19", 29),
                new TextInfo("text20", 28),
                new TextInfo("text21", 27),
                new TextInfo("text22", 25),
                new TextInfo("text23", 23),
                new TextInfo("text24", 20),
                new TextInfo("text25", 19),
                new TextInfo("text26", 18),
                new TextInfo("text27", 15),
                new TextInfo("text28", 11),
                new TextInfo("text29", 9),
                new TextInfo("text30", 4)
        );
    }
}

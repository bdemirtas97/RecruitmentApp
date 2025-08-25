package com.recruitment.app.utils;

import java.util.HashMap;
import java.util.Map;

public class CareerFieldMapper {
    private static final Map<String, String> mappings = new HashMap<>();

    static{
        addMapping("Engineering & Product", "Software Development", "Quality Assurance", "Product Management", "Business Development", "Design", "Product Management");
        addMapping("Partner Onboarding & Support", "Operations", "Customer Education");
        addMapping("Business", "Customer Success", "Sales", "Sales Operations");
        addMapping("Finance", "Finance & Business Support");
        addMapping("Marketing", "Marketing and Communications", "Marketing Design");
        addMapping("People & Culture", "Human Resources");
        addMapping("MindBehind", "MindBehind");
        addMapping("Ceo’s Office", "CEO's Executive Office");
    }

    private static void addMapping(String value, String... keys){
        for(String key : keys) mappings.put(key,value);
    }

    public static String getValue(String key){
        return mappings.get(key);
    }
}

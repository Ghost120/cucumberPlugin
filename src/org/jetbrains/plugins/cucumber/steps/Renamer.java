package org.jetbrains.plugins.cucumber.steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kasyanenko Konstantin
 * 27.06.2017
 */
public class Renamer {
    private static Renamer Instance = new Renamer();
    private static Map<String, String> languageMap = new HashMap();
    private static Properties props = new Properties();

    private Renamer() {
    }

    public static Renamer getInstance() {
        if (Instance == null) {
            Instance = new Renamer();
        }else if(languageMap.size()==0){
            saveParams("ru");
        }
        return Instance;
    }

    /**
     * Сохраняет проперти в мапу
     * @param language
     */
    private static void saveParams(String language) {
        try {
            String dir = System.getProperty("user.home") + "/IdeaProjects/CucumberPlugin/resources/";
            File file = new File(dir + language + ".properties");
            List<String> list = null;
            list = Files.lines(Paths.get(dir + language + ".properties"), StandardCharsets.UTF_8).collect(Collectors.toList());
            for (String str : list) {
                String[] mp = str.split("=");
                languageMap.put(mp[0], mp[1].replace("\\\\","\\"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * возвращает мапу из параметров
     * @param s
     * @return
     */
    private Map<String, String> getMapIntoString(String s) {
        Map<String, String> retMap = new HashMap<>();
        String[] array = s.split("\n");
        List<String> list = Arrays.asList(array);
        for (String str : list) {
            String[] mp = str.split("=");
            retMap.put(mp[0], mp[1]);
        }
        return retMap;
    }

    /**
     * Возвращает новое значнение или если нет в мапе то то что ему пришло
     * @param oldName
     * @return
     */
    public String getChangeParam(String oldName) {
         String retValue = languageMap.get(oldName);
        if (retValue == null) {
            return oldName;
        } else {
            return retValue;
        }
    }
}

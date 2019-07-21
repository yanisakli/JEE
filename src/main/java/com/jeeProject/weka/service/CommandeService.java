package com.jeeProject.weka.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandeService {


    public String getOutput(File output) {

        String s;
        StringBuilder result = new StringBuilder();

        try {

            System.out.println("voila");
            String[] cmd = {"python", "/home/ubuntu/fileload/load.py", output.getAbsolutePath()};
            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));


            // read the output from the command
            while ((s = stdInput.readLine()) != null) result.append(s);

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) result.append(s);

            System.out.println(result);

            System.exit(0);
        } catch (IOException e) {
            System.exit(-1);
        }
        return result.toString();
    }
}

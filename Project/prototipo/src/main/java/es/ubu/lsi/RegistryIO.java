package es.ubu.lsi;

import org.apache.catalina.LifecycleState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistryIO {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void guardarResultados(String url, String user, String courseid, AnalysisSnapshot instantanea){
        try {
            File f1 = new File("registry/"+url);
            File f2 = new File("registry/"+url+"/"+user);
            FileWriter pw = new FileWriter("registry/"+url+"/"+user+"/"+courseid+".csv",true);
            f1.mkdir();
            f2.mkdir();
            pw.append(instantanea.toString());
            pw.flush();
            pw.close();
        }catch (Exception e){
            LOGGER.error("exception", e);
        }
    }

    public static List<AnalysisSnapshot> leerCSV(String url, String user, String courseid){
        List<AnalysisSnapshot> registros=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("registry/"+url+"/"+user+"/"+courseid+".csv"))){
            String linea = br.readLine();
            String[] atributos;
            while (linea != null) {
                atributos=linea.split(";");
                registros.add(new AnalysisSnapshot(atributos));
                linea = br.readLine();
            }
        }catch (Exception e){
            LOGGER.error("exception", e);
        }
        return registros;
    }




    public static String generarGrafico(String url,String user,String courseid){return "";}

    public static String generarGrafico(String url,String user){return "";}

    public static String informeGlobal(String url,String user){return "";}
}

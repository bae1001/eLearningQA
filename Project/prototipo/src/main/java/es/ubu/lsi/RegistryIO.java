package es.ubu.lsi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class RegistryIO {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String barra=File.separator;
    private static final String carpeta="registry";

    private RegistryIO() {
        throw new IllegalStateException("Utility class");
    }

    public static void guardarResultados(String url, String user, String courseid, AnalysisSnapshot instantanea){
        try (FileWriter pw = new FileWriter(carpeta+barra+url+barra+user+barra+courseid+".csv",true);){
            File f1 = new File(carpeta+barra+url);
            File f2 = new File(carpeta+barra+url+barra+user);
            f1.mkdir();
            f2.mkdir();
            pw.append(instantanea.toString());
            pw.flush();
        }catch (Exception e){
            LOGGER.error("exception", e);
        }
    }

    public static List<AnalysisSnapshot> leerCSV(String url, String user, String courseid){
        List<AnalysisSnapshot> registros=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(carpeta+barra+url+barra+user+barra+courseid+".csv"))){
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




    public static String generarGrafico(String url,String user,String courseid){return url+user+courseid;}

    public static String generarGrafico(String url,String user){return url+user;}

    public static String informeGlobal(String url,String user){return user+url;}
}

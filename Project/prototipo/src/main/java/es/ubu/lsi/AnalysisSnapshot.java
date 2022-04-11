package es.ubu.lsi;

import java.util.Arrays;

public class AnalysisSnapshot {
    private String nombre;
    private long fecha;
    private int[] puntoscomprobaciones;
    private float[] porcentajesDesempeno;
    private String errores;

    public AnalysisSnapshot(String nombre, int[] puntoscomprobaciones, float[] porcentajesDesempeno, String errores) {
        this.nombre=nombre;
        this.fecha=System.currentTimeMillis();
        this.puntoscomprobaciones=puntoscomprobaciones;
        this.porcentajesDesempeno=porcentajesDesempeno;
        this.errores=errores;
    }

    public AnalysisSnapshot(String[] atributos) {
        nombre=atributos[0];
        fecha=Long.parseLong(atributos[1]);
        puntoscomprobaciones=leerArrayPuntos(atributos[2]);
        porcentajesDesempeno=leerArrayPorcentajes(atributos[3]);
        errores=atributos[4];
    }

    public static int[] leerArrayPuntos(String textoArray){
        String textoLimpio=textoArray.replaceAll("[\\[\\] ]","");
        String[] textoSeparado=textoLimpio.split(",");
        int[] array=new int[17];
        for (int i=0;i<array.length;i++){
            array[i]=Integer.parseInt(textoSeparado[i]);
        }
        return array;
    }

    public static float[] leerArrayPorcentajes(String textoArray){
        String textoLimpio=textoArray.replaceAll("[\\[\\] ]","");
        String[] textoSeparado=textoLimpio.split(",");
        float[] array=new float[9];
        for (int i=0;i<array.length;i++){
            array[i]=Float.parseFloat(textoSeparado[i]);
        }
        return array;
    }

    public String getNombre() {
        return nombre;
    }

    public long getFecha() {
        return fecha;
    }

    public int[] getPuntoscomprobaciones() {
        return puntoscomprobaciones;
    }

    public float[] getPorcentajesDesempeno() {
        return porcentajesDesempeno;
    }

    public String getErrores() {
        return errores;
    }

    @Override
    public String toString() {
        String sep=";";
        return nombre +
                sep + fecha +
                sep + Arrays.toString(puntoscomprobaciones) +
                sep + Arrays.toString(porcentajesDesempeno) +
                sep + errores + "\n";
    }
}

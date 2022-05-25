package es.ubu.lsi;

public class AlertLog {
    private StringBuilder alertas;
    private int courseid;
    private int contadorAcordeones=0;

    public AlertLog() {
        this.alertas = new StringBuilder();
    }

    public String generarAlerta(String categoria, String mensaje){
        return "<div class=\"alert alert-danger infoline overall "+categoria+"\">"+mensaje+"</div>";
    }

    public String generarAlertaDesplegable(String categoria, String mensaje, String nombreDetalles, String detalles){
        String id="c"+contadorAcordeones++;
        return "<div class=\"alert alert-danger p-0 infoline overall "+categoria+"\">"+
                "<div class=\"accordion-item\">" +
                "<button class=\"accordion-button alert-danger collapsed\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#"+id+"\" aria-expanded=\"true\">" +
                mensaje +"</button>"+
                "<div id=\""+id+"\" class=\"accordion-collapse collapse alert-danger\">" +
                "<div class=\"accordion-body\"><strong>"+nombreDetalles+"</strong></br>"+detalles+"</div></div></div></div>";
    }

    public void guardarAlerta(String categoria, String mensaje){
        alertas.append(generarAlerta(categoria, mensaje));
    }

    public void guardarTitulo(String titulo){
        alertas.append("<h4>"+titulo+"</h4>");
    }

    public void guardarAlertaDesplegable(String categoria, String mensaje, String nombreDetalles, String detalles){
        alertas.append(generarAlertaDesplegable(categoria, mensaje, nombreDetalles, detalles));
    }

    @Override
    public String toString() {
        return alertas.toString();
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }
}

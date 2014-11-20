package utn.aed.tp1;
public class MascotaVirtual {
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public MascotaVirtual(String nombre) {
        this.nombre = nombre;
        energia = 50;
        humor = 3;
        estado = ESTADO_DESPIERTO;
        conteoIngesta = 0;
        conteoEjercicio = 0;
        ultimaActividadEjecutada = ACTIVIDAD_ALTERNAR_DORMIR_DESPERTAR;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public final static int ESTADO_MUERTO = 0;
    public final static int ESTADO_DESPIERTO = 1;
    public final static int ESTADO_DURMIENDO = 2;
    public final static int ENERGIA_MAX = 100;
    private final int ENERGIA_MIN = 0;
    public final static int HUMOR_MAX = 5;
    private final int HUMOR_MIN = 1;
    public final static int ACTIVIDAD_COMER = 1;
    public final static int ACTIVIDAD_BEBER = 2;
    public final static int ACTIVIDAD_ALTERNAR_DORMIR_DESPERTAR = 3;
    public final static int ACTIVIDAD_CORRER = 4;
    public final static int ACTIVIDAD_CAMINAR = 5;
    public final static int ACTIVIDAD_SALTAR = 6;
    public final static int ACTIVIDAD_JUGAR = 7;
    private final int MODIFICACION_BASE_HUMOR_COMER = 1;
    private final int MODIFICACION_BASE_HUMOR_BEBER = 2;
    private final int CANTIDAD_MIN_PARA_SOBRE_INGESTA = 2;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private final String nombre;
    private int energia;
    private int humor;
    private int estado;
    private int conteoIngesta;
    private int conteoEjercicio;
    private int ultimaActividadEjecutada;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getNombre() {
        return nombre;
    }
    public int getEnergia() {
        return energia;
    }
    public int getHumor() {
        return humor;
    }
    public int getEstado() {
        return estado;
    }
    public String getEstadoEnString() {
        switch(estado){
            case ESTADO_DESPIERTO:
                return "Despierto";
            case ESTADO_DURMIENDO:
                return "Durmiendo";
            case ESTADO_MUERTO:
                return "Muerto";
            default:
                return "Estado desconocido...";
        }
    }
    public int getConteoIngesta(){
        return conteoIngesta;
    }
    public int getConteoEjercicio(){
        return conteoEjercicio;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public String calcularModificacionHumorComer(){
        int n = (conteoIngesta+1 >= CANTIDAD_MIN_PARA_SOBRE_INGESTA) ? (MODIFICACION_BASE_HUMOR_COMER + (-1 * (conteoIngesta))) : MODIFICACION_BASE_HUMOR_COMER;
        String s = (n >= 0) ? "+" + n : "" + n;
        return s;
    }
    public String calcularModificacionHumorBeber(){
        int n = (conteoIngesta+1 >= CANTIDAD_MIN_PARA_SOBRE_INGESTA) ? (MODIFICACION_BASE_HUMOR_BEBER + (-1 * (conteoIngesta))) : MODIFICACION_BASE_HUMOR_BEBER;
        String s = (n >= 0) ? "+" + n : "" + n;
        return s;
    }
    public String calcularModificacionHumorJugar(){
        /*
        incrementa el nivel de humor 
        en 1 grado si el nivel de humor actual es 
        mayor o igual a 2 
        y en 3 grados en caso de que sea menor.
        */
        return (humor >= 2) ? "+" + 1 : "+" + 3;
    }
    
    private void modificarHumorPorUnidad(int cantidad) {
        humor += cantidad;
        verificarHumor();
    }
    private void modificarHumorPorUnidadVerificandoSobreIngesta(int cantidad) {
        if (conteoIngesta >= CANTIDAD_MIN_PARA_SOBRE_INGESTA)
            modificarHumorPorUnidad(cantidad + (-1 * conteoIngesta));
        else
            modificarHumorPorUnidad(cantidad);
    }
    private void verificarHumor() {
        if (humor > HUMOR_MAX)
            humor = HUMOR_MAX;
        if (humor < HUMOR_MIN)
            humor = HUMOR_MIN;
    }
    
    private void modificarEnergiaPorUnidad(int cantidad) {
        energia += cantidad;
        verificarEnergia();
    }
    private void modificarEnergiaPorPorcentaje(int cantidad) {
        energia += Math.round((float) energia * (float) cantidad / 100.0);
        verificarEnergia();
    }
    private void verificarEnergia() {
        if (energia > ENERGIA_MAX)
            energia = ENERGIA_MAX;
        if (energia <= ENERGIA_MIN){
            estado = ESTADO_MUERTO;
            energia = ENERGIA_MIN;
        }
    }
    
    private void marcarUltimaActividadEjecutada(int actividad){
        ultimaActividadEjecutada = actividad;
        verificarCantidadVecesIngesta();
        verificarCantidadVecesEjercicio();
    }
    private void verificarCantidadVecesIngesta(){
        if (ultimaActividadEjecutada == ACTIVIDAD_COMER || ultimaActividadEjecutada == ACTIVIDAD_BEBER) {
            conteoIngesta++;
            if (conteoIngesta == 5) 
                estado = ESTADO_MUERTO;
        }
        else
            conteoIngesta = 0;
    }
    private void verificarCantidadVecesEjercicio(){
        if (ultimaActividadEjecutada == ACTIVIDAD_CORRER || ultimaActividadEjecutada == ACTIVIDAD_CAMINAR 
            || ultimaActividadEjecutada == ACTIVIDAD_SALTAR || ultimaActividadEjecutada == ACTIVIDAD_JUGAR) {
            conteoEjercicio++;
            if (conteoEjercicio == 5){
                dormir();
                conteoEjercicio = 5;
            }
        }
        else
            conteoEjercicio = 0;
    }
    
    private boolean estaLaMascotaEnEstadoRequerido(int estadoRequerido){
        return (estado == estadoRequerido);
    }
    
    public void alternarEntreDormirDespertar(int estadoAlQueCambia) {
        if (estadoAlQueCambia == ESTADO_DESPIERTO) 
            despertar();
        else if (estadoAlQueCambia == ESTADO_DURMIENDO) 
            dormir();
    }
    public void despertar() {
        // Verico que la mascota este en el estado requerido para realizar esta accion
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DURMIENDO)) 
            return;
        estado = ESTADO_DESPIERTO;
        conteoEjercicio = 0;
    }
    
    public void comer() {
        // Verico que la mascota este en el estado requerido para realizar esta accion
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DESPIERTO)) 
            return;
        marcarUltimaActividadEjecutada(ACTIVIDAD_COMER);
        modificarEnergiaPorPorcentaje(15);
        modificarHumorPorUnidadVerificandoSobreIngesta(1);
    }
    public void beber() {
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DESPIERTO)) 
            return;
        marcarUltimaActividadEjecutada(ACTIVIDAD_BEBER);
        modificarEnergiaPorPorcentaje(10);
        modificarHumorPorUnidadVerificandoSobreIngesta(2);
    }
    public void dormir() {
        // Verico que la mascota este en el estado requerido para realizar esta accion
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DESPIERTO)) 
            return;
        estado = ESTADO_DURMIENDO;
        modificarEnergiaPorPorcentaje(20);
        modificarHumorPorUnidad(-2);
        marcarUltimaActividadEjecutada(ACTIVIDAD_ALTERNAR_DORMIR_DESPERTAR);
    }
    
    public void correr() {
        // Verico que la mascota este en el estado requerido para realizar esta accion
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DESPIERTO)) 
            return;
        modificarEnergiaPorUnidad(-15);
        modificarHumorPorUnidad(1);
        marcarUltimaActividadEjecutada(ACTIVIDAD_CORRER);
    }
    public void caminar() {
        // Verico que la mascota este en el estado requerido para realizar esta accion
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DESPIERTO)) 
            return;
        modificarEnergiaPorUnidad(-5);
        marcarUltimaActividadEjecutada(ACTIVIDAD_CAMINAR);
    }
    public void saltar() {
        // Verico que la mascota este en el estado requerido para realizar esta accion
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DESPIERTO)) 
            return;
        modificarEnergiaPorUnidad(-20);
        modificarHumorPorUnidad(1);
        marcarUltimaActividadEjecutada(ACTIVIDAD_SALTAR);
    }
    public void jugar() {
        // Verico que la mascota este en el estado requerido para realizar esta accion
        if (!estaLaMascotaEnEstadoRequerido(ESTADO_DESPIERTO)) 
            return;
        modificarEnergiaPorUnidad(-30);
        if (humor >= 2)
            modificarHumorPorUnidad(1);
        else
            modificarHumorPorUnidad(3);
        marcarUltimaActividadEjecutada(ACTIVIDAD_JUGAR);
    }
    //</editor-fold>
}

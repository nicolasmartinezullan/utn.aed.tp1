package utn.aed.tp1;
public class Menu {
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Menu() {
        mascotaCreada = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    private final int LONGITUD_MAX_NOMBRE = 37;
    private final int LONGITUD_MAX_LINEA = 60;
    
    private final int MENU_PRINCIPAL_CREAR_MASCOTA_PRIMERA_VEZ = 1;
    private final int MENU_PRINCIPAL_CREAR_MASCOTA_BORRANDO_EXISTENTE = 2;
    private final int MENU_PRINCIPAL_ACTIVIDADES = 3;
    private final int MENU_PRINCIPAL_SALIR = 0;
    
    private final int MENU_ACTIVIDADES_COMER = 1;
    private final int MENU_ACTIVIDADES_BEBER = 2;
    private final int MENU_ACTIVIDADES_DORMIR = 3;
    private final int MENU_ACTIVIDADES_CORRER = 4;
    private final int MENU_ACTIVIDADES_CAMINAR = 5;
    private final int MENU_ACTIVIDADES_SALTAR = 6;
    private final int MENU_ACTIVIDADES_JUGAR = 7;
    private final int MENU_ACTIVIDADES_SALIR = 0;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    MascotaVirtual mascota = null;
    boolean mascotaCreada;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public void ejecutar(){
        ejecutarMenuPrincipal();
    }
    
    private void ejecutarMenuCrearMascota(int opcionSeleccionada){
        if (mascotaCreada && opcionSeleccionada == MENU_PRINCIPAL_CREAR_MASCOTA_PRIMERA_VEZ) {
            imprimirMensajeMascotaYaCreada();
        }
        else {
            String nombre = imprimirMenuCrearMascota();
            procesarMenuCrearMascota(nombre);
            mascotaCreada = true;
        }
    }
    
    private void ejecutarMenuPrincipal() {
        int opcion;
        do {
            opcion = imprimirMenuPrincipal();
            // Si la mascota no esta creada la opcion 3 debe ser tomada como invalida
            if (opcion == MENU_PRINCIPAL_ACTIVIDADES && !mascotaCreada)
                opcion = -1;
            procesarMenuPrincipal(opcion);
        } while (opcion != MENU_PRINCIPAL_SALIR);
    }

    private void ejecutarMenuActividades(){
        int opcion;
        do{
            opcion = imprimirMenuActividades();
            procesarMenuActividades(opcion);
            if (mascota.getEstado() == MascotaVirtual.ESTADO_DURMIENDO || mascota.getEstado() == MascotaVirtual.ESTADO_MUERTO)
                opcion = MENU_ACTIVIDADES_SALIR;
        }while(opcion != MENU_ACTIVIDADES_SALIR);
    }
//===================================================================
    private void procesarMenuCrearMascota(String nombre){
        int len = (nombre.length() < LONGITUD_MAX_NOMBRE) ? nombre.length() : LONGITUD_MAX_NOMBRE;
        nombre = nombre.substring(0, len);
        if (nombre.length() == 0)
            nombre = "{mascota-sin-nombre}";
        mascota = new MascotaVirtual(nombre);
    }
    
    private void procesarMenuPrincipal(int opcion) {
        switch (opcion) {
            case MENU_PRINCIPAL_CREAR_MASCOTA_PRIMERA_VEZ:
                ejecutarMenuCrearMascota(MENU_PRINCIPAL_CREAR_MASCOTA_PRIMERA_VEZ);
                break;
            case MENU_PRINCIPAL_CREAR_MASCOTA_BORRANDO_EXISTENTE:
                ejecutarMenuCrearMascota(MENU_PRINCIPAL_CREAR_MASCOTA_BORRANDO_EXISTENTE);
                break;
            case MENU_PRINCIPAL_ACTIVIDADES:
                if (mascota != null) {
                    if (mascota.getEstado() == MascotaVirtual.ESTADO_DURMIENDO)
                        mascota.alternarEntreDormirDespertar(MascotaVirtual.ESTADO_DESPIERTO);
                    else if (mascota.getEstado() == MascotaVirtual.ESTADO_DESPIERTO)
                        ejecutarMenuActividades();
                    else if (mascota.getEstado() == MascotaVirtual.ESTADO_MUERTO)
                        imprimirEscribaOpcionCorrecta();
                    break;
                }
            case MENU_PRINCIPAL_SALIR:
                limpiarPantalla();
                System.out.println(armarLineaMenu("",'=',0,false));
                System.out.println(armarLineaMenu("",' ',0,true));
                System.out.println(armarLineaMenu("GRACIAS POR USAR TAMAGOTCHI MASCOTA VIRTUAL!!!",' ',1,true));
                System.out.println(armarLineaMenu("",' ',0,true));
                System.out.println(armarLineaMenu("",'=',0,false));
                System.out.println();
                break;
            default:
                imprimirEscribaOpcionCorrecta();
        }
    }
    
    private void procesarMenuActividades(int opcion){
        switch(opcion){
            case MENU_ACTIVIDADES_COMER:
                mascota.comer();
                break;
            case MENU_ACTIVIDADES_BEBER:
                mascota.beber();
                break;
            case MENU_ACTIVIDADES_DORMIR:
                mascota.alternarEntreDormirDespertar(MascotaVirtual.ESTADO_DURMIENDO);
                break;
            case MENU_ACTIVIDADES_CORRER:
                mascota.correr();
                break;
            case MENU_ACTIVIDADES_CAMINAR:
                mascota.caminar();
                break;
            case MENU_ACTIVIDADES_SALTAR:
                mascota.saltar();
                break;
            case MENU_ACTIVIDADES_JUGAR:
                mascota.jugar();
                break;
            case MENU_ACTIVIDADES_SALIR:
                // Do nothing...
                break;
            default:
                imprimirEscribaOpcionCorrecta();
        }
    }
//===================================================================
    private void imprimirMensajeMascotaYaCreada(){
        limpiarPantalla();
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.println(armarLineaMenu("Solo usa esta opcion para crear la mascota por",' ',3,true));
        System.out.println(armarLineaMenu("primera vez",' ',3,true));
        System.out.println(armarLineaMenu("Presione enter para continuar...",' ',10,true));
        System.out.println(armarLineaMenu("",'=',0,false));
        Consola.readLine();
    }
    
    private String imprimirMenuCrearMascota() {
        limpiarPantalla();
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.println(armarLineaMenu("CREACION NUEVA MASCOTA",' ',13,true));
        System.out.println(armarLineaMenu("",'-',0,true));
        System.out.println(armarLineaMenu(String.format("Longitud maxima para nombre: %s caracteres", LONGITUD_MAX_NOMBRE),' ',1,true));
        System.out.println(armarLineaMenu("",' ',0,true));
        System.out.println(armarLineaMenu("",' ',0,true));
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.print("Nombre: ");
        return Consola.readLine();
    }

    private int imprimirMenuPrincipal() {
        limpiarPantalla();
        if (mascotaCreada == true)
            imprimirEstado();
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.println(armarLineaMenu("MENU PRINCIPAL",' ',17,true));
        System.out.println(armarLineaMenu("",'-',0,true));
        System.out.println(armarLineaMenu("Opciones:",' ',1,true));
        System.out.println(armarLineaMenu(MENU_PRINCIPAL_CREAR_MASCOTA_PRIMERA_VEZ + ". Crear mascota por primera vez",' ',7,true));
        System.out.println(armarLineaMenu(MENU_PRINCIPAL_CREAR_MASCOTA_BORRANDO_EXISTENTE + ". Crear nueva mascota borrando la existente",' ',7,true));
        if (mascotaCreada == true) {
            if (mascota.getEstado() == MascotaVirtual.ESTADO_DURMIENDO)
                System.out.println(armarLineaMenu(MENU_PRINCIPAL_ACTIVIDADES + ". Despertar",' ',7,true));
            else if (mascota.getEstado() == MascotaVirtual.ESTADO_DESPIERTO) 
                System.out.println(armarLineaMenu(MENU_PRINCIPAL_ACTIVIDADES + ". Actividades",' ',7,true));
            else if (mascota.getEstado() == MascotaVirtual.ESTADO_MUERTO) 
                System.out.println(armarLineaMenu("",' ',0,true));
        }
        else
            System.out.println(armarLineaMenu("",' ',0,true));
        System.out.println(armarLineaMenu(MENU_PRINCIPAL_SALIR +". Terminar",' ',7,true));
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.print("Ingrese una opcion: ");
        return Consola.readInt();
    }

    private int imprimirMenuActividades() {
        limpiarPantalla();
        imprimirEstado();
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.println(armarLineaMenu("MENU ACTIVIDADES",' ',17,true));
        System.out.println(armarLineaMenu("",'-',0,true));
        System.out.println(armarLineaMenu("Opciones:",' ',1,true));
        System.out.println(armarLineaMenu(String.format("%s. Comer   (+15%% energia , %s humor)", MENU_ACTIVIDADES_COMER, mascota.calcularModificacionHumorComer()),' ',7,true));
        System.out.println(armarLineaMenu(String.format("%s. Beber   (+10%% energia , %s humor)",MENU_ACTIVIDADES_BEBER, mascota.calcularModificacionHumorBeber()),' ',7,true));
        System.out.println(armarLineaMenu(String.format("%s. Dormir  (+20%% energia , -2 humor)",MENU_ACTIVIDADES_DORMIR),' ',7,true));
        System.out.println(armarLineaMenu(String.format("%s. Correr  (-15 energia  , +1 humor)",MENU_ACTIVIDADES_CORRER),' ',7,true));
        System.out.println(armarLineaMenu(String.format("%s. Caminar ( -5 energia  , +0 humor)",MENU_ACTIVIDADES_CAMINAR),' ',7,true));
        System.out.println(armarLineaMenu(String.format("%s. Saltar  (-20 energia  , +1 humor)",MENU_ACTIVIDADES_SALTAR),' ',7,true));
        System.out.println(armarLineaMenu(String.format("%s. Jugar   (-30 energia  , %s humor)",MENU_ACTIVIDADES_JUGAR, mascota.calcularModificacionHumorJugar()),' ',7,true));
        System.out.println(armarLineaMenu(String.format("%s. Volver a Menu Principal",MENU_ACTIVIDADES_SALIR),' ',7,true));
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.print("Ingrese una opcion: ");
        return Consola.readInt();
    }    
    
    private void imprimirEstado(){
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.println(armarLineaMenu(String.format("Nombre:            %s", mascota.getNombre()),' ',1,true));
        System.out.println(armarLineaMenu(String.format("Estado:            %s", mascota.getEstadoEnString()),' ',1,true));
        System.out.println(armarLineaMenu(String.format("Energia:           %s/%s", mascota.getEnergia(), MascotaVirtual.ENERGIA_MAX),' ',1,true));
        System.out.println(armarLineaMenu(String.format("Humor:             %s/%s", mascota.getHumor(), MascotaVirtual.HUMOR_MAX),' ',1,true));
        System.out.println(armarLineaMenu(String.format("Ingestas seguidas: %s", mascota.getConteoIngesta()),' ',1,true));
        System.out.println(armarLineaMenu(String.format("Ejercicio seguido: %s", mascota.getConteoEjercicio()),' ',1,true));
    }
    
    private void imprimirEscribaOpcionCorrecta(){
        System.out.println();
        System.out.println(armarLineaMenu("",'=',0,false));
        System.out.println(armarLineaMenu("Elija una opcion correcta...",' ',10,true));
        System.out.println(armarLineaMenu("Presione enter para continuar...",' ',10,true));
        System.out.println(armarLineaMenu("",'=',0,false));
        Consola.readLine();
    }

    private String armarLineaMenu(String textoDeLinea, char caracterRelleno , int indentacion, boolean laterales){
        String linea = "";
        
        // Agregar indentacion si se especifico
        if (indentacion > 0)
            for (int i = 0; i < indentacion; i++)
                linea += " ";
        
        // Agregar el texto de linea
        linea += textoDeLinea.trim();
        
        // Calculo lugares libres para linea
        int caracteresLibres = LONGITUD_MAX_LINEA - linea.length();
        
        // Completo lugares libres con el caracter de relleno hasta el maximo de caracteres permitido por linea
        // Si no hay lugares libres, me aseguro que no se exeda el maximo permitido
        if (caracteresLibres > 0)
            for (int i = 0; i < caracteresLibres; i++)
                linea += caracterRelleno;
        else
            linea = linea.substring(0, LONGITUD_MAX_LINEA);
        
        // Agregar laterales si corresponde
        if (laterales){
            linea = linea.substring(0, linea.length()-2);
            linea = "|" + linea + "|";
        }
        
        return linea;
    }
    
    private void limpiarPantalla(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
//===================================================================
    //</editor-fold>
}
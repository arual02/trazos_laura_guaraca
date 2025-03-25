class Figura {
    String tipo;
    int x, y, ancho, alto;
    Figura siguiente;

    public Figura(String tipo, int x, int y, int ancho, int alto) {
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.siguiente = null;
    }
}
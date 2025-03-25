import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
class ListaLigada {
    private Nodo cabeza;

    public void agregar(String tipo, int x1, int y1, int x2, int y2) {
        Nodo nuevo = new Nodo(tipo, x1, y1, x2, y2);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public void eliminarUltimo() {
        if (cabeza == null) return;
        if (cabeza.siguiente == null) {
            cabeza = null;
            return;
        }
        Nodo actual = cabeza;
        while (actual.siguiente.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = null;
    }

    public void guardarDibujo(String archivo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
        Nodo actual = cabeza;
        while (actual != null) {
            writer.write(actual.tipo + "," + actual.x1 + "," + actual.y1 + "," + actual.x2 + "," + actual.y2);
            writer.newLine();
            actual = actual.siguiente;
        }
        writer.close();
    }

    public void cargarDibujo(String archivo) throws IOException {
        cabeza = null;
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(",");
            agregar(partes[0], Integer.parseInt(partes[1]), Integer.parseInt(partes[2]),
                    Integer.parseInt(partes[3]), Integer.parseInt(partes[4]));
        }
        reader.close();
    }

    public Nodo getCabeza() {
        return cabeza;
    }
}
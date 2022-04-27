package com.example.practica1;

import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
public class Palet {

    public double ancho,alto,largo;
    public String estado,idProducto;
    public int nEsteria,altura,posicion,idPalet,cantidad;
    public PhongMaterial material;
    public boolean delante;
    public Group grupo;

    public Palet(double ancho, double alto, double largo, String estado, String idProducto, int nEsteria, int altura, int posicion, int idPalet, int cantidad, PhongMaterial material, boolean delante, Group grupo) {
        this.ancho = ancho;
        this.alto = alto;
        this.largo = largo;
        this.estado = estado;
        this.idProducto = idProducto;
        this.nEsteria = nEsteria;
        this.altura = altura;
        this.posicion = posicion;
        this.idPalet = idPalet;
        this.cantidad = cantidad;
        this.material = material;
        this.delante = delante;
        this.grupo = grupo;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getnEsteria() {
        return nEsteria;
    }

    public void setnEsteria(int nEsteria) {
        this.nEsteria = nEsteria;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getIdPalet() {
        return idPalet;
    }

    public void setIdPalet(int idPalet) {
        this.idPalet = idPalet;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public PhongMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PhongMaterial material) {
        this.material = material;
    }

    public boolean isDelante() {
        return delante;
    }

    public void setDelante(boolean delante) {
        this.delante = delante;
    }

    public Group getGrupo() {
        return grupo;
    }

    public void setGrupo(Group grupo) {
        this.grupo = grupo;
    }

    public String toString(){
        return nEsteria +" ;";
    }
}

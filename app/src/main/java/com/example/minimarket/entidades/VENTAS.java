package com.example.minimarket.entidades;

public class VENTAS {

    private int id_venta;
    private String codigo_venta;
    private String nombre_venta;
    private String marca_venta;
    private double precio_venta;
    private double cantidad_venta;
    private String tipounidad_venta;
    private double fecha_venta;
    private double totalpago_venta;


    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getCodigo_venta() {
        return codigo_venta;
    }

    public void setCodigo_venta(String codigo_venta) {
        this.codigo_venta = codigo_venta;
    }

    public String getNombre_venta() {
        return nombre_venta;
    }

    public void setNombre_venta(String nombre_venta) {
        this.nombre_venta = nombre_venta;
    }

    public String getMarca_venta() {
        return marca_venta;
    }

    public void setMarca_venta(String marca_venta) {
        this.marca_venta = marca_venta;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public double getCantidad_venta() {
        return cantidad_venta;
    }

    public void setCantidad_venta(double cantidad_venta) {
        this.cantidad_venta = cantidad_venta;
    }

    public String getTipounidad_venta() {
        return tipounidad_venta;
    }

    public void setTipounidad_venta(String tipounidad_venta) {
        this.tipounidad_venta = tipounidad_venta;
    }

    public double getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(double fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public double getTotalpago_venta() {
        return totalpago_venta;
    }

    public void setTotalpago_venta(double totalpago_venta) {
        this.totalpago_venta = totalpago_venta;
    }
}

package com.mikolajewald.sklepzubraniami.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private int id;
    private String nazwa;
    private String rodzaj;
    private double Cena;
    private String marka;
    private String plec;
    private String rozmiar;
    private String color;
    private int ilosc;

    public Item() {
    }

    public Item(String nazwa, String rodzaj, double cena, String marka, String plec, String rozmiar, String color, int ilosc) {
        this.nazwa = nazwa;
        this.rodzaj = rodzaj;
        Cena = cena;
        this.marka = marka;
        this.plec = plec;
        this.rozmiar = rozmiar;
        this.color = color;
        this.ilosc = ilosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public double getCena() {
        return Cena;
    }

    public void setCena(double cena) {
        Cena = cena;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getRozmiar() {
        return rozmiar;
    }

    public void setRozmiar(String rozmiar) {
        this.rozmiar = rozmiar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                ", rodzaj='" + rodzaj + '\'' +
                ", Cena=" + Cena +
                ", marka='" + marka + '\'' +
                ", plec='" + plec + '\'' +
                ", rozmiar='" + rozmiar + '\'' +
                ", color='" + color + '\'' +
                ", ilosc=" + ilosc +
                '}';
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbb;

/**
 *
 * @author danielhiggins
 */
public class ConfDB {
    String c, yf, yn;
    int w, l, ap, ncaa, ff, nc;
    double wp, srs, sos;

    public ConfDB(String c, String yf, String yn, int w, int l, double wp,
            double srs, double sos, int ap, int ncaa, int ff, int nc) {
        this.c = c;
        this.yf = yf;
        this.yn = yn;
        this.w = w;
        this.l = l;
        this.wp = wp;
        this.srs = srs;
        this.sos = sos;
        this.ap = ap;
        this.ncaa = ncaa;
        this.ff = ff;
        this.nc = nc;

    }
}

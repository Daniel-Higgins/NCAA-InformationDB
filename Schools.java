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
public class Schools {
    
        int pop, conf, did;
        int oneSeed=0;
        int championships=0;
        double latitude,longitude;
        private String name,nn,abbr,city,state;
        String yearsWon = "0";
        String[] closest = new String[4];
        
        public Schools(int c, int d, String re, String nn, String a, int p, String cit, String st, double la, String lo){
           
            pop = p;
            latitude = la;
            String newlo = "";
            for (int i = 1; i < lo.length(); i++) {
                newlo = newlo + lo.charAt(i);
            }
            longitude = Double.parseDouble(newlo);
            conf = c;
            name = re;
            this.nn = nn;
            abbr = a;
            city = cit;
            state = st;
        }
        
        public String getName(){
            return name;
        }
        
        public String nickName(){
            return nn;
        }
        
        public String getAbbrev(){
            return abbr;
        }
        
        public String getCity(){
            return city;
        }
        
        public String getState(){
            return state;
        }
    
    
}

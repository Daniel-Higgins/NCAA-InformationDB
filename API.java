/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author danielhiggins
 */
public class API {

    //this class has the main operations of this entire program
    //makes it easier to call from in an organized way
    private int count = 0;
    private LinkedList<Schools>[] sc;
    private HashMap<Integer, String> k = new HashMap<>();

    public void initialize() {
        try {
            loadC();
            sc = new LinkedList[count];
            File file = new File("coll_bb.csv");
            Scanner kb = new Scanner(file);

            kb.useDelimiter(",");

            while (kb.hasNext()) {

                Schools n = new Schools(kb.next().trim(), Integer.parseInt(kb.next()), kb.next(), kb.next(), kb.next(), Integer.parseInt(kb.next().trim()), kb.next(), kb.next(), Double.parseDouble(kb.next()), kb.nextLine());
                addS(n);

            }

            findNeigh();
            getChips();
            getN1seed();

        } catch (FileNotFoundException w) {
            System.out.println("Unable to open file");
        }
    }

    private void addS(Schools s) {
        try {
            int c = s.conf;
            System.out.println(c);
            if (sc[c] != null) {
                sc[c].add(s);

            } else {
                sc[c] = new LinkedList<>();
                sc[c].add(s);
            }
        } catch (Exception e) {

        }
    }

    private void loadC() {
        //loads the conferences file
        try {
            File f = new File("conf");
            Scanner kb = new Scanner(f);
            while (kb.hasNext()) {
                int key = kb.nextInt();
                String v = kb.nextLine();
                k.put(key, v.trim());
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getChips() {
        //finds total championships per team and the specific year
        try {
            String blogUrl = "https://en.m.wikipedia.org/wiki/NCAA_Division_I_Men%27s_Basketball_Tournament";
            Document doc = Jsoup.connect(blogUrl).get();

            Element links = doc.select("table").get(2); // gets the championship table
            Elements f = links.select("td");
            List<String> clist = f.eachText();

            for (int i = 0; i < clist.size(); i = i + 3) {
                //chipy
                String sch = clist.get(i).trim();
                int co = Integer.parseInt(clist.get(i + 1).trim());
                String years = clist.get(i + 2).trim();

                for (Schools d : getAllSchools()) {
                    if (sch.equalsIgnoreCase(d.getName().trim()) || sch.equalsIgnoreCase(d.getAbbrev().trim())) {
                        d.championships = co;
                        d.yearsWon = years;
                        break;
                    }
                }
            }
        } catch (IOException e) {

        }
    }

    private void getN1seed() {
        //retrieves how many times a school has been a #1 seed in the tournament
        try {
            String blogUrl = "https://en.wikipedia.org/wiki/NCAA_Division_I_Men%27s_Basketball_Tournament";
            Document doc = Jsoup.connect(blogUrl).get();
            HashMap<Integer, LinkedList> ch = new HashMap<>();
            Element links = doc.select("table").get(9); // gets the championship table
            Elements f = links.select("td");
            List<String> clist = f.eachText();

            for (int i = 0; i < clist.size(); i = i + 2) {
                int num = Integer.parseInt(clist.get(i));
                String ss = clist.get(i + 1).trim();
                //System.out.println(num);
                LinkedList<String> strings = new LinkedList<>();
                Scanner kb = new Scanner(ss);
                kb.useDelimiter(",");
                while (kb.hasNext()) {
                    String t = kb.next();
                    strings.add(t);
                }

                if (ch.get(num) == null || ch.get(num).isEmpty()) {
                    ch.put(num, strings);
                }

            }
            //findmax
            Set<Integer> sorted = new TreeSet<>(ch.keySet());
            Object[] so = sorted.toArray();

            for (int i = 0; i <= (int) so[so.length - 1]; i++) {
                if (ch.get(i) != null) {
                    for (int j = 0; j < ch.get(i).size(); j++) {
                        LinkedList<String> g = ch.get(i);
                        for (String q : g) {
                            for (Schools d : getAllSchools()) {
                                if (q.trim().equalsIgnoreCase(d.getName().trim()) || q.trim().equalsIgnoreCase(d.getAbbrev())) {
                                    d.oneSeed = i;
                                    break;
                                }
                            }
                        }
                    }

                }

            }
        } catch (FileNotFoundException w) {
            System.out.println("Unable to open file");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public LinkedList[] getAll() {
        return sc;
    }

    public HashMap<Integer, String> getK() {
        return k;
    }

    public LinkedList<Schools> getAt(int i) {
        //schools in a conference
        return sc[i];
    }

    public ArrayList<Schools> getAllSchools() {
        ArrayList<Schools> all = new ArrayList();
        for (int i = 0; i < sc.length; i++) {
            if (sc[i] != null) {
                for (int j = 0; j < sc[i].size(); j++) {
                    if (sc[i].get(j) != null) {
                        all.add(sc[i].get(j));
                    }
                }
            }
        }

        return all;
    }

    private void findNeigh() {
        for (Schools db : getAllSchools()) {
            HashMap<Double, String> ds = new HashMap<>();

            for (Schools tb : getAllSchools()) {
                if (!db.getName().equalsIgnoreCase(tb.getName())) {

                    double dist = Haversine.distance(db.latitude, db.longitude, tb.latitude, tb.longitude);
                    ds.put(dist, tb.getName());
                }

            }

            Set<Double> sorted = new TreeSet<>(ds.keySet());
            Object[] f = sorted.toArray();

            for (int i = 0; i <= 3; i++) {
                String q = ds.get(f[i]);
                db.closest[i] = q;
            }
        }
    }

}

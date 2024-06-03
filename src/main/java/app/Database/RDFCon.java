package app.Database;

import app.BadBase;
import app.DataObjectModels.*;
import org.apache.jena.rdfconnection.RDFConnection;
import app.Main;
import org.apache.jena.update.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class RDFCon implements AutoCloseable{
    private static final String PREFIX = "http://www.semanticweb.org/Arta/ontologies/2024/1/ProjectFire/";
    RDFConnection conn;
    static boolean firstTime = true;

    public RDFCon(){
        conn = RDFConnection.connect(Main.Fuseki);
        if(firstTime){
            conn.load(Main.KnowBaseLocation);
            firstTime = false;
        }
    }

    @Override
    public void close() {
        conn.close();
    }

    public <T extends Nameable> boolean CheckExistence(T obj){
        Class<?> clazz = obj.getClass();
        String queryString = "PREFIX : <"+ PREFIX +"> ASK WHERE {?obj a :"+clazz.getSimpleName()+" . ?obj ?p ?o . FILTER(?obj = :" + obj.getName() + ")}";
        return conn.queryAsk(queryString);
    }

    public <T extends Nameable> void Add(T obj){
        Class<?> clazz = obj.getClass();
        if(clazz==Material.class){
            return;
        }
        FullDelete(obj);
        StringBuilder sb = new StringBuilder();
        sb.append(":"+obj.getName()+" a :" + clazz.getSimpleName());
        Field[] fields = clazz.getDeclaredFields();
        for (Field f: fields) {
            try {
                if(f.get(obj)==null || f.getType()==RoomCategories.class || f.getName().equals("id")){
                    continue;
                }
                if (List.class.isAssignableFrom(f.getType())) {
                    for (Nameable o : (List<Nameable>) f.get(obj)) {
                        Add(o);
                        sb.append(" ; :has"+((Class<?>)((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0]).getSimpleName()+" :"+o.getName());
                    }
                }else{
                    sb.append(" ; :"+f.getName()+" \""+f.get(obj).toString()+"\"^^xsd:"+f.getType().getSimpleName().toLowerCase());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        String genericTTL = sb.append(" .").toString();
        String queryString = "PREFIX : <"+ PREFIX +"> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> INSERT DATA { "+genericTTL+" }";

        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessor qe = UpdateExecutionFactory.createRemote(request,
                Main.Fuseki+"/update");
        qe.execute();
    }

    static Dictionary<String, String[]> rememberClass = new Hashtable<>();
    public String[] ReadClass(String className){
        if(rememberClass.get(className)!=null){
            return rememberClass.get(className);
        }else{
            String queryString = "PREFIX : <"+ PREFIX +"> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> SELECT DISTINCT ?x WHERE {?x rdfs:subClassOf :"+className+" . }";
            List<String> results = new ArrayList<>();
            conn.queryResultSet(queryString, rsa -> {
                while (rsa.hasNext()){
                    results.add(rsa.next().getResource("x").getLocalName());
                }
            });
            results.sort(Comparator.naturalOrder());
            results.remove("Nothing");
            String[] strArr = new String[results.size()];
            strArr = results.toArray(strArr);
            rememberClass.put(className, strArr);
            return strArr;
        }
    }

    static Dictionary<String, String[]> rememberUnchangeableIndividuals = new Hashtable<>();
    public String[] ReadUnchangeableIndividuals(String className){
        if(rememberUnchangeableIndividuals.get(className)!=null){
            return rememberUnchangeableIndividuals.get(className);
        }else{
            String queryString = "PREFIX : <"+ PREFIX +"> SELECT DISTINCT ?x WHERE {?x a :"+className+" . }";
            List<String> results = new ArrayList<>();
            conn.queryResultSet(queryString, rsa -> {
                while (rsa.hasNext()){
                    results.add(rsa.next().getResource("x").getLocalName());
                }
            });
            results.sort(Comparator.naturalOrder());
            String[] strArr = new String[results.size()];
            strArr = results.toArray(strArr);
            rememberUnchangeableIndividuals.put(className, strArr);
            return strArr;
        }
    }

    public void DeleteLink(Nameable r, Nameable m){
        String genericTTL = ":"+r.getName()+" :hasMaterial :"+m.getName()+" .";
        String queryString = "PREFIX : <"+ PREFIX +"> DELETE DATA { "+genericTTL+" }";
        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessor qe = UpdateExecutionFactory.createRemote(request,
                Main.Fuseki+"/update");
        qe.execute();
    }

    public void FullDelete(Nameable o){
        String genericTTL = ":"+o.getName()+" ?y ?z .";
        String queryString = "PREFIX : <"+ PREFIX +"> DELETE WHERE { "+genericTTL+" }";
        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessor qe = UpdateExecutionFactory.createRemote(request,
                Main.Fuseki+"/update");
        qe.execute();
    }

    public ArrayList<String> GetSoue(ProtectionObject po){
        ArrayList<String> results = selectQuery("SOUEType", po.getName());

        if(results.contains("СОУЭ-4") || results.contains("СОУЭ-5")){
            results.clear();
            results.add("СОУЭ-4");
            results.add("СОУЭ-5");
        }else{
            String temp = results.get(results.size()-1);
            results.clear();
            results.add(temp);
        }

        return results;
    }

    public ArrayList<String> GetNpv(ProtectionObject po) {
        ArrayList<String> l = new ArrayList<>();

        if(Arrays.stream(BadBase.SpecialF).toList().contains(po.funcClass)){
            ArrayList<String> results = selectQuery("ExplosionAndFireHazard", po.getName());

            int litresPerSec = 0;
            if(po.area<3000) litresPerSec = 10;
            else if (po.area<5000) litresPerSec = 15;
            else if (po.area<20000) litresPerSec = 20;
            else if (po.area<50000) litresPerSec = 35;
            else if (po.area<200000) litresPerSec = 60;
            else if (po.area<400000) litresPerSec = 70;
            else if (po.area<600000) litresPerSec = 80;
            else if (po.area<800000) litresPerSec = 90;
            else litresPerSec = 100;

            int a = po.area>150?2:1;
            int t = results.contains("EAFH-А")||results.contains("EAFH-Б")||results.contains("EAFH-В")?3:2;
            int Tr = po.funcClass.equals(BadBase.SpecialF[2])?72:litresPerSec<=20?results.contains("EAFH-А")||results.contains("EAFH-Б")||results.contains("EAFH-В")?36:48:24;
            int Dt = po.funcClass.equals(BadBase.SpecialF[2])?75:100;
            int W = litresPerSec * a * t;
            double IsV = W - Dt*Dt*0.00048*Tr;
            if(IsV>0){
                int N = litresPerSec>40?2:1;
                int V = 2 / N * W;
                l.add(""+N+" резервуар"+(N>1?"a":"")+" объемом "+V+" литров");
            }else{
                l.add(BadBase.NPV[0]);
            }
        }else{
            l.add(BadBase.NPV[0]);
        }

        return l;
    }

    public ArrayList<String> GetOgnetush(Room r){
        ArrayList<String> l = new ArrayList<>();

        ArrayList<String> results = selectQuery("OgnetushClass", r.getName());
        ArrayList<String> eafh = selectQuery("ExplosionAndFireHazard", r.getName());

        int fireDist = eafh.contains("EAFH-А")?20:eafh.contains("EAFH-Б")?30:eafh.contains("EAFH-В")||eafh.contains("EAFH-Г")?40:70;
        int amount = (int) Math.ceil(Math.sqrt(r.width*r.width+r.lenght*r.lenght)/fireDist);
        for (String ognetush:results) {
            l.add(ognetush+" огнетушитель, "+amount+" шт");
        }

        return l;
    }

    private ArrayList<String> selectQuery(String classOWL, String o) {
        String queryString =
                "PREFIX : <" + PREFIX + ">\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "select ?z\n" +
                "where{\n" +
                "  ?x a ?z; a :Objects.\n" +
                "  ?z rdfs:subClassOf :"+classOWL+".\n" +
                "  FILTER ( ?x = :" + o + " )\n" +
                "  FILTER ( ?z != :"+classOWL+" )" +
                "}";
        ArrayList<String> results = new ArrayList<>();
        conn.queryResultSet(queryString, rsa -> {
            while (rsa.hasNext()) {
                results.add(rsa.next().getResource("z").getLocalName());
            }
        });
        results.sort(Comparator.naturalOrder());
        return results;
    }

    public ArrayList<String> GetSps(ProtectionObject po, Room r) {
        ArrayList<String> l = new ArrayList<>();

        String sps = selectQuery("SPSType", po.getName()).get(0);
        ArrayList<String> zkps = selectQuery("ZKPSAlgorythmType", r.getName());
        ArrayList<String> ipType = selectQuery("FireDetectorType", r.getName());

        for (String ip: ipType) {
            double radius = (ip.equals("Тепловой")||ip.equals("Пламенной"))?2.85:5.35;
            int amount = (int) Math.ceil(r.area/(Math.PI*radius*radius));
            for (String zk: zkps) {
                l.add(sps+" и "+amount+" шт "+ip+" ИП; "+zk);
            }
        }

        return l;
    }

    public ArrayList<String> GetVpv(ProtectionObject po) {
        ArrayList<String> results = selectQuery("FireHydrantType", po.getName());

        return results;
    }

    public ArrayList<String> GetAup(Room room) {
        ArrayList<String> results = selectQuery("AUPs", room.getName());
        if(results.size()==0){
            results.add(BadBase.NPV[1]);
        }

        return results;
    }

    public ArrayList<String>[] GetTrebAndRec(ProtectionObject po){
        ArrayList<String> l = new ArrayList<>();
        String soue = po.SOUE;
        soue = soue.replace("СОУЭ", "SOUE");

        String queryString =
                "PREFIX : <"+PREFIX+">\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "select ?z\n" +
                "where{\n" +
                "  ?x ?y ?z.\n" +
                "  Filter(?y=:mustHaveSOUESystem)\n" +
                "  Filter(?x=:"+soue+")\n" +
                "}";
        ArrayList<String> results1 = new ArrayList<>();
        conn.queryResultSet(queryString, rsa -> {
            while (rsa.hasNext()) {
                results1.add(rsa.next().getResource("z").getLocalName());
            }
        });
        results1.sort(Comparator.naturalOrder());

        queryString =
                "PREFIX : <"+PREFIX+">\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "select ?z\n" +
                "where{\n" +
                "  ?x ?y ?z.\n" +
                "  Filter(?y=:canHaveSOUESystem)\n" +
                "  Filter(?x=:"+soue+")\n" +
                "}";
        ArrayList<String> results2 = new ArrayList<>();
        conn.queryResultSet(queryString, rsa -> {
            while (rsa.hasNext()) {
                results2.add(rsa.next().getResource("z").getLocalName());
            }
        });
        results2.sort(Comparator.naturalOrder());

        return new ArrayList[]{results1, results2};
    }
}

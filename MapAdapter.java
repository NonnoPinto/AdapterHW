package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapAdapter implements HMap {
    
    private Enumeration keys;
    private Enumeration values;
    private Hashtable hash;
    
    //default contructor
    public MapAdapter(){
        keys = new Enumeration();
        values = new Enumeration();
        hash = new Hashtable();
    }

    //copy construcor
    public MapAdapter(MapAdapter m){
        keys = m.keys;
        values = m.values;
        hash = m.hash;
    }

    @Override
    public int size() {
        return this.hash.size();
    }

    @Override
    public boolean isEmpty() {
        return this.hash.isEmpty();
    }

    @Override
    public boolean containsKey(Object key){
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsValue(Object value){
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object get(Object key){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object put(Object key, Object value){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(HMap t){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public HSet keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HCollection values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HSet entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(Object o){
        // TODO Auto-generated method stub
        return false;
    }

    public int hashCode(){
        // TODO Auto-generated method stub
        return -1;
    }
    
}

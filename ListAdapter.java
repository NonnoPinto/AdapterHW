package myAdapter;

import java.util.NoSuchElementException;
import java.util.Vector;

//There arent any ClassCastExcpetion (and similar exception) cuz we r supposing everything is Object

public class ListAdapter implements HList{

    private Vector myVec;
    private Subber sub;

    //default constructor
    public ListAdapter(){
        myVec = new Vector();
        sub = null;
    }

    //copy constructor
    public ListAdapter(ListAdapter list){
        myVec = list.myVec;
        sub = null;
    }

    //costructor
    public ListAdapter(Vector a){
        myVec = new Vector();
        for (int i = 0; i < a.size(); i ++)
            myVec.addElement(a.elementAt(i));
        sub = null;
    }

    //subList helper constructor
    public ListAdapter(int from, int to, ListAdapter l, Vector a){
        myVec = l.myVec;
        sub = new Subber(from, to, l);
    }

    @Override
    public int size() {
        return this.myVec.size();
    }

    @Override
    public boolean isEmpty() {
        return this.myVec.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null)
            throw new NullPointerException();
        
        return this.myVec.contains(o);
    }

    @Override
    public HIterator iterator() {
        IteratorAdapter iter = new IteratorAdapter(this);
        return iter;
    }

    @Override
    public Object[] toArray() {
        Object[] myArray = new Object[this.myVec.size()];
        this.myVec.copyInto(myArray);
        return myArray;
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a==null)
            throw new NullPointerException();
        
        if (a.length < this.myVec.size())
            a = new Object[this.myVec.size()];
        
        this.myVec.copyInto(a);
        
        return a;
    }

    @Override
    public boolean add(Object o) {
        if (o == null)
            throw new NullPointerException();
               
        this.myVec.addElement(o);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
            throw new NullPointerException();

        return this.myVec.removeElement(o);
    }

    @Override
    public boolean containsAll(HCollection c) {
        if (c.contains(null) || c == null) //here and in similar piece of code the check "c.contains(null)" isnt really usefull: add doesnt accept "null" as argoument                                            
            throw new NullPointerException(); //but this.myVec way prevent this.myVec integrity
        
        HIterator tmp = c.iterator();

        while(tmp.hasNext())
            if (!(this.myVec.contains(tmp.next()))) //when a single element doesnt match, return false
                return false;

        return true;
    }

    @Override
    public boolean addAll(HCollection c) {
        if (c.contains(null))
            throw new NullPointerException();
        else if (c == null)
            return false;
        
        HIterator tmp = c.iterator();

        while(tmp.hasNext())
            this.myVec.addElement(tmp.next());
        
        return true;
    }

    @Override
    public boolean addAll(int index, HCollection c) {
        if (c.contains(null))
            throw new NullPointerException();
        else if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();
        else if (c == null)
            return false;

        HIterator tmp = c.iterator();
        while (tmp.hasNext()){
            this.myVec.insertElementAt(tmp.next(), index);
            index++;
        }

        return true;
    }

    @Override
    public boolean removeAll(HCollection c) {
        if (c.contains(null) || c == null)
            throw new NullPointerException();
        
        int oldSize = this.myVec.size(); //save old size
        HIterator tmp = c.iterator();
        while (tmp.hasNext())       //...removing...
            this.myVec.remove(tmp.next());
        
        if (this.myVec.size() == oldSize)    //if it has still the same size, nothing has been deleted
            return false;
        
        return true;
    }

    @Override
    public boolean retainAll(HCollection c) {
        if (c.contains(null) || c == null)
            throw new NullPointerException();
        
        int oldSize = this.myVec.size(); //save old size

        HIterator tmp = this.iterator();
        
        while (tmp.hasNext()){
            Object elem = tmp.next();
            if (!(c.contains(elem)))   //if HCollection c list doesnt have that element
                this.myVec.removeElement(elem); //then remove it from this list
        }

        if (this.myVec.size() == oldSize)    //if it has still the same size, nothing has been deleted
            return false;
        
        return true;
    }

    @Override
    public void clear() {
        this.myVec.removeAllElements();
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();
        
        return this.myVec.elementAt(index);
    }

    @Override
    public Object set(int index, Object element) {
        if (element == null)
            throw new NullPointerException();
        else if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();
        
        Object old = this.myVec.elementAt(index);   //saving the old element
        this.myVec.setElementAt(element, index);    //setting the new one

        return old;
    }

    @Override
    public void add(int index, Object element) {
        if (element == null)
            throw new NullPointerException();
        else if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();

        this.myVec.insertElementAt(element, index);
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();
        
        Object old = this.myVec.elementAt(index);   //saving old element, since remove from J2SE 1.1 is void
        this.myVec.removeElementAt(index);          //...removing...
        
        return old;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null)
            throw new NullPointerException();
    
        return this.myVec.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null)
            throw new NullPointerException();
        
        int in = -1;
        for (int i = 0; i < this.myVec.size(); i++)
            if (o == this.myVec.elementAt(i))
                in = i;
        
        return in;
    }

    @Override
    public HListIterator listIterator() {
        ListIteratorAdapter iter = new ListIteratorAdapter(this);
        return iter;
    }

    @Override
    public HListIterator listIterator(int index) {
        if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();

        ListIteratorAdapter iter = new ListIteratorAdapter(this);
        
        for (int i = 0; i < index; i++)
            iter.next();

        return null;
    }

    @Override
    public HList subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > this.myVec.size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        Vector subVec = new Vector();
        
        for (int i = fromIndex; i < toIndex; i++)
            subVec.addElement(this.myVec.elementAt(i));

        ListAdapter sub = new ListAdapter(fromIndex, toIndex, this, subVec);

        return sub;
    }
    
    @Override
    public int hashCode(){
        int hashCode = 1;
        HIterator i = this.iterator();
        while (i.hasNext()) {
            Object obj = i.next();
            hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
        }
        return hashCode;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof ListAdapter){   // o is istnace of ListAdpter
            HIterator myTmp = this.iterator();
            HIterator tmp = ((ListAdapter)o).iterator();
            while (myTmp.hasNext() && tmp.hasNext())    //if a single element doesnt match, return false
                if (myTmp.next() != tmp.next())
                        return false;
        }
        else                            //o !(istance of) ListAdapter
            return false;

        return true;                    //o is istance of ListAdapter AND all elements match at the same position
    }
    
    private class IteratorAdapter implements HIterator{
        private int index;
        private boolean next;
        ListAdapter list;

        IteratorAdapter(ListAdapter l){
            list = new ListAdapter(l);
            index = -1;
            next = false;
        }

        public boolean hasNext(){
            return (index < list.myVec.size());
        }

        public Object next(){
            if (index == list.myVec.size())
                throw new NoSuchElementException();
            
            next = true;

            return list.myVec.elementAt(index++);
        }

        public void remove(){
            if (next == false || index < 0)
                throw new IllegalStateException();
            
            next = false;
            list.myVec.removeElementAt(index);
            index--;
        }
    }

    private class ListIteratorAdapter implements HListIterator{
        
        IteratorAdapter iter;
        private boolean check;

        ListIteratorAdapter(ListAdapter l){
            iter = new IteratorAdapter(l);
            check = false;
        }

        /*ListIteratorAdapter(IteratorAdapter l){
            iter = new IteratorAdapter(l.list);
            check = false;
        }*/

        public void add(Object o){
            check = false;
            this.iter.list.myVec.insertElementAt(o, this.iter.index++);
        }
          
        public boolean hasNext(){
            return (this.iter.index < this.iter.list.myVec.size());
        }
                
        public boolean hasPrevious(){
            return  (this.iter.index > 0);
        }
                
        public Object next(){
            check = true;
            return this.iter.next();
        }
                
        public int nextIndex(){
            int myIndex;
            if (this.iter.index==this.iter.list.myVec.size())
                myIndex = this.iter.index;
            else
                myIndex = this.iter.index+1;

            return myIndex;
        }
                
        public Object previous(){
            if (this.iter.index == 0)
                throw new NoSuchElementException();
            
                check = true;
            
            return (this.iter.list.myVec.elementAt(this.iter.index--));
        }
                
        public int previousIndex(){
            int myIndex;
            if (this.iter.index==-1)
                myIndex = -1;
            else
                myIndex = this.iter.index-1;

            return myIndex;
        }
                
        public void remove(){
            if (check == false || this.iter.index < 0)
                throw new IllegalStateException();
            
            check = false;
            
            this.iter.list.myVec.removeElementAt(this.iter.index);
        }
                
        public void set(Object o){
            if (check == false || this.iter.index < 0)
                throw new IllegalStateException();
            
            check = false;

            this.iter.list.myVec.setElementAt(o, this.iter.index);
        }
    }

    private class Subber{
        protected int from;
        protected int to;
        ListAdapter original;
        
        Subber (int f, int t, ListAdapter o){
            from = f;
            to = t;
            original = o;
        }
    }
}
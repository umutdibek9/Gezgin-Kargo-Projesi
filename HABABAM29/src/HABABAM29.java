import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import sun.java2d.loops.SurfaceType;


public class HABABAM29 extends JFrame implements ActionListener{
    JLabel label = new JLabel();
    JLabel label2 = new JLabel();
    Image image = new ImageIcon(HABABAM29.class.getResource("/turkey402.png")).getImage();
    Image image2 = new ImageIcon(HABABAM29.class.getResource("/isaret.png")).getImage();
    Struct array=new Struct();
    int kordin[][];
    ArrayList<ArrayList<Integer>> sehirler;
    Timer timer = new Timer(1000,this);
    int paint_i;
    int sehir_kontrol=0;
    
    public static Struct goback(Struct array){
        while(array!=null){
            if(array.plaka==1) break;
            array=array.previous;
        }
        return array;
    }
    public static int minDistance(int dist[],Boolean set[]) {
        int min=Integer.MAX_VALUE;
        int min_index=-1;
        int i;
        for(i=0;i<81;i++){
            if(set[i]==false && dist[i]<=min){
                min=dist[i];
                min_index=i;
            }
        }
        
        return min_index;
        
    }
    public static int minDistance2(int komsuluk[][],Boolean set[],int src){
        
        int i,j;
        int res=Integer.MAX_VALUE;
        for(i=0;i<81;i++){
            if(komsuluk[src][i]!=0 && set[i]==false && res<komsuluk[src][i]){
                res=i;
            }
        }
        
        return i;
        
    }
    public static void printSolution(int dist[]) 
    { 
        int i;
        System.out.println("Vertex \t\t Distance from Source"); 
        for (i = 0; i < 81; i++){
            System.out.println(i + " \t\t " + dist[i]); 
        }
    } 
    public static int find(Struct array,int i){
        int ins=0;
        Struct temp=new Struct();
        temp=goback(array);
            while(!array.neig.get(i).equals(temp.cl)){
                if(temp.next==null) break;
                temp=temp.next;
                if(temp==null) break;
                if(array.neig.get(i).equals(temp.cl)) break;
            }
            
            ins=temp.plaka;
        return ins;
    }
    
    public static Struct djiekstra(Struct array,int n_m[][],int src) {
        
        Struct array2=new Struct();
        array2=array;
        int[] dist=new int[81];
        Boolean set[] = new Boolean[81]; 
        while(array!=null){
            array.plakano.clear();
            if(array.next==null) break;
            array=array.next;
        }
        array=goback(array);
        int i,j,t,z;
        int flag=0;
        int decrease=0;
        for(i=0;i<81;i++){
            dist[i]=Integer.MAX_VALUE;
            set[i]=false;
        }
        dist[src]=0;
        for(i=0;i<81;i++){
            int min=minDistance(dist,set);
            set[min]=true;
            for(j=0;j<81;j++){
                if(set[j]==false && n_m[min][j]!=0 && dist[min]!=Integer.MAX_VALUE && dist[min]+n_m[min][j]<dist[j]){
                    dist[j]=dist[min]+n_m[min][j];
                    while(array!=null){
                        if(array.plaka==j+1) break;
                        if(array.next==null) break;
                        array=array.next;
                    }
                    while(array2!=null){
                        if(array2.plaka==min+1) break;
                        if(array2.next==null) break;
                        array2=array2.next;
                    }
                    if(array2.plakano.size()>0){
                        for(z=0;z<array2.plakano.size();z++){
                            array.plakano.add(array2.plakano.get(z));
                        }
                        array.plakano.add(min);
                    }
                    else{
                        array.plakano.add(min);
                    }
                    array=goback(array);
                    array2=goback(array2);
            }
        }
        }
        while(array!=null){
            for(i=0;i<array.plakano.size();i++){
                if(array.plakano.get(i)==src && flag==0){
                    flag=1;
                }
                else if(array.plakano.get(i)==src && flag!=0){
                    for(j=0;j<i;j++){
                        array.plakano.remove(0);
                    }
                i=0;
                }
            }
            if(array.next==null) break;
            array=array.next;
            flag=0;
        }
        array=goback(array);
        /*while(array!=null){
            for(i=0;i<array.plakano.size();i++){
                System.out.print(array.plakano.get(i) + 1);
                System.out.print(" ");
            }
            System.out.println();
            if(array.next==null) break;
                array=array.next;
        }*/
        //printSolution(dist);
        return array;
        
    }
    public static int[] djiekstra2(int komsuluk[][],int src) {
        
        int dist[] = new int[81];
        Boolean set[] = new Boolean[81];
        int i;
        for (i = 0; i < 81; i++) { 
            dist[i] = Integer.MAX_VALUE; 
            set[i] = false; 
        } 
  
        dist[src] = 0; 
        for (int count = 0; count <81; count++) { 
            
            int min = minDistance(dist, set); 
  
            set[min] = true; 
  
            for (i=0;i<81;i++) 
  
                if (!set[i] && komsuluk[min][i] != 0 &&  
                   dist[min] != Integer.MAX_VALUE && dist[min] + komsuluk[min][i] < dist[i]) 
                    dist[i] = dist[min] + komsuluk[min][i]; 
        } 
        
        return dist;
        
    }
    
    public HABABAM29(ArrayList<Integer> hababam_list){
        
      kordin = new int[][]{
        {340,260},
        {440,220},
        {160,180},
        {600,130},
        {340,90},
        {230,140},
        {170,250},
        {560,70},
        {60,210},
        {70,130},
        {150,120},
        {520,160},
        {580,190},
        {200,100},
        {140,230},
        {110,110},
        {30,110},
        {260,90},
        {310,110},
        {110,210},
        {510,200},
        {30,60},
        {480,180},
        {470,140},
        {550,130},
        {180,140},
        {410,250},
        {440,100},
        {470,110},
        {650,220},
        {360,280},
        {170,220},
        {280,270},
        {110,70},
        {50,190},
        {600,100},
        {270,60},
        {360,180},
        {60,40},
        {290,160},
        {150,80},
        {230,200},
        {130,150},
        {430,190},
        {70,170},
        {390,220},
        {520,230},
        {80,240},
        {560,160},
        {310,180},
        {310,220},
        {410,90},
        {520,80},
        {160,100},
        {360,70},
        {580,210},
        {320,60},
        {400,140},
        {50,70},
        {380,110},
        {490,90},
        {480,160},
        {470,250},
        {120,180},
        {640,190},
        {330,140},
        {210,70},
        {280,200},
        {500,110},
        {260,250},
        {270,130},
        {550,220},
        {580,230},
        {240,60},
        {600,70},
        {640,120},
        {120,100},
        {250,80},
        {400,260},
        {370,250},
        {190,90}  
      };
      int i,j,t;
      timer.start();
      int d=0;
      paint_i=0;
        try(Scanner scanner=new Scanner(new BufferedReader(new FileReader("sehir.txt")));){
            String temporary;
            int count=0;
            int check=0;
            while(scanner.hasNextLine()){
                Struct second=new Struct();
                second.previous=null;
                int counter=0;
                int flag=0;
                temporary=scanner.nextLine();
                String[] keep=temporary.split(",");
                for(i=0;i<keep.length;i++){
                    if(keep[i].charAt(0)>=65 && 122>=keep[i].charAt(0) && counter==0){
                        second.cl=keep[i];
                        counter++;
                    }
                    else if(keep[i].charAt(0)>=65 && 122>=keep[i].charAt(0) && counter!=0){
                        second.neig.add(keep[i]);
                        counter++;
                    }
                    else if(keep[i].charAt(0)>=48 && keep[i].charAt(0)<=57 && counter==0 && flag==0){
                        second.plaka=Integer.parseInt(keep[i]);
                        flag=1;
                    }
                    else if(keep[i].charAt(0)>=48 && keep[i].charAt(0)<=57 && counter!=0 && flag!=0){
                        second.distant.add(Integer.parseInt(keep[i]));
                    }
                    if(keep.length==i+1){
                        if(check==0){
                            array=second;
                            array.previous=null;
                            check++;
                        }
                        else{
                            Struct backup=new Struct();
                            backup=array;
                            while(array!=null){
                                if(array.next==null) break;
                                array=array.next;
                            }if(array==null) System.out.println("Hello");
                            array.previous=backup;
                            array.next=second;
                        }
                    }
                }
            }
        }catch(IOException ex){
            System.out.println("askmds");
        }
        Struct temp2=new Struct();
        temp2=array;
        array=array.next;
        array.previous=temp2;
        int[][] komsuluk=new int[81][81];
        array=goback(array);
        i=0;
        j=0;
        while(array!=null){
            for(i=0;i<array.neig.size();i++){
                array.llist.add(find(array, i));
            }
            if(array.plaka==81) break;
            array=array.next;
        }
        array=goback(array);
        /*while(array!=null){
            for(i=0;i<array.llist.size();i++){
            System.out.println(array.llist.get(i));
            }
            if(array.next==null) break;
            array=array.next;
        }*/
        for(i=0;i<81;i++){
            for(j=0;j<81;j++){
                komsuluk[i][j]=0;
            }
        }
        while(array!=null){
            for(i=0;i<array.llist.size();i++){
                komsuluk[array.plaka-1][array.llist.get(i)-1]=array.distant.get(i);
            }
            if(array.next==null) break;
            array=array.next;
        }
        array=goback(array);
        int dist=0;
        int list[]=new int[hababam_list.size()];
        int list_;
        for(i=0;i<list.length;i++){
            list[i]=hababam_list.get(i);
        }
        int src=40;
        Boolean set[]=new Boolean[list.length];
        int p=0;
        ArrayList<Integer> list9=new ArrayList<Integer>();
        for(i=0;i<list.length;i++){
            set[i]=false;
        }
        int temp[]=new int[81];
        temp=djiekstra2(komsuluk, src);
        for(t=0;t<list.length-1;t++){
            if(temp[list[t]]>temp[list[t+1]] && list[t+1]!=40){
                int keep=list[t];
                list[t]=list[t+1];
                list[t+1]=keep;
                t=-1;
            }
        }
        for(t=0;t<list.length-1;t++){
            if(list[t]==58 || list[t]==38 || list[t]==33 || list[t]==21){
                list9.add(list[t]);
                d=1;
            }
        }
        for(t=0;t<list.length-1;t++){
            if(list[t]!=58 && list[t]!=38 && list[t]!=33 && list[t]!=21){
                list9.add(list[t]);
            }
        }
        for(t=0;t<list.length-1;t++){
            list[t]=list9.get(t);
        }
        sehirler = new ArrayList<ArrayList<Integer>>();
        p=0;
        for(i=0;i<list.length;i++){
            sehirler.add(new ArrayList());
        }
        int size=0;
        for(i=0;i<list.length;i++){
            Struct array5=djiekstra(array, komsuluk, src);
            temp=djiekstra2(komsuluk, src);
                    while(array5!=null){
                    if(array5.plaka-1==list[i] && set[i]==false){
                    for(p=0;p<array5.plakano.size();p++){
                        sehirler.get(size).add(array5.plakano.get(p));
                    }
                    for(p=0;p<sehirler.get(size).size();p++){
                        for(t=0;t<list.length-1;t++){
                        if(sehirler.get(size).get(p)==list[t] && set[t]!=true){
                            set[t]=true;
                        }
                            }
                    }
                    src=list[i];
                    set[i]=true;
                }
                    if(array5.next==null) break;
                    array5=array5.next;
                }
            array5=goback(array5);
            if(i==list.length-1){
                if(size==3){
                    i=10;
                }
                else{
                    size++;
                    i=-1;
                    list9.clear();
                    if(size==1 && d==1){
                        for(t=0;t<list.length-1;t++){
                            if(list[t]!=58 && list[t]!=38 && list[t]!=33 && list[t]!=21){
                                list9.add(list[t]);
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            if(list[t]==58 || list[t]==38 || list[t]==33 || list[t]==21){
                                list9.add(list[t]);
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            list[t]=list9.get(t);
                        }
                    }
                    if(size==2 && d==1){
                        for(t=0;t<list.length-1;t++){
                            if(temp[list[t]]<temp[list[t+1]] && list[t+1]!=40){
                                int keep=list[t];
                                list[t]=list[t+1];
                                list[t+1]=keep;
                                t=-1;
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            if(list[t]==58 || list[t]==38 || list[t]==33 || list[t]==21){
                                list9.add(list[t]);
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            if(list[t]!=58 && list[t]!=38 && list[t]!=33 && list[t]!=21){
                                list9.add(list[t]);
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            list[t]=list9.get(t);
                        }
                    }
                    if(size==3 && d==1){
                        for(t=0;t<list.length-1;t++){
                            if(temp[list[t]]<temp[list[t+1]] && list[t+1]!=40){
                                int keep=list[t];
                                list[t]=list[t+1];
                                list[t+1]=keep;
                                t=-1;
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            if(list[t]!=58 && list[t]!=38 && list[t]!=33 && list[t]!=21){
                                list9.add(list[t]);
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            if(list[t]==58 || list[t]==38 || list[t]==33 || list[t]==21){
                                list9.add(list[t]);
                            }
                        }
                        for(t=0;t<list.length-1;t++){
                            list[t]=list9.get(t);
                        }
                    }
                    if(size==1 && d==0){
                        System.out.println(size);
                        for(t=0;t<list.length-1;t++){
                            if(temp[list[t]]>temp[list[t+1]] && list[t+1]!=40){
                                int keep=list[t];
                                list[t]=list[t+1];
                                list[t+1]=keep;
                                t=-1;
                            }
                        }
                    }
                    if(size==2 && d==0){
                        for(t=0;t<list.length-1;t++){
                            if(temp[list[t]]>temp[list[t+1]] && list[t+1]!=40){
                                int keep=list[t];
                                list[t]=list[t+1];
                                list[t+1]=keep;
                                t=-1;
                            }
                        }
                        int keep=list[list.length-2];
                        list[list.length-2]=list[list.length-3];
                        list[list.length-3]=keep;
                    }
                    if(size==3 && d==0){
                        for(t=0;t<list.length-1;t++){
                            if(temp[list[t]]>temp[list[t+1]] && list[t+1]!=40){
                                int keep=list[t];
                                list[t]=list[t+1];
                                list[t+1]=keep;
                                t=-1;
                            }
                        }
                        int keep=list[list.length-4];
                        list[list.length-3]=list[list.length-4];
                        list[list.length-4]=keep;
                    }
                    for(t=0;t<list.length;t++){
                        set[t]=false;
                    }
                    src=40;
                }
            }
        }  
        for(i=0;i<list.length;i++){
            for(j=0;j<sehirler.get(i).size();j++){
                System.out.println(sehirler.get(i).get(j) + 1);
            }
            sehirler.get(i).add(40);
            System.out.println();
        }  
        sehir_kontrol=1;
      getContentPane().setLayout(null);
      setSize(700,352);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
    }
    
    public void repaint(){
        super.repaint();
    }
    
    public void paint(Graphics g){
        g.drawImage(image, 0, 20, image.getWidth(this), image.getHeight(this), this);
        int i;
        for(i=0;i<sehirler.get(paint_i).size();i++){
        g.drawImage(image2, kordin[sehirler.get(paint_i).get(i)][0], kordin[sehirler.get(paint_i).get(i)][1], this);
        if(i==sehirler.get(paint_i).size()-1) break;
        try
{
    Thread.sleep(1000);
}
catch(InterruptedException ex)
{
    Thread.currentThread().interrupt();
}
        g.drawLine(kordin[sehirler.get(paint_i).get(i)][0], kordin[sehirler.get(paint_i).get(i)][1], kordin[sehirler.get(paint_i).get(i+1)][0], kordin[sehirler.get(paint_i).get(i+1)][1]);   
        }
                if(paint_i==3){
                    dispose();
                }
    }
    
    
  public static void HABABAM29 (String[] argv){
       
      
      
      
      
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        
        if(sehir_kontrol==1){
            if(paint_i<sehirler.size()-1){
                paint_i=paint_i+1;
                repaint();
            }
            else{
                paint_i=0;
                repaint();
            }
            
            
        }
            
    }
  
}
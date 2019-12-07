/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayesian;

import java.util.Scanner;

/**
 *
 * @author Shaf
 */
public class Bayesian2 {
    public String[] attribut = {"Umur","Kegemukan"};
    public String[] attributAge = {"muda","parubaya","tua"};
    public String[] attributObesity = {"gemuk","sangat gemuk","terlalu gemuk"};
    public String[][] data ={{"muda","gemuk","no"},
                             {"muda","sangat gemuk","no"},{"parubaya","gemuk","no"},
                             {"parubaya","Terlalu Gemuk","yes"},{"Tua","Terlalu Gemuk","yes"}};
    public String[] dataTest = new String[2];
    public String[][] hipotesa = new String[18][3];
    public double[] numProbability = new double[18];
    
     public void input(){
        Scanner input = new Scanner(System.in);
         System.out.println("input :{ Umur      : muda, parubaya, tua}");
         System.out.println("       { Kegemukan : gemuk,sangat gemuk, terlalu gemuk}\n");
        for(int i=0;i<dataTest.length;i++){
          System.out.print("masukan nilai attribut "+attribut[i]+" = ");
          dataTest[i]=input.nextLine();
        }
    }
    
    public double countingLabel(String status){
        int count=0;
         for(int i=0;i<data.length;i++){
            for(int j=0;j<3;j++){
              if(j==2&&data[i][2].equalsIgnoreCase(status)){
                  count++;
               }
            }
         }
         return(count);
    }
    
    public double[] countingFacts(String attribut[],String status,double label){
       double[] facts = new double[3];
        for(int i=0;i<attribut.length;i++){
          for(int j=0;j<data.length;j++){
            for(int k=0;k<2;k++){
            if(data[j][k].equalsIgnoreCase(attribut[i])){
                if(data[j][2].equalsIgnoreCase(status)){
                  facts[i]+=1;
              }
            }
           }
          }
          facts[i]=(facts[i]/data.length)/label;
        }
        return facts;
    }
   
    
    public void hipotesaStatement(){
       int idx=0;
       while(idx<18){
        for(int i=0;i<attributAge.length;i++){
          for(int j=0;j<attributObesity.length;j++){
            hipotesa[idx][0]=attributAge[i];
            hipotesa[idx][1]=attributObesity[j];
            if(idx<9){
               hipotesa[idx][2]="yes";
            }else{
               hipotesa[idx][2]="no";
            }
            idx++;
          }
        }
       } 
    }
    
    public void probability(double factsAge[],double[] factsObesity,String status,double label){
      int idx;
      if(status.equalsIgnoreCase("no")){
        idx=9;}
      else{
        idx=0;}
      for(int i=0;i<attributAge.length;i++){
          for(int j=0;j<attributObesity.length;j++){
              numProbability[idx]=(factsAge[i]*factsObesity[j])*label;
              idx++;
          } 
        }
    }
    
    public void generaceOutput(){
        for(int i=0;i<hipotesa.length;i++){
           int j=0;
            if(hipotesa[i][j].equalsIgnoreCase(dataTest[j])){
                if(hipotesa[i][j+1].equalsIgnoreCase(dataTest[j+1])){
                    if(numProbability[i]>numProbability[i+9]){
                        System.out.println("Status Hipertensi :"+hipotesa[i][j+2]);
                        break;
                    }else if(numProbability[i]==0.0&numProbability[i+9]==0.0){
                         System.out.println("Status Hipertensi :Tidak ada keputusan");
                         break;
                    } 
                    else{
                      System.out.println("Status Hipertensi :"+hipotesa[i+9][j+2]);
                      break;
                    }
                }
              }
          }  
    }

    
    public void inisialisasiProgram(){
        double[]factsAgeYes = new double[3];
        double[]factsAgeNo = new double[3];
        double[] factsObesityYes = new double[3];
        double[] factsObesityNo = new double[3];
        double yes,no;
        yes=countingLabel("yes")/data.length;
        no=countingLabel("no")/data.length;
        factsAgeYes=countingFacts(attributAge,"yes",yes);
        factsAgeNo=countingFacts(attributAge,"no",no);
        factsObesityYes=countingFacts(attributObesity,"yes",yes);
        factsObesityNo=countingFacts(attributObesity,"no",no);
        hipotesaStatement();
        probability(factsAgeYes,factsObesityYes,"yes",yes);
        probability(factsAgeNo,factsObesityNo,"no",no);
    }
    
    public void runProgram(){
        String again;
        Scanner in = new Scanner(System.in);
        inisialisasiProgram();
        do{
         input();
         generaceOutput();
         System.out.print("\ntry again (y/n) ? ");
         again = in.nextLine();
        }while(again.equals("y"));  
    }
    
    public static void main(String[] args) {
        Bayesian2 bayesian  = new Bayesian2();
        bayesian.runProgram();
    }  
}

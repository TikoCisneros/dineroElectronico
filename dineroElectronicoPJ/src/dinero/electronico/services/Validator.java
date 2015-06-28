package dinero.electronico.services;

public class Validator {
	
	public static boolean validarCI(String ci){
	    int suma=0;
	    if(ci.length()<=9){
	      return false;
	    }else{
	      int a[]=new int [ci.length()/2];
	      int b[]=new int [(ci.length()/2)];
	      int c=0;
	      int d=1;
	      
	      for (int i = 0; i < ci.length()/2; i++) {
	        a[i]=Integer.parseInt(String.valueOf(ci.charAt(c)));
	        c=c+2;
	        if (i < (ci.length()/2)-1) {
	          b[i]=Integer.parseInt(String.valueOf(ci.charAt(d)));
	          d=d+2;
	        }
	      }
	   
	      for (int i = 0; i < a.length; i++) {
	        a[i]=a[i]*2;
	        if (a[i] >9){
	          a[i]=a[i]-9;
	        }
	        suma=suma+a[i]+b[i];
	      }
	      
	      int aux=suma/10;
	      int dec=(aux+1)*10;
	      
	      if ((dec - suma) == Integer.parseInt(String.valueOf(ci.charAt(ci.length()-1)))){
	        return true;
	      }else{
	        if(suma%10==0 && ci.charAt(ci.length()-1)=='0'){
	          return true;
	        }else{
	          return false;
	        }
	      }
	  }
	}
}

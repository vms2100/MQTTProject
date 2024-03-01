
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class AuxFunc {
// Método para ler uma String:
public static String umaString (){
 String s = "";
 try{
 BufferedReader in = new BufferedReader ( new InputStreamReader (System.in));
 s= in.readLine();
 }
 catch (IOException e){
 System.out.println("Erro ao ler fluxo de entrada.");
 }
 return s;
}
// Método para ler um int:
public static int umInt(){
while(true){
try{
return Integer.parseInt(umaString().trim());
}
catch(NumberFormatException e){
System.out.println("Não é um inteiro válido!!!");
}
}
}
// Método para ler um byte:
public static byte umByte(){
while(true){
try{
return Byte.parseByte(umaString().trim());
}
catch(NumberFormatException e){
System.out.println("Não é um byte válido!!!");
}
}
}
// Método para ler um short:
public static short umShort(){
while(true){
try{
return Short.parseShort(umaString().trim());
}
catch(NumberFormatException e){
System.out.println("Não é um short válido!!!");
}
}
}
// Método para ler um long:
public static long umLong(){
while(true){
try{
return Long.parseLong(umaString().trim());
}
catch(NumberFormatException e){
System.out.println("Não é um long válido!!!");
}
}
}
//// Método para ler um float;
public static float umFloat(){
while(true){
try{
return Float.parseFloat(umaString().trim());
}
catch(NumberFormatException e){
System.out.println("Não é um float válido!!!");
}
}
}

// Método para ler um double:
public static double umDouble(){
while(true){
try{
 return Double.valueOf(umaString().trim());
}
catch(NumberFormatException e){
System.out.println("Não é um double válido!!!");
}
}
}
// Método para ler um char:
public static char umChar(){
while(true){
try{
 return umaString().charAt(0);
}
catch(Exception e){
System.out.println("Não é um char válido!!!");
}

}
}
// Método para ler um boolean:
public static boolean umBoolean(){
while(true){
try{
return Boolean.parseBoolean(umaString().trim());
}
catch(Exception e){
System.out.println("Não é um boolean válido!!!");
}
}
}
public static void Delay(int time){
	 try {
           TimeUnit.SECONDS.sleep(time);
       } catch (InterruptedException e) {

       }
}

static void Red(String message){
	System.out.println("\u001B[31m"+message+"\u001B[31m");
}
static void Green(String message){
	System.out.println("\u001B[32m"+message+"\u001B[0m");
}


@SuppressWarnings("rawtypes")
public static boolean equals(ArrayList List,ArrayList TempList){
	if (List.size() != TempList.size()) {
		return false;
	}else{
		for(int i=0;i<List.size();i++){
			if (!List.get(i).equals(TempList.get(i))){
				return false;
			}
		}
	}
	return true;
}
}
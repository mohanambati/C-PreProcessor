//***********************************************************************************************************************************************************
//   Import Files
//***********************************************************************************************************************************************************
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;
/**
 *
 * @author mohan
 */
public class prepro {
    

//***********************************************************************************************************************************************************
//   Main function
//***********************************************************************************************************************************************************

   public static String filename;
   int i = 0;
   public static void main(String[] args) {
            filename = args[0];
      String withoutcomments = Removecomments(filename);
          withoutcomments = Removeduplicates(withoutcomments);
          withoutcomments = Replacedefine(withoutcomments);
              //  System.out.println(withoutcomments);
          while (withoutcomments.contains("#include \"(.*)\"")) {System.out.println("yes"); withoutcomments = Expandheader(Replacedefine(withoutcomments));}
          while (withoutcomments.contains("#define (.*)")) {System.out.println("yes"); withoutcomments = Replacedefine(withoutcomments);}
          /*   try{
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        writer.println(withoutcomments);
        writer.close();
      } catch (IOException e) {
         // do something
      }*/
      System.out.println(withoutcomments);

    }
//***********************************************************************************************************************************************************
//   This function Removes Duplicate header files.
//***********************************************************************************************************************************************************   

    public static String Removeduplicates(String data)
    {
        String newline = " ";
        try{
                        if(data != null){
                        final Pattern pattern = Pattern.compile("#include (.+?)\\n");
                        if(pattern != null){
                     //     System.out.println("not null");
                        final Matcher matcher = pattern.matcher(data);
                        if(matcher.find()){
                        newline = (matcher.group(1)); 
                        newline = "#include "+newline;
                        String[] parts = data.split(newline);
                    //    System.out.println(parts.length);
                        if(parts.length > 2)
                        {
                          newline = parts[0]+newline+parts[1]+parts[2];
                          Removeduplicates(newline);
                        }
                        else return data;
                    }else newline = data;
                  //      System.out.println("#include "+newline);
                      }

                        }
                     }catch(Exception e){}
                     
                     return newline;

    }
 
//***********************************************************************************************************************************************************
//   This function remove comments
//***********************************************************************************************************************************************************

    public static String Removecomments(String filename)
    {
      //  System.out.println(filename);
        String withoutcomments = " ", expandheadr = " ";
        try
      { 
            
            
             try( BufferedReader br = new BufferedReader(new FileReader(filename))) {
                  
                    StringBuilder sb = new StringBuilder();
                    String line= br.readLine();
                    
                    while (line != null) {                 
                        sb.append(line);
                        sb.append(System.lineSeparator());
                       line = br.readLine();
                       
                    }
                    br.close();
                    String everything = sb.toString();
                    withoutcomments = everything.replaceAll("(/\\*(?:.|[\\n\\r])*?\\*/)|(//.*)", ""); 
                
                     try{
                        if(withoutcomments != null){
                        final Pattern pattern = Pattern.compile("#include \"(.+?)\"");
                        if(pattern != null){
                        final Matcher matcher = pattern.matcher(withoutcomments);
                        matcher.find();
                        filename = (matcher.group(1)); }
                        if(filename != null) expandheadr = expandheadr+withoutcomments.replaceFirst("#include \"(.*)\"",Removecomments(filename));
                        }
                     }catch(Exception e){expandheadr = expandheadr + withoutcomments;}
                        
                }    
      }
      catch(ArrayIndexOutOfBoundsException e)
      {
        System.out.println("Usage: java prepro <inputfilename>");
      }
         catch(FileNotFoundException e)
      {
        System.out.println("File Not found");
      }
         catch(IOException e)
      {
        System.out.println("IOException");
      }


     while (expandheadr.contains("#include \"(.*)")) {/*System.out.println("yes");*/ expandheadr = Expandheader(expandheadr);}
     return expandheadr;   
    }
//***********************************************************************************************************************************************************
//   This function expands header files.
//***********************************************************************************************************************************************************    
     
     public static String Expandheader(String line)
    {
        String expandheadr = " " ; 
  
              String withoutcomments = " ";
              withoutcomments = withoutcomments+line.replaceAll("(/\\*(?:.|[\\n\\r])*?\\*/)|(//.*)", ""); 
                
                     
                     try{
                        if(withoutcomments != null){
                        final Pattern pattern = Pattern.compile("#include \"(.+?)\"");
                        if(pattern != null){
                        final Matcher matcher = pattern.matcher(withoutcomments);
                        matcher.find();
                        filename = (matcher.group(1)); }
                        expandheadr = withoutcomments.replaceFirst("#include \""+filename+"\"",Removecomments(filename));
                        }
                     }catch(Exception e){expandheadr = expandheadr+withoutcomments;}
                     
                     return expandheadr;

    }
//***********************************************************************************************************************************************************
//   This function handles #define macros
//***********************************************************************************************************************************************************   

    public static String Replacedefine(String line)
    {
        String replacedefine = " ";
      Matcher matcher = null;
      String newline = Expandheader(line);
       String[] parts = null;
       String a= " ",b = " ";
                     try{
                        final Pattern pattern = Pattern.compile("#define\\s\\s*(.+(?!\";|\\);))\\n");
                        matcher = pattern.matcher(newline);
                        if(matcher.find()){
                        replacedefine = matcher.group(1);
                     //   System.out.println(replacedefine);
                       parts = replacedefine.split("\\s\\s*",2);  
                      // System.out.print("length: ");System.out.print(parts.length);

                       if(parts.length ==2)
                       if((parts[0]!=null) && (parts[1] != null) && !(replacedefine.contains("\";")) && !(replacedefine.contains("\\);")) ) {
                        
            newline = newline.replaceFirst("#define\\s\\s*(.+(?!,))\\n"," ");

                        if(!(replacedefine.contains("define")) && (replacedefine.contains("PLUS"))){
                        if(newline.contains("class"))
                        { newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_)",parts[1]);
                        newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_)",parts[1]);
                            newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_)",parts[1]);}
                        else {
                        newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_)",parts[1]);
                        newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_)",parts[1]);
                        newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_)"," ");
                        }
                        newline = newline.replaceAll("(?!#|_)"+parts[0]+"(?!\"|_)",parts[1]);
            }
            else if((replacedefine.contains("define")) && !(replacedefine.contains("PLUS")))
            {

                            newline = newline.replaceAll("(^!#|_)"+parts[0]+"(?!\"|_)",parts[1]);
                            a = parts[0]; b = parts[1];
            }
            else {
                            if(newline.contains("class") && replacedefine.contains("CO")){
                                 newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_|\\)\")",parts[1]);
                                newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_|\\)\")",parts[1]);
                                newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_|\\)\")",parts[1]);
                                newline = newline.replaceFirst("(?!#|_)"+parts[0]+"(?!\"|_|\\)\")",parts[1]);
                            }else newline = newline.replaceAll("(?!#|_)"+parts[0]+"(?!\"|_|\\)\")",parts[1]);}

            newline = Replacedefine(newline);
                         if(newline.contains("class")){
                        newline = newline.replaceFirst("(?!#|_)"+a+"(?!\"|_)",b);
                        newline = newline.replaceFirst("(?!#|_)"+a+"(?!\"|_)",b);}
                    }
                        } 
                        }       
                     catch(Exception e){System.out.println("Exception in Replacedefine"); e.printStackTrace(); }



                     return newline;

    }
   
    
    
}

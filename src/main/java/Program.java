import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        StringBuilder slovo = new StringBuilder();
        slovo.append('"');
        List<String> slova = new ArrayList<String>();
        try(FileReader reader = new FileReader("C:\\Users\\Usvel\\Desktop\\laba2Terminal\\DashaTelegramBot\\src\\main\\resources\\text.txt"))
        {
            // читаем посимвольно
            int c;
            int okNext = 0;
            while((c=reader.read())!=-1){
                if (okNext == 0) {
                    if ((char)c == '<') {
                        okNext++;
                    }
                    else {
                        okNext = 0;
                    }
                } else if (okNext == 1){
                    if ((char)c == 'p') {
                        okNext++;
                    }
                    else {
                        okNext = 0;
                    }
                }
                else if (okNext == 2) {
                    if ((char)c == '>') {
                        okNext++;
                    }
                    else {
                        okNext = 0;
                    }
                }
                else if (okNext == 3){
                    if ((char)c == '<'){
                        slovo.append('"');
                        slova.add(slovo.toString());
                        slovo.delete(0, slovo.length()-1);
                        okNext = 0;
                    }
                    else {
                        slovo.append((char)c);
                    }
                }
                //System.out.print((char)c);
            }
            System.out.println(slova);
            System.out.println(slova.size());
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
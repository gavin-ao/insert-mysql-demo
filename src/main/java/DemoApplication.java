import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by aoyonggang on 2022/4/18.
 */
public class DemoApplication {
    public static void main(String[] args) throws IOException{
        FileWriter writer = new FileWriter("/Users/aoyonggang/Downloads/1million.txt");
        for(int i=1; i<1000001;i++) {
            writer.write(i + ", axax, 23, 1" );
            writer.write("\n");
            if ((i % 100000) == 0)
            {
                writer.flush();
            }
        }
        writer.close();

    }
}

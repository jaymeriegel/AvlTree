import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) throws ParseException {
        TreeInterface tree = new TreeInterface();
        tree.readCSV("teste.csv");
        /*
        String cpf = "12345678";
        System.out.println(tree.findByCpf(Long.valueOf(cpf)));
        List<Integer> achou = tree.findAllByTerm("Ci");
        achou.forEach(System.out::println);


         */
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String initS = "01/02/1956";
        String finalS = "01/02/2930";
        Date init = formatter.parse(initS);
        Date finalD = formatter.parse(finalS);

        System.out.println("--------------------");
        List<Integer> achouDatas = tree.findAllBetweenDates(init, finalD);
        achouDatas.forEach(System.out::println);
    }
}

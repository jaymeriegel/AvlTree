import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TreeInterface {
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private final AvlTree<Long> treeCpf;
    private final AvlTree<String> treeName;
    private final AvlTree<Date> treeBirthDate;
    private final List<Pessoa> peoplesList;

    public TreeInterface() {
        this.treeCpf = new AvlTree<>();
        this.treeName = new AvlTree<>();
        this.treeBirthDate = new AvlTree<>();
        this.peoplesList = new ArrayList<>();
    }

    public void readCSV(String path) {
        String line = "";
        int countIndex = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] items = line.split(";");

                Pessoa p = new Pessoa(
                        Long.parseLong(items[0]),
                        Long.parseLong(items[1]),
                        items[2],
                        convertStringToDate(items[3]),
                        items[4]);
                insertPessoa(p, countIndex);
                peoplesList.add(p);
                countIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        showPessoa(peoplesList);
    }

    private Date convertStringToDate(String value) {
        try {
            return formatter.parse(value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private String convertDateToString(Date value) {
        return formatter.format(value);
    }

    private void insertPessoa(Pessoa p, int index) {
        treeName.insert(p.getNome(), index);
        treeCpf.insert(p.getNumeroCPF(), index);
        treeBirthDate.insert(p.getDataNascimento(), index);
    }

    private void showPessoa(List<Pessoa> pessoas) {
        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);
        st.setHeaders("CPF", "RG", "NOME", "DATA NASCIMENTO", "CIDADE NASCIMENTO");

        for (Pessoa p : pessoas) {
            st.addRow(String.format("%011d", p.getNumeroCPF()),
                    String.format("%010d", p.getNumeroRG()),
                    p.getNome(),
                    convertDateToString(p.getDataNascimento()),
                    p.getCidadeNascimento());
        }
        st.print();
    }

    public int findByCpf(Long cpf) {
        return treeCpf.search(cpf);
    }

    public List<Integer> findAllByTerm(String term) {
        return treeName.searchByTerm(term);
    }

    public List<Integer> findAllBetweenDates(Date init, Date end) {
        return treeBirthDate.searchByDate(init, end);
    }
}

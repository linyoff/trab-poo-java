
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import mvc.control.ConnectionFactory;
import mvc.model.DAO.PessoaDAO;
import mvc.model.Pessoa;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Aliny
 */
public class Teste {

    public Teste() {
        PessoaDAO pessoaDAO = new PessoaDAO();
        List<Pessoa> pessoas = pessoaDAO.lista(null);
        //imprimeLista(pessoas);
        //pessoaDAO.exclui(2);

        
        /*Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o novo nome:");
        String novoNome = scanner.nextLine();

        System.out.println("Informe o novo sexo (1 - para Masculino ou 2 - para Feminino):");
        int opcSex = Integer.parseInt(scanner.nextLine());
        String novoSexo;
        switch (opcSex) {
            case 1:
                novoSexo = "M";
                break;
            case 2:
                novoSexo = "F";
                break;
            default:
                throw new AssertionError();
        }
        
        System.out.println("Informe a nova data de nascimento (AAAA-MM-DD):");
        String novaDataNascimento = scanner.next();
        LocalDate novaData = LocalDate.parse(novaDataNascimento);

        System.out.println("Informe o novo login:");
        String novoLogin = scanner.next();

        System.out.println("Informe a nova senha:");
        String novaSenha = scanner.next();

        pessoaDAO.altera(9, novoNome, novoSexo, novaData, novoLogin, novaSenha);*/

        
        pessoas = pessoaDAO.lista(null);
        imprimeLista(pessoas);
    }

    public void imprimeLista(List<Pessoa> pessoas) {
        for (Pessoa p : pessoas) {
            System.out.println(p.toString());
        }
    }

    public static void main(String[] args) {
        new Teste();
    }
}

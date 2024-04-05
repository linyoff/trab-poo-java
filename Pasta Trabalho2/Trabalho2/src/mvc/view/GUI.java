/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.view;

import java.util.Scanner;
import mvc.control.Utils;

/**
 *
 * @author Aliny
 */
public class GUI {

    Scanner scanner = new Scanner(System.in, "UTF-8");

    public int menuLogin() {

        StringBuilder builder = new StringBuilder("");

        builder.append("SEJA BEM VINDO AO FitNow. Faça o seu login ou Cadastre-se\n\n");
        builder.append("\n0 - Para sair do programa");
        builder.append("\n1 - Login");
        builder.append("\n2 - Faça seu cadastro");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuLogado() {

        System.out.println("\nOlá " + Utils.getPessoaLogada().getNome());

        StringBuilder builder = new StringBuilder("");

        builder.append("\n0-Para sair da sua conta");
        builder.append("\n1 - Exibir pessoas");
        builder.append("\n2 - Excluir pessoas");
        builder.append("\n3 - Exibir seus dados");
        builder.append("\n4 - Alterar dados");
        builder.append("\n5 - Posts");
        builder.append("\n6 - Menu de Seguir");
        builder.append("\n7 - Menu de mensagens");
        builder.append("\n8 - Menu de Avaliacao Fisica");
        builder.append("\n9 - Menu de Dieta");
        builder.append("\n10 - Menu de Alimento Receita");
        builder.append("\n11 - Menu de Refeicoes");
        builder.append("\n12 - Menu de Registro de Refeicoes");
        builder.append("\n13 - Menu de Alimentos de Preferencia");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuPosts() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\nBem-vindo ao menu de Posts");
        builder.append("\n0-Para sair do menu de posts");
        builder.append("\n1-Para fazer um post");
        builder.append("\n2-Para alterar um post");
        builder.append("\n3-Para excluir um post");
        builder.append("\n4-Para ver seus posts");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuSeguir() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem-vindo ao menu de seguir");
        builder.append("\n0-Para sair do menu de seguir");
        builder.append("\n1-Para ver seguidores");
        builder.append("\n2-Para ver pessoas que sigo");
        builder.append("\n3-Para deixar de seguir uma pessoa");
        builder.append("\n4-Para seguir uma pessoa");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuMensagens() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem-vindo ao menu de Mensagens");
        builder.append("\n0-Para sair do menu de mensagens");
        builder.append("\n1-Para enviar uma mensagem");
        builder.append("\n2-Para excluir uma mensagem");
        builder.append("\n3-Para visualizar mensagens recebidas");
        builder.append("\n4-Para visualizar mensagens enviadas");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());

    }

    public int menuAvaliacoes() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem-vindo ao menu de avaliações físicas");
        builder.append("\n0-Para sair do menu de avaliações físicas");
        builder.append("\n1-Para fazer uma avaliação física");
        builder.append("\n2-Para atualizar a sua avaliação física");
        builder.append("\n3-Para remover a sua avaliação física");
        builder.append("\n4-Para gerar relatorio da sua avaliacao fisica");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuDieta() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem-vindo ao menu de Dieta");
        builder.append("\n0-Para sair do menu de dietas");
        builder.append("\n1-Para fazer uma nova dieta");
        builder.append("\n2-Para atualizar a sua dieta");
        builder.append("\n3-Para remover a sua dieta");
        builder.append("\n4-Para mostrar a sua dieta");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuAlimentoReceita() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem-vindo ao menu de Receitas");
        builder.append("\n0-Para sair do menu de receitas");
        builder.append("\n1-Para cadastrar um alimento");
        builder.append("\n2-Para alterar um alimento");
        builder.append("\n3-Para excluir um alimento");
        builder.append("\n4-Para ver alimentos cadastrados");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuAlimentoRefeicoes() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem-vindo ao menu de Refeicoes");
        builder.append("\n0-Para sair do menu de refeicoes");
        builder.append("\n1-Para criar seu cardápio de refeicoes");
        builder.append("\n2-Para ver seu cardápio de refeicoes");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuRegRefeicoes() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\nBem-vindo ao menu de Registro de Refeicoes");
        builder.append("\n0-Para sair do menu de registro de refeicoes");
        builder.append("\n1-Para registrar uma refeicao");
        builder.append("\n2-Para ver refeicoes registradas");
        builder.append("\n3-Para excluir uma refeicao");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }

    public int menuPreferencia() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\nBem-vindo ao menu de Preferencias de Alimentos");
        builder.append("\n0-Para sair do menu de preferencias");
        builder.append("\n1-Para cadastrar uma preferencia");
        builder.append("\n2-Para excluir uma preferencia");
        builder.append("\n3-Para ver preferencias cadastradas");
        builder.append("\nQual sua opção ? R: ");
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
}

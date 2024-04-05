/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.control;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import mvc.model.AlimentoReceita;
import mvc.model.AlimentoRefeicoes;
import mvc.model.AvaliacaoFisica;
import mvc.model.DAO.PessoaDAO;
import mvc.model.Pessoa;
import mvc.model.Mensagem;
import mvc.model.RegistroDieta;
import mvc.model.Post;
import mvc.model.Refeicoes;
import mvc.model.TipoDieta;
import mvc.model.DAO.SeguirDAO;
import mvc.model.DAO.PostDAO;
import mvc.model.DAO.AlimentoReceitaDAO;
import mvc.model.DAO.MensagemDAO;
import mvc.model.DAO.PreferenciaAlimentoDAO;
import mvc.model.DAO.AvaliacaoFisicaDAO;
import mvc.model.DAO.TipoDietaDAO;
import mvc.model.DAO.RegistroDietaDAO;
import mvc.model.DAO.RefeicoesDAO;
import mvc.model.DAO.AlimentosRefeicoesDAO;
import mvc.view.GUI;

/**
 *
 * @author Aliny
 */
public class Main {

    GUI gui = new GUI();
    Scanner scanner = new Scanner(System.in);
    Pessoa usuarioLogado;

    //inicializando DAOs 
    PessoaDAO pessoaDAO = new PessoaDAO();
    SeguirDAO seguirDAO = new SeguirDAO(pessoaDAO);
    PostDAO postDAO = new PostDAO(pessoaDAO, seguirDAO);
    MensagemDAO mensagemDAO = new MensagemDAO(pessoaDAO, seguirDAO);
    AlimentoReceitaDAO alimentoRcDAO = new AlimentoReceitaDAO(pessoaDAO);
    PreferenciaAlimentoDAO preferenciaDAO = new PreferenciaAlimentoDAO(pessoaDAO, alimentoRcDAO);
    AvaliacaoFisicaDAO avaliacaoFDAO = new AvaliacaoFisicaDAO(pessoaDAO);
    TipoDietaDAO tipoDietaDAO = new TipoDietaDAO();
    RegistroDietaDAO rgDietaDAO = new RegistroDietaDAO(pessoaDAO, avaliacaoFDAO, tipoDietaDAO);
    RefeicoesDAO refeicoesDAO = new RefeicoesDAO(pessoaDAO, rgDietaDAO);
    AlimentosRefeicoesDAO alimentoRfDAO = new AlimentosRefeicoesDAO(alimentoRcDAO, refeicoesDAO);

    public Main() {

        int opcaoUsuario;

        do {
            opcaoUsuario = gui.menuLogin();

            switch (opcaoUsuario) {
                case 1:
                    System.out.println("\n=====PÁGINA DE LOGIN=====");

                    System.out.print("Login: ");
                    String login = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    usuarioLogado = pessoaDAO.buscarAcesso(login, senha);

                    if (usuarioLogado != null) {

                        System.out.println("\nLogin bem-sucedido!");
                        Utils.setPessoaLogada(usuarioLogado);
                        int opcLogado;
                        do {
                            exibirTimeline(usuarioLogado);
                            opcLogado = gui.menuLogado();

                            switch (opcLogado) {
                                case 1:
                                    System.out.println("\n=====PÁGINA PARA LISTAR PESSOAS=====");
                                    List<Pessoa> pessoas = pessoaDAO.lista(null);
                                    pessoaDAO.imprimeLista(pessoas);
                                    break;

                                case 2:
                                    System.out.println("\n=====PÁGINA PARA EXCLUIR PESSOAS=====");
                                    System.out.print("Digite o id de quem deseja excluir: ");
                                    long id = Long.parseLong(scanner.nextLine());
                                    Pessoa pessoa = pessoaDAO.buscarPessoaPorId(id);
                                    if (pessoa != null) {
                                        pessoaDAO.exclui(id);
                                    } else {
                                        System.out.println("\nPessoa não existe");
                                    }
                                    break;
                                case 3:
                                    System.out.println(usuarioLogado.toString());
                                    break;
                                case 4:
                                    alterarDados();
                                    break;
                                case 5:
                                    post();
                                    break;
                                case 6:
                                    seguir();
                                    break;
                                case 7:
                                    mensagens();
                                    break;
                                case 8:
                                    avaliacoes();
                                    break;
                                case 9:
                                    dieta();
                                    break;
                                case 10:
                                    alimentoReceita();
                                    break;
                                case 11:
                                    alimentoRefeicoes();
                                    break;
                                case 12:
                                    regRefeicoes();
                                    break;
                                case 13:
                                    preferencias();
                                    break;
                                default:
                                    if (opcLogado != 0) {
                                        System.out.print("Opção inválida! Digite novamente: ");
                                    }
                            }
                        } while (opcLogado != 0);

                    } else {
                        System.out.println("Tentativa de login falhou");
                    }

                    break;

                case 2:
                    System.out.println("\n=====PÁGINA DE CADASTRO=====");

                    System.out.println("Informe seu nome:");
                    String nome = scanner.nextLine();

                    System.out.println("Informe o sexo (1 - para Masculino ou 2 - para Feminino):");
                    int opcSex = Integer.parseInt(scanner.nextLine());
                    String sexo;
                    switch (opcSex) {
                        case 1:
                            sexo = "M";
                            break;
                        case 2:
                            sexo = "F";
                            break;
                        default:
                            throw new AssertionError();
                    }

                    System.out.println("Informe a data de nascimento (AAAA-MM-DD):");
                    String data = scanner.next();
                    LocalDate dataNascimento = LocalDate.parse(data);

                    System.out.println("Informe um login:");
                    login = scanner.next();

                    System.out.println("Informe a nova senha:");
                    senha = scanner.next();

                    Pessoa p = new Pessoa(0, nome, sexo, dataNascimento, login, senha, dataNascimento, dataNascimento);
                    pessoaDAO.adiciona(p);
                    break;
                default:
                    if (opcaoUsuario != 0) {
                        System.out.print("Opção inválida! Digite novamente: ");
                    }
            }

        } while (opcaoUsuario
                != 0);

        System.out.println(
                "\nFinalizando o programa...\n");
    }

    //MAIN==================================
    public static void main(String[] args) {
        new Main();
    }

    //METÓDOS==================================
    public void exibirTimeline(Pessoa usuarioLogado) {

        if (usuarioLogado != null) {
            postDAO.timeline(usuarioLogado);
        } else {
            System.out.println("Você precisa estar logado para visualizar a timeline.");
        }
    }

    public void post() {

        try {
            int opc;
            do {
                opc = gui.menuPosts();
                String conteudoDaMensagem;
                switch (opc) {
                    case 1:
                        System.out.println("Fazer um post");
                        System.out.println("Digite o conteudo: ");
                        conteudoDaMensagem = scanner.nextLine();
                        Post postNovo = new Post(0, usuarioLogado, conteudoDaMensagem, LocalDate.now(), LocalDate.now());
                        postDAO.adiciona(postNovo);
                        break;
                    case 2:
                        System.out.println("Digite o id do post que deseja alterar: ");
                        long id1 = scanner.nextLong();
                        scanner.nextLine();
                        System.out.println("Digite o conteudo: ");
                        conteudoDaMensagem = scanner.nextLine();
                        Post post = new Post(id1, usuarioLogado, conteudoDaMensagem, LocalDate.now(), LocalDate.now());
                        postDAO.altera(post, usuarioLogado);
                        break;
                    case 3:
                        System.out.println("Digite o id do post que deseja excluir");
                        long id = scanner.nextLong();
                        scanner.nextLine();
                        postDAO.exclui(id, usuarioLogado);
                        break;
                    case 4:
                        postDAO.exibirMeusPosts(usuarioLogado);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de posts: " + e.getMessage());
        }

    }

    public void alterarDados() {
        try {
            long id = pessoaDAO.existePessoa(usuarioLogado);

            if (id != -1) {
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

                pessoaDAO.altera(id, novoNome, novoSexo, novaData, novoLogin, novaSenha);
            } else {
                System.out.println("Pessoa não encontrada. Não é possível realizar a alteração.");
            }
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao tentar verificar a existência da pessoa: " + e.getMessage());
        }
    }

    public void seguir() {
        try {
            int opc;
            do {
                opc = gui.menuSeguir();
                switch (opc) {
                    case 1:
                        seguirDAO.mostrarSeguidores(usuarioLogado);
                        break;
                    case 2:
                        seguirDAO.mostrarQuemEstouSeguindo(usuarioLogado);
                        break;
                    case 3:
                        System.out.println("Digite o nome da pessoa que deseja deixar de seguir: ");
                        String nomeSeguindo = scanner.nextLine();
                        Pessoa seguindo = pessoaDAO.buscarPessoaPorNome(nomeSeguindo);
                        seguirDAO.deixarDeSeguir(usuarioLogado, seguindo);

                        break;
                    case 4:
                        System.out.println("Digite o nome da pessoa que deseja seguir: ");
                        String nomeSeguir = scanner.nextLine();
                        Pessoa seguir = pessoaDAO.buscarPessoaPorNome(nomeSeguir);
                        seguirDAO.seguirPessoa(usuarioLogado, seguir);

                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de seguir: " + e.getMessage());
        }
    }

    public void mensagens() {
        try {
            int opc;
            do {
                opc = gui.menuMensagens();
                switch (opc) {
                    case 1:
                        System.out.println("Enviar mensagem");
                        System.out.println("Digite o nome da pessoa que deseja enviar a mensagem: ");
                        String nomeDestino = scanner.nextLine();
                        Pessoa destino = pessoaDAO.buscarPessoaPorNome(nomeDestino);
                        if (destino != null) {
                            System.out.println("Digite o conteúdo: ");
                            String conteudoDoTexto = scanner.nextLine();
                            LocalDate dataCriacao = LocalDate.now();
                            LocalDate dataModificacao = LocalDate.now();
                            Mensagem msg = new Mensagem(0, usuarioLogado, destino, conteudoDoTexto, dataCriacao, dataModificacao);
                            mensagemDAO.adiciona(msg);
                        } else {
                            System.out.println("Pessoa não encontrada!");
                        }
                        break;
                    case 2:
                        System.out.println("Digite o id da mensagem que deseja excluir: ");
                        long id = scanner.nextLong();
                        scanner.nextLine();
                        mensagemDAO.exclui(id, usuarioLogado);
                        break;
                    case 3:
                        System.out.println("------MENSAGENS RECEBIDAS-----");
                        mensagemDAO.exibirMensagensRecebidas(usuarioLogado);
                        break;

                    case 4:
                        System.out.println("------MENSAGENS ENVIADAS-----");
                        mensagemDAO.exibirMensagensEnviadas(usuarioLogado);
                        break;
                    default:
                        System.out.print("Opção inválida! Digite novamente: ");
                }
            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de mensagens: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void alimentoReceita() {
        try {
            int opc;
            do {
                opc = gui.menuAlimentoReceita();

                switch (opc) {
                    case 1:
                        System.out.println("Digite o nome da receita: ");
                        String nomeReceita = scanner.nextLine();
                        System.out.println("Digite a quantidade de carboidratos: ");
                        double carbo = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite a quantidade de proteínas: ");
                        double prot = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite a quantidade de gorduras: ");
                        double gord = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite a porção: ");
                        double porc = Double.parseDouble(scanner.nextLine());
                        AlimentoReceita ar = new AlimentoReceita(0, usuarioLogado, nomeReceita, carbo, prot, gord, porc, LocalDate.now(), LocalDate.now());
                        alimentoRcDAO.adiciona(ar);
                        break;
                    case 2:
                        System.out.println("Digite o id da receita que deseja alterar: ");
                        long id = scanner.nextLong();
                        scanner.nextLine();
                        System.out.println("Digite o nome da receita: ");
                        String novoNomeReceita = scanner.nextLine();
                        System.out.println("Digite a quantidade de carboidratos: ");
                        double novoCarbo = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite a quantidade de proteínas: ");
                        double novaProt = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite a quantidade de gorduras: ");
                        double novaGord = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite a porção: ");
                        double novaPorc = Double.parseDouble(scanner.nextLine());
                        AlimentoReceita arNovo = new AlimentoReceita(id, usuarioLogado, novoNomeReceita, novoCarbo, novaProt, novaGord, novaPorc, LocalDate.now(), LocalDate.now());
                        alimentoRcDAO.altera(arNovo, usuarioLogado);
                        break;
                    case 3:
                        System.out.println("Digite o id da receita que deseja excluir: ");
                        long id2 = scanner.nextLong();
                        scanner.nextLine();
                        alimentoRcDAO.exclui(id2, usuarioLogado);
                        break;
                    case 4:
                        alimentoRcDAO.exibirMeusAlimentos(usuarioLogado);
                        break;
                    default:
                        System.out.print("Opção inválida! Digite novamente: ");
                }

            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de alimento receita: " + e.getMessage());
            e.printStackTrace(); // Apenas para depuração, considere remover em um ambiente de produção.
        }
    }

    public void preferencias() {
        try {
            int opc;
            do {
                opc = gui.menuPreferencia();

                switch (opc) {
                    case 1:
                        System.out.println("Digite o id da receita que deseja dar preferencia: ");
                        long id = scanner.nextLong();
                        scanner.nextLine();
                        preferenciaDAO.adiciona(usuarioLogado, id);
                        break;
                    case 2:
                        System.out.println("Digite o id da preferência que deseja excluir: ");
                        long id2 = scanner.nextLong();
                        scanner.nextLine();
                        preferenciaDAO.exclui(usuarioLogado, id2);
                        break;
                    case 3:
                        preferenciaDAO.exibirMinhasPreferencias(usuarioLogado);
                        break;
                    default:
                        System.out.print("Opção inválida! Digite novamente: ");
                }

            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de preferências: " + e.getMessage());
            e.printStackTrace(); // Apenas para depuração, considere remover em um ambiente de produção.
        }
    }

    public void avaliacoes() {
        try {
            int opc;

            do {
                opc = gui.menuAvaliacoes();

                switch (opc) {
                    case 1:
                        if (avaliacaoFDAO.buscarAvaliacaoFisica(usuarioLogado) != null) {
                            System.out.println("\nVocê já possui uma avaliação");
                        } else {
                            System.out.println("Informe o seu Peso: ");
                            double peso = Double.parseDouble(scanner.nextLine());
                            System.out.println("Informe a largura do seu Pescoço: ");
                            double pescoco = Double.parseDouble(scanner.nextLine());
                            System.out.println("Informe o seu Quadril: ");
                            double quadril = Double.parseDouble(scanner.nextLine());
                            System.out.println("Informe a sua Cintura: ");
                            double cintura = Double.parseDouble(scanner.nextLine());
                            System.out.println("Informe a sua Altura: ");
                            double altura = Double.parseDouble(scanner.nextLine());
                            System.out.println("Informe o seu Fator de Atividade: 1) sedentário, 2) levemente ativo, 3) moderadamente ativo, 4) muito ativo, 5) extremamente ativo");
                            double fatorAtv = Double.parseDouble(scanner.nextLine());

                            AvaliacaoFisica af = new AvaliacaoFisica(0, usuarioLogado, pescoco, quadril, cintura, altura, peso, fatorAtv, LocalDate.now(), LocalDate.now());
                            avaliacaoFDAO.adiciona(af);
                            af.IMC();
                            af.TMB();
                            af.calcularPercentualGordura();
                            af.gerarRelatorioGorduraCorporal(af);
                        }
                        break;
                    case 2:

                        System.out.println("Informe o novo peso:");
                        double novoPeso = Double.parseDouble(scanner.nextLine());

                        System.out.println("Informe a nova medida do pescoço: ");
                        double novoPescoco = Double.parseDouble(scanner.nextLine());

                        System.out.println("Informe a nova medida do quadril:");
                        double novoQuadril = Double.parseDouble(scanner.nextLine());

                        System.out.println("Informe a nova medida da cintura:");
                        double novaCintura = Double.parseDouble(scanner.nextLine());

                        System.out.println("Informe a nova medida do altura:");
                        double novaAltura = Double.parseDouble(scanner.nextLine());

                        System.out.println("Informe o novo fator de atividade:");
                        double novoFatorAtividade = Double.parseDouble(scanner.nextLine());

                        avaliacaoFDAO.altera(usuarioLogado, novoPescoco, novoQuadril, novaCintura, novaAltura, novoPeso, novoFatorAtividade);

                        break;
                    case 3:
                        avaliacaoFDAO.exclui(usuarioLogado);
                        break;
                    case 4:
                        AvaliacaoFisica avaliacaoFisica = avaliacaoFDAO.buscarAvaliacaoFisica(usuarioLogado);
                        System.out.println("\nSeus dados de avaliação: " + avaliacaoFisica);
                        avaliacaoFisica.gerarRelatorioGorduraCorporal(avaliacaoFisica);
                        break;
                    default:
                        System.out.print("Opção inválida! Digite novamente: ");
                }

            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de avaliações: " + e.getMessage());
            e.printStackTrace(); // Apenas para depuração, considere remover em um ambiente de produção.
        }
    }

    public void dieta() {
        try {
            int opc;
            do {
                opc = gui.menuDieta();
                RegistroDieta rg;
                TipoDieta td;
                AvaliacaoFisica avaliacao;
                int op, numRef;
                long id;
                String obj;

                switch (opc) {
                    case 1:

                        avaliacao = avaliacaoFDAO.buscarAvaliacaoFisica(usuarioLogado);

                        if (avaliacao == null) {
                            System.out.println("Você precisa criar uma avaliacao fisica primeiro");
                        } else {

                            td = menuTipoDieta();

                            System.out.println("\nQual o seu objetivo? Pressione o numero do seu objetivo");
                            System.out.println("1)DIMINUIR O PESO");
                            System.out.println("2)MANTER O PESO");
                            System.out.println("3)MELHORAR COMPOSIÇÃO CORPORAL");
                            System.out.println("4)AUMENTAR O PESO");
                            System.out.println("Qual a sua opção? R: ");
                            op = Integer.parseInt(scanner.nextLine());
                            obj = "";
                            if (op == 1) {
                                obj = "DIMINUIR O PESO";
                            } else if (op == 2) {
                                obj = "MANTER O PESO";
                            } else if (op == 3) {
                                obj = "MELHORAR COMPOSICAO CORPORAL";
                            } else if (op == 4) {
                                obj = "AUMENTAR O PESO";
                            }

                            System.out.println("\nAgora digite o numero de refeicoes R: ");
                            numRef = Integer.parseInt(scanner.nextLine());

                            rg = new RegistroDieta(0, usuarioLogado, avaliacao, td, obj, numRef, LocalDate.now(), LocalDate.now());
                            rgDietaDAO.adiciona(rg);
                        }

                        break;
                    case 2:
                        System.out.println("Digite o id da dieta que deseja alterar: ");
                        id = scanner.nextLong();
                        scanner.nextLine();
                        rg = rgDietaDAO.buscarRegistroDietaPorId(id);
                        if (rg != null && rg.getP().getId() == usuarioLogado.getId()) {

                            avaliacao = avaliacaoFDAO.buscarAvaliacaoFisica(usuarioLogado);

                            if (avaliacao == null) {
                                System.out.println("Você precisa criar uma avaliacao fisica primeiro");
                            } else {

                                td = menuTipoDieta();

                                System.out.println("\nQual o seu objetivo? Pressione o numero do seu objetivo");
                                System.out.println("1)DIMINUIR O PESO");
                                System.out.println("2)MANTER O PESO");
                                System.out.println("3)MELHORAR COMPOSIÇÃO CORPORAL");
                                System.out.println("4)AUMENTAR O PESO");
                                System.out.println("Qual a sua opção? R: ");
                                op = Integer.parseInt(scanner.nextLine());
                                obj = "";
                                if (op == 1) {
                                    obj = "DIMINUIR O PESO";
                                } else if (op == 2) {
                                    obj = "MANTER O PESO";
                                } else if (op == 3) {
                                    obj = "MELHORAR COMPOSICAO CORPORAL";
                                } else if (op == 4) {
                                    obj = "AUMENTAR O PESO";
                                }

                                System.out.println("\nAgora digite o numero de refeicoes R: ");
                                numRef = Integer.parseInt(scanner.nextLine());
                                rgDietaDAO.altera(usuarioLogado, id, td, obj, numRef, numRef);
                            }
                        } else {
                            System.out.println("Você não possui um registro de dieta");
                        }
                        break;

                    case 3:
                        System.out.println("Digite o id da dieta que deseja excluir: ");
                        id = scanner.nextLong();
                        scanner.nextLine();
                        rg = rgDietaDAO.buscarRegistroDietaPorId(id);
                        if (rg != null && rg.getP().getId() == usuarioLogado.getId()) {
                            rgDietaDAO.exclui(id, usuarioLogado);
                        } else {
                            System.out.println("Você não possui um registro de dieta");
                        }

                        break;
                    case 4:
                        rgDietaDAO.exibirMeusRegistrosDieta(usuarioLogado);
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                }

            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de dieta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TipoDieta menuTipoDieta() {

        int escolha;
        List<TipoDieta> tiposDieta = tipoDietaDAO.lista();
        TipoDieta tp;

        System.out.println("1) Dieta Equilibrada");
        System.out.println("2) Dieta Low Carb");
        System.out.println("3) Dieta Cetogênica");
        System.out.print("Escolha um tipo de dieta para continuarmos: ");

        escolha = Integer.parseInt(scanner.nextLine());

        switch (escolha) {
            case 1:
                tp = tiposDieta.get(0);
                System.out.println("Tipo de Dieta atribuida com sucesso a " + usuarioLogado.getNome());
                return tp;

            case 2:
                tp = tiposDieta.get(1);
                System.out.println("Tipo de Dieta atribuida com sucesso a " + usuarioLogado.getNome());

                return tp;
            case 3:
                tp = tiposDieta.get(2);
                System.out.println("Tipo de Dieta atribuida com sucesso a " + usuarioLogado.getNome());

                return tp;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                escolha = Integer.parseInt(scanner.nextLine());
                break;
        }
        return null;
    }

    public void regRefeicoes() {
        try {
            int opc;
            long id;
            do {
                opc = gui.menuRegRefeicoes();

                switch (opc) {
                    case 1:
                        StringBuilder builder = new StringBuilder("");
                        builder.append("Digite a refeicao que deseja cadastrar: ");
                        builder.append("\n1-Cafe da manha;");
                        builder.append("\n2-Almoco;");
                        builder.append("\n3-Cafe da tarde;");
                        builder.append("\n4-Jantar;");
                        builder.append("\n5-Ceia;");
                        builder.append("\nQual sua opcao ? R: ");
                        System.out.println(builder.toString());
                        int opcRef = Integer.parseInt(scanner.nextLine());
                        String aux = "";

                        switch (opcRef) {
                            case 1:
                                aux = "Cafe da manha";
                                break;
                            case 2:
                                aux = "Almoco";
                                break;
                            case 3:
                                aux = "Cafe da tarde";
                                break;
                            case 4:
                                aux = "Jantar";
                                break;
                            case 5:
                                aux = "Ceia";
                                break;
                        }

                        System.out.println("Digite os carboidratos: ");
                        double carbo = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite as proteinas: ");
                        double prot = Double.parseDouble(scanner.nextLine());
                        System.out.println("Digite as gorduras: ");
                        double gord = Double.parseDouble(scanner.nextLine());

                        System.out.println("Digite o id da dieta que está fazendo: ");
                        id = scanner.nextLong();
                        scanner.nextLine();
                        RegistroDieta dieta = rgDietaDAO.buscarRegistroDietaPorId(id);

                        double calorias = 4 * carbo + 4 * prot + 9 * gord;
                        double caloriasTot = refeicoesDAO.caloriasTotais();

                        if (calorias + caloriasTot > dieta.getCalorias()) {
                            System.out.println("Você excedeu o limite de calorias por dia de acordo com sua dieta");

                        } else {
                            double diferencaCalorias = dieta.getCalorias() - caloriasTot;
                            System.out.printf("Você ainda pode consumir %.2f calorias no dia %n", diferencaCalorias);
                        }
                        Refeicoes rf = new Refeicoes(0, dieta, aux, caloriasTot, prot, gord, LocalDate.now(), LocalDate.now());
                        refeicoesDAO.adiciona(rf);
                        System.out.println("Refeição adicionada!");

                        break;
                    case 2:
                        refeicoesDAO.exibirRefeicoes();
                        break;
                    case 3:
                        System.out.println("Digite o id da refeicao que deseja excluir: ");
                        id = scanner.nextLong();
                        scanner.nextLine();
                        refeicoesDAO.exclui(id);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.print("Opção inválida! Digite novamente: ");
                }

            } while (opc != 0);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro ao executar a operação de registro de refeições: " + e.getMessage());
            e.printStackTrace(); // Apenas para depuração, considere remover em um ambiente de produção.
        }
    }

    public void alimentoRefeicoes() {
        try {
            long id1;
            long id2;
            long id3;
            int opc;
            do {
                opc = gui.menuAlimentoRefeicoes();
                switch (opc) {
                    case 1:
                        System.out.print("Essas são as refeicoes disponiveis: ");
                        refeicoesDAO.exibirRefeicoes();
                        System.out.print("Digite o ID da refeicao que ira escolher:");
                        id1 = Long.parseLong(scanner.nextLine());
                        System.out.print("Esses são os alimentos Disponiveis: ");
                        alimentoRcDAO.exibirMeusAlimentos(usuarioLogado);
                        System.out.print("Digite o ID da refeicao que ira escolher:");
                        id2 = Long.parseLong(scanner.nextLine());
                        Refeicoes rf = refeicoesDAO.buscaRefeicaoPorID(id1);
                        AlimentoReceita ar = alimentoRcDAO.buscarAlimentoPorId(id2);
                        System.out.print("Agora digite as quantidade: ");
                        double porc1 = Double.parseDouble(scanner.nextLine());

                        AlimentoRefeicoes arf0 = new AlimentoRefeicoes(0, ar, rf, porc1, ar.getProteinas() + rf.getProteina(), ar.getGorduras() + rf.getGordura(), ar.getCarboidratos() + rf.getCarboidrato(), LocalDate.now(), LocalDate.now());
                        alimentoRfDAO.adiciona(arf0);

                        break;
                    case 2:
                        System.out.print("Essas são as suas refeicoes:");
                        alimentoRfDAO.exibirAlimentosRefeicoes();
                        break;
                    case 3:
                        alimentoRfDAO.exibirAlimentosRefeicoes();
                        System.out.print("Digite o id do Alimento Refeicoes que queira alterar, caso não queira alterar a refeicao pode digitar o id da mesma");
                        id1 = Long.parseLong(scanner.nextLine());

                        alimentoRcDAO.exibirMeusAlimentos(usuarioLogado);
                        System.out.print("Digite o id do alimento que queira colocar,caso não queira alterar o alimento pode digitar o id do mesmo ");
                        id2 = Long.parseLong(scanner.nextLine());

                        refeicoesDAO.exibirRefeicoes();
                        System.out.print("Agora digite o id da Refeicoes que queira colocar");
                        id3 = Long.parseLong(scanner.nextLine());

                        Refeicoes rfc = refeicoesDAO.buscaRefeicaoPorID(id3);
                        AlimentoReceita alr = alimentoRcDAO.buscarAlimentoPorId(id2);
                        AlimentoRefeicoes alrf = alimentoRfDAO.buscarAlimentoRefeicoesPorId(id1);
                        alimentoRfDAO.altera(id1, id3, id2, alrf.getPorcao(), alr.getProteinas() + rfc.getProteina(), alr.getGorduras() + rfc.getGordura(), alr.getCarboidratos() + rfc.getCarboidrato(), alr.getCalorias() + rfc.getCalorias());
                        break;
                    case 4:
                        alimentoRfDAO.exibirAlimentosRefeicoes();
                        System.out.print("Digite o id do item do cardapio que você quer excluir:");
                        id3 = Long.parseLong(scanner.nextLine());
                        alimentoRfDAO.exclui(id3);
                        break;
                    case 0:
                        break;
                }
            } while (opc != 0);
        } catch (RuntimeException e) {

        }
    }

}

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Controlador_Menu_Vintage controlador_Menu_Vintage;
    private static Scanner input = new Scanner(System.in);

    // Contrutores
    public Menu(Controlador_Menu_Vintage controlador_Menu_Vintage) {
        this.controlador_Menu_Vintage = controlador_Menu_Vintage; // Agregação
    }

    //fecha scanner
    private void closeScanner(Scanner scanner){
        scanner.close();
    } 

    public static Vintage carregaDefault_Changed() throws FileNotFoundException, ClassNotFoundException, IOException, 
    DateTimeException, AddException, RemoveException, VintageException{

        System.out.println("\n------Carregar Sistema-----");
        System.out.println("Estado do Sistema (1-> Por Defeito)/(2-> Alterado): ");

        int default_changed=0;
        try{
            default_changed=input.nextInt();
        }catch (InputMismatchException e){
            input.nextLine();
        }

        if (default_changed==1) return Controlador_Menu_Vintage.carregaEstado("EstadoVintage_default.dat");
        else if (default_changed==2) return Controlador_Menu_Vintage.carregaEstado("EstadoVintage_changed.dat");
        else throw new VintageException("Selecione 1 ou 2!!!");
    }

    public void run_AdminOrUser(){
        System.out.println("\n------Vintage-----");
        System.out.println("Modo de navegação (1-> Admin)/(2->User): ");

        int admin_user=0;
        try{
            admin_user=input.nextInt();
            input.nextLine(); 
        }catch (InputMismatchException e){
            System.out.println("Escolha 1 ou 2!!!");
        }

        if(admin_user==1) this.runAdmin();
        else if (admin_user==2) this.runUtilizador();
        else {
            input.nextLine();
            this.run_AdminOrUser();
        }
    }

    private void runAdmin(){
        int opcao;

        do{
            System.out.println("\n------ MENU ------");
            System.out.println("1. Verificar Estado do sistema");
            System.out.println("2. Avançar no Tempo");
            System.out.println("3. Adicionar Utilizador");
            System.out.println("4. Adicionar Transportadora");
            System.out.println("5. Remover Utilizador");
            System.out.println("6. Remover Transportadora");
            System.out.println("7. Perfil Utilizador");
            System.out.println("8. Perfil Transportadora");
            System.out.println("9. Ganhos do Sistema");
            System.out.println("10. Vendedor que mais fatorou");
            System.out.println("11. Transportadora com mais volume de encomendas");
            System.out.println("12. Melhores compradores e vendedores num determinado periodo de tempo");
            System.out.println("0. Sair");

            opcao=-1;

            try {
                opcao = input.nextInt();
            } catch (InputMismatchException e) {
                input.nextLine();
            }

            switch(opcao){
                case 1:
                    System.out.println(controlador_Menu_Vintage.toString_Vintage());
                    break;
                case 2:
                    input.nextLine();
                    System.out.println("Data (dd-MM-yyyy) para onde pertende avancar: ");
                    String data = input.nextLine();
                    try {
                        System.out.println(controlador_Menu_Vintage.avancarTempo(data));
                    } catch (DateTimeException e) {
                        System.out.println("A data é no formato (dd-MM-yyyy)!");
                        break;
                    }
                    break;
                case 3:
                    input.nextLine();
                    System.out.println("Nome: ");
                    String nome = input.nextLine();
                    System.out.println("Morada: ");
                    String morada = input.nextLine();
                    System.out.println("NumFiscal: ");
                    int numFiscal =0;
                    try {
                        numFiscal = input.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println("O numFiscal é um numero!!");
                        input.nextLine();
                        break;
                    }
                    try {
                        System.out.println("\nEscolha uma das transportadoras (nome): \n"+"Nº de Transportadoras: "
                        +controlador_Menu_Vintage.toString_Transportadoras());
                        System.out.println();
                        input.nextLine();
                        System.out.println("Nome Transportadora:");
                        String nomeTransportadora = input.nextLine();
                        Transportadora transportadora = controlador_Menu_Vintage.getTransportadora(nomeTransportadora);

                        Utilizador utilizador = new Utilizador(nome, morada,numFiscal, new GestorArtigos(), 
                        new GestorArtigos(), new GestorArtigos(), transportadora);

                        controlador_Menu_Vintage.addUtilizador(utilizador);
                        break;
                    }catch(GetException e){
                        System.out.println(e.getMessage());
                        break;
                    }catch(AddException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                case 4:
                    input.nextLine();
                    System.out.println("\nNome da Transportadora: ");
                    nome = input.nextLine();
                    System.out.println("Preço Encomenda pequena: ");
                    try{
                        int precoExpPequena = input.nextInt();
                        System.out.println("Preço Encomenda media: ");
                        int precoExpMedia = input.nextInt();
                        System.out.println("Preço Encomenda Grande: ");
                        int precoExpGrande = input.nextInt();
                        Transportadora transportadora = new Transportadora(nome, precoExpPequena,
                        precoExpMedia,precoExpGrande);
                        controlador_Menu_Vintage.addTransportadora(transportadora);
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("\nOs Preços são números!!");
                        input.nextLine();
                        break;
                    }catch(AddException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                case 5:
                    input.nextLine();
                    System.out.println("\nCódigo do utilizador a remover: ");
                    String codUtilizador = input.nextLine();
                    try {
                        controlador_Menu_Vintage.removeUtilizador(codUtilizador);
                    }catch (RemoveException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    input.nextLine();
                    System.out.println("\nNome da Transportadora a remover: ");
                    String nomeTransportadora = input.nextLine();
                    try {
                        controlador_Menu_Vintage.removeTransportadora(nomeTransportadora);
                    }catch (RemoveException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7: 
                    input.nextLine();
                    System.out.println("\nCodigo de Utilizador: ");
                    codUtilizador = input.nextLine();
                    try{
                        System.out.println(controlador_Menu_Vintage.getUtilizador(codUtilizador).toString());
                    }catch (GetException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 8:
                    input.nextLine();
                    System.out.println("\nNome da Transportadora: ");
                    nomeTransportadora = input.nextLine();
                    try{
                        System.out.println(controlador_Menu_Vintage.getTransportadora(nomeTransportadora).toString());
                    }catch (GetException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 9:
                    System.out.println("\nGanhos do sistema: " + controlador_Menu_Vintage.ganhosVintage());
                    break;
                case 10:
                    System.out.println("\nVendedor que mais fatorou: " + controlador_Menu_Vintage.vendedorMaisFatorou());
                    break;
                case 11:
                    System.out.println("\nTransportadora mais usada: "+ controlador_Menu_Vintage.TransportadoraMaiorVolumeFatoracao());
                    break;
                case 12:
                    System.out.println("\nData Inicial do Periodo (dd-MM-yyyy):  ");
                    input.nextLine();
                    String datai = input.nextLine();
                    System.out.println("\nData Final do Periodo (dd-MM-yyyy):  ");
                    String dataf = input.nextLine();
                    System.out.println("\nQuantos vendedores/compradores? ");
                    int top=0;
                    try{
                        top = input.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println("\nA quantidade de utilizadores é um numero!!");
                        input.nextLine();
                        break;
                    }
                    try{
                        controlador_Menu_Vintage.topVendedoresCompradores(datai, dataf, top);
                    }catch(DateTimeException e){
                        System.out.println("\nDatas sao no formato (dd-MM-yyyy)!!");
                        break;
                    }
                    break;
                case 0:
                    System.out.println("\nSaindo do programa...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
                    break;
            }

        }while(opcao!=0);

        closeScanner(input);
    }

    private void runUtilizador() throws DateTimeException {
        int opcao;

        System.out.println("\n------------LOGIN-----------");
        System.out.println("Código de utilizador:");
        String codUtilizadorLogin = input.nextLine();
        if(!controlador_Menu_Vintage.exists_Utilizador(codUtilizadorLogin)){
            System.out.println("Utilizador não existe!!");
            this.runUtilizador();
        }

        do {
            System.out.println("\n------ MENU ------");
            System.out.println("1. Perfil Utilizador");
            System.out.println("2. Meu histórico de encomendas");
            System.out.println("3. Realizar Encomendas");
            System.out.println("4. Colocar artigos à Venda");
            System.out.println("0. Sair");
            System.out.println("Escolha uma opção: ");

            opcao = -1;

            try {
                opcao = input.nextInt();
            } catch (InputMismatchException e) {
                input.nextLine();
            }

            SwitchFalg: switch (opcao) { 
                case 1: 
                    try{
                        System.out.println(controlador_Menu_Vintage.getUtilizador(codUtilizadorLogin).toString());
                    }catch (GetException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 2:
                    System.out.println(controlador_Menu_Vintage.encomendasComprador(codUtilizadorLogin));
                    break;
                case 3:
                    input.nextLine();
                    System.out.println("\nArtigos à Venda: ");
                    System.out.println(controlador_Menu_Vintage.artigos_AVenda_Vintage());

                    double taxaGarantia = 10.5; //preco padrao do sistema
                    LocalDate dataCriacao_Americano= controlador_Menu_Vintage.getVintage().get_DataAtual();
                    LocalDate dataEntrega_Americano =dataCriacao_Americano.plusMonths(2);

                    String dataCriacao = Data.DatetoString(dataCriacao_Americano);
                    String dataEntrega = Data.DatetoString(dataEntrega_Americano);

                    Encomenda.Estado estadoEncomenda= Encomenda.Estado.pendente;
                    try {
                        Utilizador comprador = controlador_Menu_Vintage.getUtilizador(codUtilizadorLogin);
                        Encomenda encomenda = new Encomenda(new GestorArtigos(), taxaGarantia, dataCriacao, 
                        dataEntrega, estadoEncomenda, new GestorUtilizadores(),comprador);
                        System.out.println("Número de artigos na Encomenda: ");
                        int nArtigos=0;
                        try{
                            nArtigos = input.nextInt();
                        }catch(InputMismatchException e){
                            System.out.println("Escolha um numero!!!");
                            input.nextLine();
                            break;
                        }
                        input.nextLine();
                        for (int i=0;i<nArtigos;i++){
                            System.out.println("Código de barras do Artigo: ");
                            String codBarras = input.nextLine();
                            try {
                                Artigo artigo = controlador_Menu_Vintage.getArtigo(codBarras);
                                controlador_Menu_Vintage.addEncomenda_withArtigo_Vintage(encomenda, artigo, comprador);
                            }catch(RemoveException e){
                                System.out.println(e.getMessage());
                                break SwitchFalg;
                            }
                        }
                    }catch(GetException e){
                        System.out.println(e.getMessage());
                        break;
                    }catch(DateTimeException e){
                        System.out.println("Data tem que estar no formato (dd-MM-yyyy)!!!");
                        break;
                    } catch (AddException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 4:
                    Utilizador dono=null;
                    try {
                        dono = controlador_Menu_Vintage.getUtilizador(codUtilizadorLogin);
                    } catch (GetException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    input.nextLine();
                    System.out.println("\nO artigo é novo (true/false)? ");
                    boolean artigo_novo;
                    try{
                        artigo_novo = input.nextBoolean();
                    }catch(InputMismatchException e){
                        System.out.println("Deve inserir true/false!!");
                        input.nextLine();
                        break;
                    }
                    String estado;
                    if (!artigo_novo){
                        System.out.println("Quanto usado esta?");
                        estado=input.nextLine();
                    }else estado = "novo";

                    System.out.println("\nNúmero de donos do artigo: ");
                    int num_donos = 0;
                    try{
                        num_donos = input.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println("Numero de donos é um numero");
                        input.nextLine();
                        break;
                    }
                    System.out.println("\nDescreva o produto: ");
                    input.nextLine();
                    String descricao = input.nextLine();
                    System.out.println("\nMarca: ");
                    String marca = input.nextLine();
                    System.out.println("\nPreco Base: ");
                    double preco_base;
                    try {
                        preco_base = input.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Precos sao numeros!!");
                        input.nextLine();
                        break;
                    }
                    double estado_utilizacao;
                    if (artigo_novo) estado_utilizacao =1;
                    else if (num_donos<3) estado_utilizacao =0.7; //valores default do sistema
                    else estado_utilizacao=0.4; //valores default do sistema
                    System.out.println("\nEscolha o tipo de artigo (1->mala/2->sapatilha/3->t-shirt): ");
                    try{
                        int opcaoArtigo = input.nextInt();
                        if (opcaoArtigo==1){
                            System.out.println("\nDimensao (pequeno/medio/grande): ");
                            input.nextLine();
                            Mala.Dimensao dimensao;
                            try {
                                dimensao = Mala.Dimensao.valueOf(input.next().toUpperCase());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Dimensao tem que ser pequeno/medio/grande!!");
                                break;
                            }
                            System.out.println("\nMaterial: ");
                            String material = input.nextLine();
                            System.out.println("\nData colecao (dd-MM-yyyy): ");
                            String dataColecao = input.nextLine();
                            System.out.println("\nPremium? (true/false): ");
                            boolean premium;
                            try {
                                premium = input.nextBoolean();
                            } catch (InputMismatchException e) {
                                System.out.println("Premium é (true/false)!!");
                                input.nextLine();
                                break;
                            }
                            Mala mala = null;
                            try {
                                mala = new Mala(artigo_novo, estado, num_donos, descricao, marca,
                                preco_base, estado_utilizacao, dono, dimensao, material, dataColecao, premium);
                                controlador_Menu_Vintage.addArtigo_VendedorVintage(mala, dono);
                                break;
                            }catch(DateTimeException e){ // datacolecao pode ter erro de parse
                                System.out.println("Data no fomato errado (dd-MM-yyyy)!!");
                                break;
                            }catch(AddException e){
                                System.out.println(e.getMessage());
                                break;
                            } catch (GetException e) {
                                System.out.println(e.getMessage());
                            } catch (RemoveException e) {
                                System.out.println(e.getMessage());
                            }
                        }else if (opcaoArtigo ==2){
                            System.out.println("\nTamanho: ");
                            int tamanho=0;
                            try{
                                tamanho=input.nextInt();
                            }catch(InputMismatchException e){
                                System.out.println("Tamanho é um número!!");
                                input.nextLine();
                                break;
                            }
                            System.out.println("\nTem atacadores? (true/false) ");
                            boolean atacadores=false;
                            try{
                                atacadores=input.nextBoolean();
                            }catch(InputMismatchException e){
                                System.out.println("Tem que escolher true/false!!");
                                input.nextLine();
                                break;
                            }
                            System.out.println("\nEscolha uma cor (valor RGB): ");
                            Color cor=Color.BLACK;
                            try{
                                cor = new Color(input.nextInt());
                                input.nextLine();
                            }catch(InputMismatchException e){
                                System.out.println("A cor é o seu valor em RGB!!");
                                input.nextLine();
                                break;
                            }
                            System.out.println("\nData de lançamento (dd-MM-yyyy): ");
                            String data_lancamento = input.nextLine();
                            System.out.println("\nPremium? (true/false): ");
                            boolean premium=false;
                            try{
                                premium = input.nextBoolean();
                            }catch(InputMismatchException e){
                                System.out.println("Premium é (true/false)!!");
                                input.nextLine();
                                break;
                            }
                            try{
                                Sapatilha sapatilha = new Sapatilha(artigo_novo, estado, num_donos, descricao, 
                                marca, preco_base, estado_utilizacao, dono, tamanho, atacadores, cor, premium, data_lancamento);
                                controlador_Menu_Vintage.addArtigo_VendedorVintage(sapatilha, dono);
                                break;
                            }catch (DateTimeException e){
                                System.out.println("Data de lancamento tem que estar no formato (dd-MM-yyyy)!!");
                                break;
                            } catch (AddException e) {
                                System.out.println(e.getMessage());
                                break;
                            } catch (GetException e) {
                               System.out.println(e.getMessage());
                            } catch (RemoveException e) {
                               System.out.println(e.getMessage());
                            }
                        }else if (opcaoArtigo ==3){
                            System.out.println("\nTamanho: (s/m/l/xl)");
                            input.nextLine();
                            Tshirt.Tamanho tamanho;
                            try{
                                tamanho = Tshirt.Tamanho.valueOf(input.next().toUpperCase());
                            }catch(IllegalArgumentException e){
                                System.out.println("Tamanho tem que ser (s/m/l/xl)!!");
                                break;
                            }
                            System.out.println("\nPadrao (liso/riscas/palmeiras): ");
                            Tshirt.Padrao padrao;
                            try{
                                padrao = Tshirt.Padrao.valueOf(input.next().toUpperCase());
                            }catch(IllegalArgumentException e){
                                System.out.println("Padrao tem que ser (liso,riscas,palmeiras)!!");
                                break;
                            }
                            Tshirt tshirt = new Tshirt(artigo_novo, estado, num_donos, descricao,
                            marca, preco_base, estado_utilizacao, dono, tamanho, padrao);
                            try{
                                controlador_Menu_Vintage.addArtigo_VendedorVintage(tshirt, dono);
                                break;
                            }catch (AddException e){
                                System.out.println(e.getMessage());
                                break;
                            } catch (GetException e) {
                                System.out.println(e.getMessage());
                            } catch (RemoveException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }catch(InputMismatchException e){
                        System.out.println("Tem que escolher entre 1,2 ou 3!!!");
                        input.nextLine();
                    }
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

        } while (opcao != 0);

        closeScanner(input);
    }
}

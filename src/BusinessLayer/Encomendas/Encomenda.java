import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Encomenda implements Serializable{
    public enum DimensaoEmbalagem{
        pequeno,medio,grande
    }
    
    public enum Estado{
        pendente,entregue
    }

    private int numeroEncomenda;
    private GestorArtigos gestorArtigos;
    private DimensaoEmbalagem dimensaoEmbalagem; 
    private double taxaGarantia; //preco pago no inicio, como uma garantia ao vendedor
    private double custoExpedicao;
    private double precoFinal;
    private LocalDate dataCriacao;
    private LocalDate dataEntrega;
    private Estado estado;
    private GestorUtilizadores vendedores; //uma encomenda pode ter diversos vendedores
    private Utilizador comprador;
    private Transportadora transportadora;

    private static int contador = 1;

    //Construtores
    public Encomenda() {
        this.numeroEncomenda=contador++;
        this.gestorArtigos = new GestorArtigos();
        this.dimensaoEmbalagem = DimensaoEmbalagem.pequeno;
        this.taxaGarantia = 0;
        this.custoExpedicao = 0;
        this.precoFinal = 0;
        this.dataCriacao = LocalDate.now();
        this.dataEntrega = LocalDate.MAX;
        this.estado=Estado.pendente;
        this.vendedores=new GestorUtilizadores();
        this.comprador=new Utilizador();
        this.transportadora=new Transportadora();
    }

    private double calcularPrecoFinal() {
        return gestorArtigos.calcularPrecoFinalEncomenda(this);
    }


    public Encomenda(GestorArtigos gestorArtigos,double taxaGarantia,String dataCriacao,
    String dataEntrega,Estado estado,GestorUtilizadores vendedores,Utilizador comprador) throws DateTimeException{
        this.numeroEncomenda=contador++;
        this.gestorArtigos=gestorArtigos.clone();

        int numArtigos = gestorArtigos.getArtigos().size(); 

        if (numArtigos<3) this.dimensaoEmbalagem=DimensaoEmbalagem.pequeno;
        else if (numArtigos<5) this.dimensaoEmbalagem=DimensaoEmbalagem.medio;
        else this.dimensaoEmbalagem=DimensaoEmbalagem.grande;

        this.taxaGarantia=taxaGarantia;

        if(dimensaoEmbalagem==DimensaoEmbalagem.pequeno){
            this.custoExpedicao=comprador.getTransportadora().getPrecoExpPequena();
        }else if (dimensaoEmbalagem==DimensaoEmbalagem.medio){
            this.custoExpedicao=comprador.getTransportadora().getPrecoExpMedia();
        }else{
            this.custoExpedicao=comprador.getTransportadora().getPrecoExpGrande();
        }

        this.precoFinal=this.calcularPrecoFinal();
        this.dataCriacao=Data.StringEuropeia_toDate(dataCriacao);
        this.dataEntrega=Data.StringEuropeia_toDate(dataEntrega);     
        this.estado=estado;
        this.vendedores=vendedores.clone(); 
        this.comprador=comprador.clone();
        this.transportadora=comprador.getTransportadora(); //transportadora é definida por defeito pelo vendedor
    }

    public Encomenda(Encomenda umEncomenda){
        this.numeroEncomenda=umEncomenda.getNumeroEncomenda();
        this.gestorArtigos=umEncomenda.getGestorArtigos();
        this.dimensaoEmbalagem=get_DimensaoEmbalagem();
        this.taxaGarantia=umEncomenda.getTaxaGarantia();
        this.custoExpedicao=umEncomenda.getCustoExpedicao();
        this.precoFinal=umEncomenda.get_PrecoFinal();
        this.dataCriacao=umEncomenda.get_DataCriacao();
        this.dataEntrega=umEncomenda.get_DataEntrega();
        this.estado=umEncomenda.getEstado();
        this.vendedores=umEncomenda.getVendedores();
        this.comprador=umEncomenda.getComprador();
        this.transportadora=umEncomenda.getTransportadora();
    }
    
    //gets
    public int getNumeroEncomenda() {
        return this.numeroEncomenda;
    }

    public GestorArtigos getGestorArtigos() {
        return gestorArtigos; //Agregação
    }

    public DimensaoEmbalagem get_DimensaoEmbalagem() {
        return this.dimensaoEmbalagem;
    }

    public double getTaxaGarantia() {
        return this.taxaGarantia;
    }

    public double getCustoExpedicao() {
        return this.custoExpedicao;
    }

    public double get_PrecoFinal() {
        return this.precoFinal;
    }

    public LocalDate get_DataCriacao() { 
        return LocalDate.of(dataCriacao.getYear(),
        dataCriacao.getMonth(),dataCriacao.getDayOfMonth());
    }

    public LocalDate get_DataEntrega() {
        return LocalDate.of(dataEntrega.getYear(),
        dataEntrega.getMonth(),dataEntrega.getDayOfMonth());
    }

    public Estado getEstado() {
        return this.estado;
    }

    public GestorUtilizadores getVendedores() {
        return this.vendedores.clone();
    }

    public Utilizador getComprador() {
        return this.comprador.clone();
    }

    public Transportadora getTransportadora() {
        return this.transportadora.clone();
    }

    public static int getContador() {
        return Encomenda.contador;
    }

    //sets
    public void setNumeroEncomenda(int numeroEncomenda) {
        this.numeroEncomenda = numeroEncomenda;
    }

    public void setGestorArtigos(GestorArtigos gestorArtigos) {
        this.gestorArtigos = gestorArtigos.clone();
    }

    public void set_DimensaoEmbalagem(DimensaoEmbalagem dimensaoEmbalagem) {
        this.dimensaoEmbalagem = dimensaoEmbalagem;
    }

    public void set_TaxaGarantia(double taxaGarantia) {
        this.taxaGarantia = taxaGarantia;
    }

    public void set_CustoExpedicao(double custoExpedicao) {
        this.custoExpedicao = custoExpedicao;
    }

    public void set_PrecoFinal(int precoFinal) {
        this.precoFinal = precoFinal;
    }

    public void set_DataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = LocalDate.of(dataCriacao.getYear(),
        dataCriacao.getMonth(),dataCriacao.getDayOfMonth());
    }

    public void set_DataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = LocalDate.of(dataEntrega.getYear(),
        dataEntrega.getMonth(),dataEntrega.getDayOfMonth());
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setVendedores(GestorUtilizadores vendedores) {
        this.vendedores = vendedores.clone();
    }

    public void setComprador(Utilizador comprador) {
        this.comprador = comprador.clone();
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora.clone();
    }

    public static void setContador(int contador) {
        Encomenda.contador = contador;
    }

    //clone
    public Encomenda clone(){
        return new Encomenda(this);
    }

    //equals
    public boolean equals(Object o){
        if (this==o) 
            return true;
        if ((o==null) || (this.getClass() != o.getClass())) 
            return false;
        Encomenda encomenda = (Encomenda) o;
        return (encomenda.getNumeroEncomenda()==this.numeroEncomenda &&
        encomenda.getGestorArtigos().equals(this.gestorArtigos) &&
        encomenda.get_DimensaoEmbalagem()==this.dimensaoEmbalagem &&
        encomenda.getTaxaGarantia()==this.taxaGarantia &&
        encomenda.getCustoExpedicao()==this.custoExpedicao &&
        encomenda.get_PrecoFinal()==this.precoFinal &&
        encomenda.get_DataCriacao().equals(this.dataCriacao) &&
        encomenda.get_DataEntrega().equals(this.dataEntrega) &&
        encomenda.getEstado()==this.estado &&
        encomenda.getVendedores().equals(this.vendedores) &&
        encomenda.getComprador().equals(this.comprador) &&
        encomenda.getTransportadora().equals(this.transportadora));
    }

    //toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n........Encomenda.........\n");
        sb.append("Numero de Encomenda: "+this.getNumeroEncomenda()+'\n');
        sb.append("\nArtigos na Encomenda: ").append(gestorArtigos.toString());
        if(this.get_DimensaoEmbalagem() == DimensaoEmbalagem.pequeno){
            sb.append("\nDimensãoEmbalagem: Pequena\n");
        }else if(this.get_DimensaoEmbalagem()==DimensaoEmbalagem.medio){
            sb.append("\nDimensãoEmbalagem: Média\n");
        }else{
            sb.append("\nDimensãoEmbalagem: Grande\n");
        }
        sb.append("\nVendedores: "+this.getVendedores().toString());
        sb.append("\nComprador: "+this.getComprador().getCodUtilizador()+"\n");
        sb.append("Transportadora utilizada: "+this.getTransportadora().getNome()+"\n");
        sb.append("DataCriação: "+this.get_DataCriacao()+"\n");
        sb.append("DataEntrega: "+this.get_DataEntrega()+"\n");
        sb.append("Estado: "+this.getEstado()+'\n');
        if(this.getEstado() == Estado.entregue) {
            if(isDentroDoPrazoDevolucao()){
                sb.append("Prazo de Devolução: No prazo\n");
            }else{
                sb.append("Prazo de Devolução: Prazo de devolução já expirou\n");
            }
        }
        sb.append("PrecoTotal: "+this.get_PrecoFinal()+"\n");
        return sb.toString();
    }

    //métodos adicionais
    public void addArtigo_withUtilizadores(Artigo artigo,Utilizador vendedor,Utilizador comprador) throws AddException, RemoveException{
        this.gestorArtigos.addArtigo(artigo);
        this.vendedores.addUtilizador(vendedor);
        comprador.artigo_Comprado(artigo);
        vendedor.artigo_Vendido(artigo);
        artigo.setFoi_comprado(true);
    }

    public void addArtigo(Artigo artigo) throws AddException, RemoveException{
        this.gestorArtigos.addArtigo(artigo);
        artigo.setFoi_comprado(true);
    }

    public void addVendedor(Utilizador vendedor) throws AddException{
        this.vendedores.addUtilizador(vendedor);
    }

    public String fatura(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n--FATURA--\n");
        sb.append(this.toString());
        return sb.toString();
    }
    
    public void entregar() {
        this.dataEntrega = LocalDate.now();
    }

    public Estado atualizarEstado(LocalDate data) { //recebe a data atual do sistema e atualiza se
        if(data.compareTo(this.get_DataEntrega()) > 0) {
            return(this.estado = Estado.entregue);
        }else{
            return(this.estado);
        }              
    }
    
    public boolean isDentroDoPrazoDevolucao() {
        if (this.getEstado()==Estado.entregue) {
            long diferencaDias = ChronoUnit.DAYS.between(this.dataCriacao,this.dataEntrega);
            return diferencaDias <= 2;
        }else {
            return false;
        }
    }
}

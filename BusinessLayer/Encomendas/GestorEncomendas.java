import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;

public class GestorEncomendas implements Serializable{
    private Map<Integer,Encomenda> vendas; //map das diversas encomendas vendidas (chave é numeroEncomenda).

    public GestorEncomendas(){
        this.vendas = new HashMap<>();
    }

    public GestorEncomendas(Map<Integer,Encomenda> vendas) {
        this.vendas = new HashMap<>();
        for (Map.Entry<Integer,Encomenda> entry: vendas.entrySet()){
            this.vendas.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public GestorEncomendas(GestorEncomendas gereEncomendas) {
        this.vendas = gereEncomendas.getVendas();
    }

    //get
    public Map<Integer, Encomenda> getVendas() {
        Map<Integer,Encomenda> novo = new HashMap<>();
        for(Map.Entry<Integer,Encomenda> entry: this.vendas.entrySet()){
            novo.put(entry.getKey(),entry.getValue().clone());
        }
        return novo;
    }

    //set
    public void setVendas(Map<Integer,Encomenda> vendas) {
        this.vendas=new HashMap<>();
        for(Map.Entry<Integer,Encomenda> entry: vendas.entrySet()){
            this.vendas.put(entry.getKey(),entry.getValue().clone());
        }
    }

    //clone
    public GestorEncomendas clone(){
        return new GestorEncomendas(this);
    }
    
    //equals
    public boolean equals(Object o){
        if (this==o) 
            return true;
        if ((o==null) || (this.getClass() != o.getClass())) 
            return false;
        GestorEncomendas vendas = (GestorEncomendas) o;
        return (vendas.getVendas().equals(this.vendas));
    }

    //toString
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(vendas.size()).append('\n');

        for(Map.Entry<Integer,Encomenda> entry: this.vendas.entrySet()){
            sb.append("Numero de encomenda: ").append(entry.getKey()).append('\n');
        }

        return sb.toString();
    }

    //Outros métodos
    public void addEncomenda(Encomenda encomenda) throws AddException{
        Encomenda previousValue = this.vendas.putIfAbsent(encomenda.getNumeroEncomenda(),encomenda.clone());
        if (previousValue != null) { //se a chave já existir no map
            throw new AddException("Já existe no sistema uma Encomenda com esse Nº Encomenda!");
        }
    }

    public void removeEncomenda(Integer numEncomenda) throws RemoveException{
        Encomenda encomandaRemovida = this.vendas.remove(numEncomenda);
        if (encomandaRemovida == null){
            throw new RemoveException("Não existe uma Encomenda com esse Numero de Encomenda!");
        }
    }

    public Encomenda getEncomenda(Integer numEncomenda) throws GetException{
        Encomenda encomenda = this.vendas.get(numEncomenda);
        if (encomenda == null){
            throw new GetException("Não existe uma Encomenda com esse Numero de Encomenda!");
        }
        return encomenda.clone();
    }

    public String entregaEncomenda(Vintage vintage){
        StringBuilder encomendasEntregues = new StringBuilder();
        for (Map.Entry<Integer,Encomenda> entry: vendas.entrySet()){
            if (vintage.get_DataAtual().compareTo(entry.getValue().get_DataEntrega())>0
            && Encomenda.Estado.pendente.equals(entry.getValue().getEstado())){
                entry.getValue().setEstado(Encomenda.Estado.entregue);
                encomendasEntregues.append(entry.getValue().fatura());
            }
        }
        return encomendasEntregues.toString();
    }

    //Q1
    public String vendedorMaisFatorou(){
        Map<String, Double> vendedores = new HashMap<>(); //associa a cada utilizador o seu lucro
        for(Map.Entry<Integer,Encomenda> entry: vendas.entrySet()){
            if(!(entry.getValue().getEstado().equals(Encomenda.Estado.pendente))){
                GestorArtigos gestorArtigos = entry.getValue().getGestorArtigos();
                GestorUtilizadores gestor_vendedores = entry.getValue().getVendedores();
                for (Map.Entry<String,Utilizador> Entry : gestor_vendedores.getUtilizadores().entrySet()){
                    String codUtilizadorVendedor= Entry.getValue().getCodUtilizador();
                    double valorEncomenda=gestorArtigos.ValorFaturado_Encomenda();
                    if(!(vendedores.containsKey(codUtilizadorVendedor))){
                        vendedores.put(codUtilizadorVendedor,valorEncomenda);
                    }else{
                        double valorExistente = vendedores.get(codUtilizadorVendedor);
                        vendedores.replace(codUtilizadorVendedor, valorEncomenda+valorExistente);
                    }
                }
            }
        }

        double maior=0;
        String maiorVendedor="Não se realizaram vendas";
        for(Map.Entry<String,Double> entry: vendedores.entrySet()){
            if(entry.getValue()>maior){
                maior=entry.getValue();
                maiorVendedor=entry.getKey();
            }
        }
        return maiorVendedor;   
    }

    //Q2
    public String TransportadoraMaiorVolumeFatoracao(){
        Map<String, Integer> transportadoras = new HashMap<>();
        String transportadoraNome="Não foram realizadas vendas";
        int volume=0;
        for(Map.Entry<Integer,Encomenda> entry: vendas.entrySet()){
            transportadoraNome=entry.getValue().getTransportadora().getNome();
            if(transportadoras.containsKey(transportadoraNome)){
                volume=transportadoras.get(transportadoraNome)+1;
                transportadoras.put(transportadoraNome,volume);
            }else{
                transportadoras.put(transportadoraNome,1);
            }
        }
        for(Map.Entry<String,Integer> entry: transportadoras.entrySet()){
            if(entry.getValue()>volume){
                volume=entry.getValue();
                transportadoraNome=entry.getKey();
            }
        }
        return transportadoraNome;
    }

    //Q3
    public String encomendasComprador(String codUtilizador){
        StringBuilder sb = new StringBuilder();
        sb.append("\nHistórico de encomendas: \n");
        for(Map.Entry<Integer,Encomenda> entry: vendas.entrySet()){
            if (entry.getValue().getComprador().getCodUtilizador().equals(codUtilizador)){
                sb.append("Nº de Encomenda: ").append(entry.getKey().toString()+'\n');
            }
        }
        return sb.toString();
    }

    //Q4
    public void topVendedoresCompradores(String dataInicial, String dataFinal, int top) throws DateTimeException {
        ArrayList<Encomenda> encomendas = getEncomendasForPeriod(dataInicial, dataFinal); //lista so com as encomendas dentro desse periodo de tempo
        if(encomendas.isEmpty()) {
            System.out.println("Não existem encomendas realizadas nesse periodo!!");
            return;
        }

        Map<String, Double> vendedoresTotais = new HashMap<>();
        Map<String, Double> compradoresTotais = new HashMap<>();
        for (Encomenda encomenda : encomendas) {
            for(Map.Entry<String,Utilizador> entry : encomenda.getVendedores().getUtilizadores().entrySet()){
                String codVendedor=entry.getValue().getCodUtilizador();
                String codComprador = encomenda.getComprador().getCodUtilizador();
                Double preco_final = encomenda.get_PrecoFinal();
    
                compradoresTotais.put(codComprador, preco_final);
                vendedoresTotais.put(codVendedor, preco_final);
    
                if (vendedoresTotais.containsKey(codVendedor)) {
                    Double preco_existente = vendedoresTotais.get(codVendedor);
                    vendedoresTotais.replace(codVendedor, preco_final+preco_existente);
                }
    
                if (compradoresTotais.containsKey(codComprador)) {
                    Double preco_existente = compradoresTotais.get(codComprador);
                    compradoresTotais.replace(codComprador, preco_final+preco_existente);
                }
            }
        }

        List<Map.Entry<String,Double>> organizarVendedores = new ArrayList<>(vendedoresTotais.entrySet());
        organizarVendedores.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("Maiores Vendedores:");
        int count = 1;
        for (Map.Entry<String, Double> entry : organizarVendedores) {
            String codVendedor =  entry.getKey();
            System.out.println(count + ". " + codVendedor);
            count++;
            if (count > top) {
                break;
            }
        }
        if (count<top) System.out.println("Não existem mais vendedores diferentes nesse periodo!!");

        List<Map.Entry<String, Double>> organizarCompradores = new ArrayList<>(compradoresTotais.entrySet());
        organizarCompradores.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("\nMaiores Compradores:");
        count = 1;
        for (Map.Entry<String, Double> entry : organizarCompradores) {
            String codComprador = entry.getKey();
            System.out.println(count + ". " + codComprador);
            count++;
            if (count > top) {
                break;
            }
        }
        if (count<top) System.out.println("Não existem mais compradores diferentes nesse periodo!!");
    }

    private ArrayList<Encomenda> getEncomendasForPeriod(String dataInicio, String dataFim) throws DateTimeException {
        ArrayList<Encomenda> encomendasPeriodo = new ArrayList<>();
        LocalDate dataInicioF = Data.StringEuropeia_toDate(dataInicio);
        LocalDate dataFimF = Data.StringEuropeia_toDate(dataFim);
        for (Map.Entry<Integer, Encomenda> entry: vendas.entrySet()) {
            LocalDate dataCriacao = entry.getValue().get_DataCriacao();
            if (dataCriacao.compareTo(dataInicioF) >= 0 && dataCriacao.compareTo(dataFimF) <= 0) {
                encomendasPeriodo.add(entry.getValue());
            }
        }
        return encomendasPeriodo;
    }

    //Q5
    public double ganhosVintage(){
        int ganhosTotais=0;
        for(Map.Entry<Integer,Encomenda> entry: vendas.entrySet()){
            ganhosTotais+=entry.getValue().get_PrecoFinal();
        }
        return ganhosTotais;
    }
}


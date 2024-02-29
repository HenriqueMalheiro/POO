import java.awt.Color;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Sapatilha extends Artigo{
    private int tamanho;
    private boolean atacadores;
    private Color cor;//Usar tuplo para RGB
    private LocalDate data_lancamento; //Passada como argumento em String "dd-MM-yyyy"
    private boolean premium;

    private static int contador = 1;

    public Sapatilha(){
        super();
        this.tamanho=0;
        this.atacadores=false;
        this.cor=Color.BLACK;
        this.data_lancamento= LocalDate.now();
        this.premium=false;
    }
    
    public Sapatilha(boolean artigo_novo, String estado, int num_donos,
    String descricao, String marca,double preco_base,double estado_utilizacao,Utilizador dono,
    int tamanho,boolean atacadores,Color cor,boolean premium,String data_colecao) throws DateTimeException{
        super(artigo_novo,estado,num_donos,descricao,marca,preco_base,estado_utilizacao,dono);
        super.setCod_barras("sap"+contador++);
        this.tamanho=tamanho;
        this.atacadores=atacadores;
        this.cor=cor;
        this.data_lancamento=Data.StringEuropeia_toDate(data_colecao);
        this.premium=premium;
    }

    public Sapatilha(Sapatilha novo){
        super(novo);
        this.tamanho=novo.getTamanho();
        this.atacadores=novo.getAtacadores();
        this.cor=novo.getCor();
        this.data_lancamento=novo.getData_lancamento();
        this.premium=novo.getPremium();
    }

    //get
    public int getTamanho(){
        return this.tamanho;
    }

    public boolean getAtacadores(){
        return this.atacadores;
    }

    public Color getCor(){
        return this.cor;
    }

    public LocalDate getData_lancamento(){
        return LocalDate.of(data_lancamento.getYear(),
        data_lancamento.getMonth(),data_lancamento.getDayOfMonth());
    }
    
    public boolean getPremium(){
        return this.premium;
    }

    public static int getContador() {
        return contador;
    }

    //set
    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }

    public void setAtacadores(boolean atacadores){
        this.atacadores=atacadores;
    }

    public void setCor(Color cor){
        this.cor = cor;
    }

    public void setData_lancamento(LocalDate data_lancamento) {
        this.data_lancamento = LocalDate.of(data_lancamento.getYear(),
        data_lancamento.getMonth(),data_lancamento.getDayOfMonth());
    }

    public void serPremium(boolean premium){
        this.premium=premium;
    }

    public static void setContador(int contador) {
        Sapatilha.contador = contador;
    }

    @Override
    public Sapatilha clone(){
        return new Sapatilha(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) 
           return true;
        if(obj==null || obj.getClass() != this.getClass()) 
           return false;
        Sapatilha sap = (Sapatilha) obj;
        return (super.equals(sap) && this.tamanho==sap.getTamanho() && 
        this.atacadores==sap.getAtacadores() && this.cor.equals(sap.getCor()) && 
        this.data_lancamento.equals(sap.getData_lancamento()) && this.premium==sap.getPremium() ); 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("........SAPATILHA.........\n");
        sb.append(super.toString());
        sb.append("Tamanho: "+this.getTamanho()+"\n");
        sb.append("Atacadores: " + (this.getAtacadores() ? "Sim" : "Não") + "\n");
        sb.append("Cor:"+ this.cor.toString()+"\n");
        sb.append("Data de lancamento: "+this.getData_lancamento()+"\n");
        sb.append("Premium: " + (this.getPremium() ? "Sim" : "Não") + "\n");
       
        return sb.toString();
    }  

    public double desconto(){
        if(this.getPremium()){
            LocalDate dataAtual = LocalDate.now();
            long diffInYears = ChronoUnit.YEARS.between(dataAtual, data_lancamento);
            return this.getPreco_base()*0.1*diffInYears;
        }else{
            if(super.getArtigo_novo()){
                if(this.getTamanho()>45){
                    return this.getPreco_base() * 0.3;
                } else {
                    return 0;
                }
            } else {
                return (this.getPreco_base() / this.getNum_donos()) * this.getEstado_utilizacao();
            }
        }
    }

    public double preco_final(){
        return (this.getAtacadores() ? this.getPreco_base()+this.getDesconto() : this.getPreco_base()-this.getDesconto());
    }
    
    
}

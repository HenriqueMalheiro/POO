public class Tshirt extends Artigo{
    public enum Tamanho{
        S,M,L,XL
    }

    public enum Padrao{
        LISO,RISCAS,PALMEIRAS
    }

    private Tamanho tamanho;
    private Padrao padrao;

    private static int contador = 1; 

    public Tshirt(){
        super();
        this.tamanho=Tamanho.S;
        this.padrao=Padrao.LISO;
    }

    public Tshirt(boolean artigo_novo, String estado, int num_donos,
    String descricao, String marca,double preco_base,double estado_utilizacao,Utilizador dono,
    Tamanho tamanho,Padrao padrao){
        super(artigo_novo,estado,num_donos,descricao,marca,preco_base,estado_utilizacao,dono);
        super.setCod_barras("tshirt"+contador++);
        this.tamanho=tamanho;
        this.padrao=padrao;
    }

    public Tshirt(Tshirt novo){
        super(novo);
        this.tamanho=novo.getTamanho();
        this.padrao=novo.getPadrao();
    }

    //gets
    public Tamanho getTamanho(){
        return this.tamanho;
    }

    public Padrao getPadrao(){
        return this.padrao;
    }

    public static int getContador() {
        return contador;
    }

    //sets
    public void setTamanho(Tamanho tamanho){
        this.tamanho = tamanho;
    }

    public void setPadrao(Padrao padrao){
        this.padrao = padrao;
    }

    public static void setContador(int contador) {
        Tshirt.contador = contador;
    }

    @Override
    public Tshirt clone(){
        return new Tshirt(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) 
           return true;
        if(obj==null || obj.getClass() != this.getClass()) 
           return false;
        Tshirt tsh = (Tshirt) obj;
        return (super.equals(tsh) && this.tamanho==tsh.getTamanho() && 
        this.padrao==tsh.getPadrao()); 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("........T-SHIRT.........\n");
        sb.append(super.toString());
        
        Tamanho tam=this.getTamanho();
        if(tam==Tamanho.S){
            sb.append("Tamanho: S"+"\n");
        }else if(tam==Tamanho.M){
            sb.append("Tamanho: M"+"\n");
        }else if(tam==Tamanho.L){
            sb.append("Tamanho: L"+"\n");
        }else{
            sb.append("Tamanho: XL"+"\n");
        }

        Padrao padrao=this.getPadrao();
        if(padrao==Padrao.LISO){
            sb.append("Padrao: Liso"+"\n");
        }else if(padrao==Padrao.RISCAS){
            sb.append("Padrao: Riscas"+"\n");
        }else{
            sb.append("Tamanho: Palmeiras"+"\n");
        }

        return sb.toString();
    }  

    public double desconto(){
        if(this.getArtigo_novo()) return 0;
        return this.getPreco_base()*0.5; 
    }
    
    public double preco_final(){
        return this.getPreco_base()-this.getDesconto();
    }


}
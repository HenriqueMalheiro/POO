import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;

public class Main {
    public static void main(String[] args) {
        try{
            Vintage vintage = Menu.carregaDefault_Changed();
            //Vintage vintage = new Vintage();
            Controlador_Menu_Vintage controlador_Menu_Vintage = new Controlador_Menu_Vintage(vintage);
            Menu menu = new Menu(controlador_Menu_Vintage);
            menu.run_AdminOrUser();
            vintage.guardaEstado("EstadoVintage_changed.dat");
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());  
        }catch (DateTimeException e){
            System.out.println(e.getMessage());
        }catch (AddException e){
            System.out.println(e.getMessage());
        }catch (RemoveException e){
            System.out.println(e.getMessage());
        }catch (VintageException e){
            System.out.println(e.getMessage());
            Main.main(args); 
        }
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Sistema Bancarário ===");
        
        controller.SistemaController sistema = new controller.SistemaController();
        sistema.iniciar();

        System.out.println("=== Sistema Finalizado ===");
    }
}

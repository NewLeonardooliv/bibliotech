package shared;

public class ValidateException extends Exception {

    public ValidateException() {
        super("Erro de validação.");
    }

    public ValidateException(String mensagem) {
        super(mensagem);
    }
}

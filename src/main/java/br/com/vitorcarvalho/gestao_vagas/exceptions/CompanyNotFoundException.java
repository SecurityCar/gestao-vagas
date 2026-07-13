package br.com.vitorcarvalho.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException(){
        super("Company not found. Verify the ID.");
    }
}

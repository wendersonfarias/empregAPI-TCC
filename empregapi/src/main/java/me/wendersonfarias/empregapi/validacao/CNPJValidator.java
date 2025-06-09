package me.wendersonfarias.empregapi.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJValido, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return false;

    String cnpj = value.replaceAll("[^\\d]", "");
    if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}"))
      return false;

    int[] pesos1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
    int[] pesos2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

    try {
      int soma = 0;
      for (int i = 0; i < 12; i++) {
        soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
      }

      int dig1 = 11 - (soma % 11);
      dig1 = (dig1 >= 10) ? 0 : dig1;

      soma = 0;
      for (int i = 0; i < 13; i++) {
        soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
      }

      int dig2 = 11 - (soma % 11);
      dig2 = (dig2 >= 10) ? 0 : dig2;

      return dig1 == Character.getNumericValue(cnpj.charAt(12)) &&
          dig2 == Character.getNumericValue(cnpj.charAt(13));

    } catch (Exception e) {
      return false;
    }
  }
}